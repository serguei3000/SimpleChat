package org.serguei3000.task.controller;

import org.serguei3000.task.entity.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
 
@Controller
public class WebSocketController {
 
 
	//serg3000 аннотация  @SendTo перенаправляет сообщения брокеру
 
    @MessageMapping("/chat.sendMessage")   //serg3000 здесь добавляется префикс из настройки конфигурации ("/app")
    @SendTo("/topic/publicChatRoom")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }
 
    
    //serg3000 получили уведомление скрипта для добавления пользователя к сессии
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/publicChatRoom")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }
 
}