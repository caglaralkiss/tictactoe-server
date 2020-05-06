package com.caglaralkiss.tictactoe.ws.registries;

import com.caglaralkiss.tictactoe.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketSessionsRegistry {

    private final static Logger logger = LoggerFactory.getLogger(WebSocketSessionsRegistry.class);

    public volatile static Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();

    private final ObjectMapper mapper;

    @Autowired
    public WebSocketSessionsRegistry(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public synchronized void addNewSession(WebSocketSession session) {
        sessionMap.put(session.getId(), session);
    }

    public synchronized void removeSession(String sessionId) {
        sessionMap.remove(sessionId);
    }

    public synchronized void sendMessage(String sessionId, Message message) {
        WebSocketSession session = sessionMap.get(sessionId);

        if (session != null) {
            try {
                String serializedMsg = mapper.writeValueAsString(message);
                session.sendMessage(new TextMessage(serializedMsg));
            } catch (IOException e) {
                logger.warn(e.getMessage());
            }
        }
    }
}
