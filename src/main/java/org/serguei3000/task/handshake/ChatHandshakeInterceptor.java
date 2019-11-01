package org.serguei3000.task.handshake;

import java.util.Map;

import javax.servlet.http.HttpSession;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
 
@Component
public class ChatHandshakeInterceptor implements HandshakeInterceptor {
 
    //serguei3000 для любителей статистики
	private static final Logger logger = LoggerFactory.getLogger(ChatHandshakeInterceptor.class);
     
    
    //serguei3000 слушаем события соединения с сервером и отсоединения
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
            Map<String, Object> attributes) throws Exception {
         
        logger.info("Создаем сессию");
         
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession();
            attributes.put("sessionId", session.getId());
        }
        return true;
    }
 
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
            Exception ex) {
        logger.info("Погнали чатиться :)");
    }
     
}