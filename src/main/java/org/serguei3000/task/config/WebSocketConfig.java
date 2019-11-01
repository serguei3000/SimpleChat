package org.serguei3000.task.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
 
@Configuration
@EnableWebSocketMessageBroker  //serg3000 включили Websocket сервер от Spring
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
     
    @Autowired
    private HandshakeInterceptor handshakeInterceptor;
 
    
    //serg3000 в скрипте: var socket = new SockJS('/ourChat');   stompClient = Stomp.over(socket);
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ourChat").withSockJS().setInterceptors(handshakeInterceptor);
    }
    
 
   //serg3000 здесь создали простого брокера, который будет расслылать полученные на него сообщения всем участникам
    
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/topic");  //serg3000 префикс маппинга для брокера
    }
    
    
    
  //serg3000 без этого бина у меня приложение не видело шаблонов, не всегда нужно, зависит от версии библиотек Spring
    @Bean
    public ClassLoaderTemplateResolver yourTemplateResolver() {
        ClassLoaderTemplateResolver configurer = new ClassLoaderTemplateResolver();
        configurer.setPrefix("templates/");
        configurer.setSuffix(".html");
        configurer.setTemplateMode(TemplateMode.HTML);
        configurer.setCharacterEncoding("UTF-8");
        configurer.setOrder(0);  
        configurer.setCacheable(false);
        configurer.setCheckExistence(true);
        return configurer;
    }
 
}