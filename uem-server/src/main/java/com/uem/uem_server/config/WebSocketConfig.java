package com.uem.uem_server.config;

import org.springframework.context.annotation.Configuration;

import org.springframework.web.socket.config.annotation.*;

import com.uem.uem_server.websocket.LiveStreamHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfig
        implements WebSocketConfigurer {

    private final LiveStreamHandler handler;

    public WebSocketConfig(LiveStreamHandler handler) {

        this.handler = handler;
    }

    @Override
    public void registerWebSocketHandlers(
            WebSocketHandlerRegistry registry) {

        registry.addHandler(handler, "/live")
                .setAllowedOrigins("*");
    }
}