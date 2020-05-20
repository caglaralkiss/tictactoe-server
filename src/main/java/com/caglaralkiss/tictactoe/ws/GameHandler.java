package com.caglaralkiss.tictactoe.ws;

import com.caglaralkiss.tictactoe.constants.*;
import com.caglaralkiss.tictactoe.model.*;
import com.caglaralkiss.tictactoe.ws.registries.GamePlayersRegistry;
import com.caglaralkiss.tictactoe.ws.registries.GamesRegistry;
import com.caglaralkiss.tictactoe.ws.registries.WebSocketSessionsRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.UUID;

@Component
public class GameHandler extends TextWebSocketHandler {

    private final static Logger logger = LoggerFactory.getLogger(GameHandler.class);

    private final WebSocketSessionsRegistry webSocketSessionsRegistry;
    private final GamePlayersRegistry gamePlayersRegistry;
    private final GamesRegistry gamesRegistry;
    private final ObjectMapper mapper;

    @Autowired
    public GameHandler(WebSocketSessionsRegistry webSocketSessionsRegistry,
                       GamePlayersRegistry gamePlayersRegistry,
                       GamesRegistry gamesRegistry,
                       ObjectMapper mapper) {
        this.webSocketSessionsRegistry = webSocketSessionsRegistry;
        this.gamePlayersRegistry = gamePlayersRegistry;
        this.gamesRegistry = gamesRegistry;
        this.mapper = mapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        logger.info("Connection established. SessionID: " + session.getId());

        String playerName = this.parsePlayerNameFromUri(session.getUri().getPath());

        Player player = new Player(session.getId(), playerName);
        player.setPlayerState(PlayerState.WAITING);

        webSocketSessionsRegistry.addNewSession(session);
        gamePlayersRegistry.addPlayer(player);

        this.tryToStartNewGame();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        logger.info("Connection closed. SessionID: " + session.getId());

        Game game = gamesRegistry.getGameFromPlayerId(session.getId());

        if (game != null) {
            gamesRegistry.removeGame(game.getId());

            this.sendParticipantLeftMessage(session, game);
        }

        gamePlayersRegistry.removePlayer(session.getId());
        webSocketSessionsRegistry.removeSession(session.getId());
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Game game = gamesRegistry.getGameFromPlayerId(session.getId());

        if (game != null) {
            if (this.checkCurrentTurn(session.getId(), game)) {
                Move move = mapper.readValue(message.getPayload(), Move.class);
                Board board = game.getBoard();

                Player current = this.getMessageSenderPlayer(session.getId(), game);
                Player opponent = this.getOpponentPlayer(session.getId(), game);

                try {
                    current.makeMove(move, board);
                    executeMoveLogic(game, move, current, opponent);

                    if (board.isFilled()) {
                        executeDrawLogic(game, board, current, opponent);
                    } else {
                        SquareState winner = board.checkWinner();

                        if (winner == SquareState.X_MARK) {
                            executeWinningLogic(game, board, current, opponent, GameState.X_WIN);
                        } else if (winner == SquareState.O_MARK) {
                            executeWinningLogic(game, board, current, opponent, GameState.O_WIN);
                        }
                    }
                } catch (Exception notValidMoveException) {
                    Message notValidMoveErrMsg = new Message.MessageBuilder(MessageType.GAME_ERROR, game.getId())
                            .error(GameErrorType.INVALID_MOVE)
                            .build();

                    webSocketSessionsRegistry.sendMessage(session.getId(), notValidMoveErrMsg);
                }
            } else {
                Message notYourTurnErrMsg = new Message.MessageBuilder(MessageType.GAME_ERROR, game.getId())
                        .error(GameErrorType.NOT_YOUR_TURN)
                        .build();

                webSocketSessionsRegistry.sendMessage(session.getId(), notYourTurnErrMsg);
            }
        }
    }


    private void executeMoveLogic(Game game, Move move, Player current, Player opponent) {
        gamesRegistry.updateGameTurn(game.getId());

        Message moveMsg = new Message.MessageBuilder(MessageType.GAME_MOVE, game.getId())
                .move(move)
                .turn(game.getTurn())
                .build();

        webSocketSessionsRegistry.sendMessage(current.getId(), moveMsg);
        webSocketSessionsRegistry.sendMessage(opponent.getId(), moveMsg);
    }

    private void executeDrawLogic(Game game, Board board, Player current, Player opponent) {
        Message drawResultMessage = new Message.MessageBuilder(MessageType.GAME_RESULT, game.getId())
                .winner(GameState.DRAW)
                .turn(Turn.X_TURN)
                .build();
        webSocketSessionsRegistry.sendMessage(current.getId(), drawResultMessage);
        webSocketSessionsRegistry.sendMessage(opponent.getId(), drawResultMessage);

        board.clearBoard();
        game.setTurn(Turn.X_TURN);
    }

