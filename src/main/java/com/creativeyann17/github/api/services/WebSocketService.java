package com.creativeyann17.github.api.services;

import io.micronaut.websocket.WebSocketBroadcaster;
import io.micronaut.websocket.WebSocketSession;
import io.micronaut.websocket.annotation.OnClose;
import io.micronaut.websocket.annotation.OnMessage;
import io.micronaut.websocket.annotation.OnOpen;
import io.micronaut.websocket.annotation.ServerWebSocket;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;

@Slf4j
@ServerWebSocket("/ws")
public class WebSocketService {

  @Inject
  private WebSocketBroadcaster broadcaster;

  @OnOpen
  public void onOpen(WebSocketSession session) {
    log.info("New websocket session: {}", session.getId());
  }

  @OnMessage
  public void onMessage(String message, WebSocketSession session) {
    log.info("Message websocket session: {} message: {}", session.getId(), message);
  }

  @OnClose
  public void onClose(WebSocketSession session) {
    log.info("Close websocket session: {}", session.getId());
  }
}