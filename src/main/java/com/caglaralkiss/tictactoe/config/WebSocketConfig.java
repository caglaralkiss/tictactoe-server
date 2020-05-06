package com.caglaralkiss.tictactoe.config;

import com.caglaralkiss.tictactoe.ws.GameHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final GameHandler gameHandler;

    @Autowired
    public WebSocketConfig(GameHandler handler) {
        this.gameHandler = handler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry
                .addHandler(this.gameHandler, "/tictactoe-game/{userName}")
                .setAllowedOrigins("*");
    }
}