    private void executeWinningLogic(Game game, Board board, Player current, Player opponent, GameState winner) {
        Message.MessageBuilder commonMsg =
                new Message.MessageBuilder(MessageType.GAME_RESULT, game.getId())
                        .turn(Turn.X_TURN)
                        .winner(winner);

        if (winner == GameState.X_WIN) {
            if (current.getSquareState() == SquareState.X_MARK) {
                current.setScore(current.getScore() + 10);
            } else {
                opponent.setScore(opponent.getScore() + 10);
            }
        } else {
            if (current.getSquareState() == SquareState.O_MARK) {
                current.setScore(current.getScore() + 10);
            } else {
                opponent.setScore(opponent.getScore() + 10);
            }
        }

        Message currentPlayerMsg = commonMsg.player(current).opponent(opponent).build();
        Message opponentPlayerMsg = commonMsg.player(opponent).opponent(current).build();

        webSocketSessionsRegistry.sendMessage(current.getId(), currentPlayerMsg);
        webSocketSessionsRegistry.sendMessage(opponent.getId(), opponentPlayerMsg);

        board.clearBoard();
        game.setTurn(Turn.X_TURN);
    }

    private String parsePlayerNameFromUri(String path) {
        try {
            String[] splittedPath = path.split("/");
            return splittedPath[splittedPath.length - 1];

        } catch (Exception e) {
            return "NamelessPlayer";
        }
    }

    private void tryToStartNewGame() {
        List<Player> waitingPlayers = gamePlayersRegistry.getWaitingPlayers();

        if (waitingPlayers.size() > 1) {
            Player player1 = waitingPlayers.get(0);
            player1.setSquareState(SquareState.X_MARK);
            player1.setScore(0);
            Player player2 = waitingPlayers.get(1);
            player2.setSquareState(SquareState.O_MARK);
            player2.setScore(0);

            Game game = new Game(UUID.randomUUID().toString());
            game.setPlayer1(player1);
            game.setPlayer2(player2);
            game.setTurn(Turn.X_TURN);

            gamesRegistry.addGame(game);
            gamePlayersRegistry.markPlayerAsPlaying(player1.getId());
            gamePlayersRegistry.markPlayerAsPlaying(player2.getId());

            this.sendGameStartMessageToPlayers(player1, player2, game.getId());
        }
    }

    private void sendGameStartMessageToPlayers(Player player1, Player player2, String gameId) {
        Message.MessageBuilder partialMsg =
                new Message.MessageBuilder(MessageType.GAME_START, gameId);

        Message firstPlayerMsg = partialMsg
                .player(player1)
                .opponent(player2)
                .turn(Turn.X_TURN)
                .build();

        Message secondPlayerMsg = partialMsg
                .player(player2)
                .opponent(player1)
                .turn(Turn.X_TURN)
                .build();

        webSocketSessionsRegistry.sendMessage(player1.getId(), firstPlayerMsg);
        webSocketSessionsRegistry.sendMessage(player2.getId(), secondPlayerMsg);
    }


    private void sendParticipantLeftMessage(WebSocketSession closingSession, Game game) {
        Player stillPlayingGamer = !game.getPlayer1().getId().equals(closingSession.getId()) ?
                game.getPlayer1() : game.getPlayer2();

        Message message = new Message.MessageBuilder(MessageType.GAME_ERROR, game.getId())
                .error(GameErrorType.PARTICIPANT_LEFT)
                .build();

        webSocketSessionsRegistry.sendMessage(stillPlayingGamer.getId(), message);
    }

    private boolean checkCurrentTurn(String moveMakerPlayerId, Game game) {
        Player player1 = game.getPlayer1();
        Player player2 = game.getPlayer2();
        Turn currentTurn = game.getTurn();

        if (player1.getId().equals(moveMakerPlayerId)) {
            if (player1.getSquareState() == SquareState.X_MARK && currentTurn == Turn.X_TURN) {
                return true;
            } else return player1.getSquareState() == SquareState.O_MARK && currentTurn == Turn.O_TURN;
        } else if (player2.getId().equals(moveMakerPlayerId)) {
            if (player2.getSquareState() == SquareState.X_MARK && currentTurn == Turn.X_TURN) {
                return true;
            } else return player2.getSquareState() == SquareState.O_MARK && currentTurn == Turn.O_TURN;
        } else {
            return false;
        }
    }

    private Player getMessageSenderPlayer(String messageSenderSessId, Game game) {
        return game.getPlayer1().getId().equals(messageSenderSessId) ? game.getPlayer1() : game.getPlayer2();
    }

    private Player getOpponentPlayer(String messageSenderSessId, Game game) {
        return game.getPlayer1().getId().equals(messageSenderSessId) ? game.getPlayer2() : game.getPlayer1();
    }
}
