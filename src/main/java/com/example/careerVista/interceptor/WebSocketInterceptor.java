package com.example.careerVista.interceptor;

import com.example.careerVista.Jwt.CandidateDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;

import java.util.List;

public class WebSocketInterceptor implements ChannelInterceptor {

    @Autowired
    private com.example.careerVista.Jwt.JwtUtil jwtUtil;

    @Autowired
    private CandidateDetailsService candidateDetailsService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        System.out.println("Hello from preSend"+accessor);
        List<String> tokenList = accessor.getNativeHeader("Authorization");
        if (tokenList == null || tokenList.isEmpty()) {
            return message;
        }


        String token = tokenList.get(0).substring(7);
        System.out.println("Hi from websocket" + token);



        accessor.setLeaveMutable(true);
        return MessageBuilder.createMessage(message.getPayload(), accessor.getMessageHeaders());
    }
}
