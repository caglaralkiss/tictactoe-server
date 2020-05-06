package com.caglaralkiss.tictactoe.ws.registries;

import com.caglaralkiss.tictactoe.constants.Turn;
import com.caglaralkiss.tictactoe.model.Game;
import com.caglaralkiss.tictactoe.model.Player;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GamesRegistry {

    protected static volatile Map<String, Game> gameMap = new ConcurrentHashMap<>();

    public synchronized void addGame(Game game) {
        gameMap.put(game.getId(), game);
    }

    public synchronized void removeGame(String gameId) {
        gameMap.remove(gameId);
    }

    public synchronized Game getGameFromPlayerId(String playerId) {
        for (Game game : gameMap.values()) {
            Player player1 = game.getPlayer1();
            Player player2 = game.getPlayer2();

            if (player1.getId().equals(playerId) || player2.getId().equals(playerId)) {
                return game;
            }
        }

        return null;
    }

    public synchronized void updateGameTurn(String gameId) {
        Game game = gameMap.get(gameId);
        if (game.getTurn() == Turn.X_TURN) {
            game.setTurn(Turn.O_TURN);
        } else {
            game.setTurn(Turn.X_TURN);
        }
    }
}
