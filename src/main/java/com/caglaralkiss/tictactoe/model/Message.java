package com.caglaralkiss.tictactoe.model;

import com.caglaralkiss.tictactoe.constants.GameErrorType;
import com.caglaralkiss.tictactoe.constants.GameState;
import com.caglaralkiss.tictactoe.constants.MessageType;
import com.caglaralkiss.tictactoe.constants.Turn;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message {

    private static final Logger logger = LoggerFactory.getLogger(Message.class);

    private final MessageType messageType;
    private final String gameId;
    private final Move move;
    private final Turn turn;
    private final Player player;
    private final Player opponent;
    private final GameState winner;
    private final GameErrorType error;

    private Message(MessageBuilder messageBuilder) {
        this.messageType = messageBuilder.messageType;
        this.gameId = messageBuilder.gameId;
        this.move = messageBuilder.move;
        this.turn = messageBuilder.turn;
        this.player = messageBuilder.player;
        this.opponent = messageBuilder.opponent;
        this.winner = messageBuilder.winner;
        this.error = messageBuilder.error;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getGameId() {
        return gameId;
    }

    public Move getMove() {
        return move;
    }

    public Turn getTurn() {
        return turn;
    }

    public Player getPlayer() {
        return player;
    }

    public Player getOpponent() {
        return opponent;
    }

    public GameState getWinner() {
        return winner;
    }

    public GameErrorType getError() {
        return error;
    }

    public static class MessageBuilder {
        private final MessageType messageType;
        private final String gameId;
        private Move move;
        private Turn turn;
        private Player player;
        private Player opponent;
        private GameState winner;
        private GameErrorType error;

        public MessageBuilder(MessageType messageType, String gameId) {
            this.messageType = messageType;
            this.gameId = gameId;
        }

        public MessageBuilder move(Move move) {
            this.move = move;
            return this;
        }

        public MessageBuilder turn(Turn turn) {
            this.turn = turn;
            return this;
        }

        public MessageBuilder player(Player player) {
            this.player = player;
            return this;
        }

        public MessageBuilder opponent(Player opponent) {
            this.opponent = opponent;
            return this;
        }

        public MessageBuilder winner(GameState winner) {
            this.winner = winner;
            return this;
        }

        public MessageBuilder error(GameErrorType error) {
            this.error = error;
            return this;
        }

        public Message build() {
            Message message = new Message(this);
            this.validateWinner(message);
            return new Message(this);
        }

        public void validateWinner(Message message) {
            if (message.getWinner() == null || message.getWinner() == GameState.PLAYING) {
                logger.error("Invalid GameState");
            }
        }
    }
}
