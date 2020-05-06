package com.caglaralkiss.tictactoe.ws.registries;

import com.caglaralkiss.tictactoe.constants.PlayerState;
import com.caglaralkiss.tictactoe.model.Player;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class GamePlayersRegistry {

    public volatile static Map<String, Player> playerMap = new ConcurrentHashMap<>();

    public synchronized void addPlayer(Player player) {
        playerMap.put(player.getId(), player);
    }

    public synchronized void removePlayer(String sessionId) {
        playerMap.remove(sessionId);
    }

    public synchronized void markPlayerAsPlaying(String playerId) {
        Player player = playerMap.get(playerId);
        player.setPlayerState(PlayerState.PLAYING);
    }

    public synchronized void markPlayerAsWaiting(String sessionId) {
        Player player = playerMap.get(sessionId);
        player.setPlayerState(PlayerState.WAITING);
    }

    public synchronized List<Player> getWaitingPlayers() {
        return playerMap
                .values()
                .stream()
                .filter(player -> player.getPlayerState() == PlayerState.WAITING)
                .collect(Collectors.toList());
    }
}
