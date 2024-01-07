package com.example.careerVista.controllerImpl;

import com.example.careerVista.dao.MessageDao;
import com.example.careerVista.dto.MessageDto;
import com.example.careerVista.dto.UserChatDto;
import com.example.careerVista.dto.UserDto;
import com.example.careerVista.entity.Message;
import com.example.careerVista.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class WebSocketController {


    @Autowired
   private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    MessageDao messageDao;



    @MessageMapping("/hello/{channel}")
    public Message greet(@DestinationVariable String channel, Message message) throws InterruptedException {
          Thread.sleep(1000);
        messageDao.save(message);
        simpMessagingTemplate.convertAndSend("/topic/" + channel, message.getMessage());
        return message;
    }

    @GetMapping("/getMessage/{channel}")
    public List<MessageDto> getMessages(@PathVariable String channel,Principal principal)
    {
        System.out.println("Hi from wesocketcontroller "+principal);


        List<Message> messages = messageDao.findAllByChannel(channel);

        List<MessageDto> messageDtos = new ArrayList<>();

        messages.forEach(m->
        {
            User nSender = m.getSender();
            User nReceiver = m.getReceiver();
            UserChatDto sender = new UserChatDto(nSender.getId(),nSender.getFirstName(),
                    nSender.getLastName());
            UserChatDto receiver = new UserChatDto(nReceiver.getId(),nReceiver.getFirstName(),
                    nReceiver.getLastName());

            messageDtos.add(new MessageDto(m.getId(),m.getChannel(),m.getTime(), m.getMessage(),sender,receiver));

        });
        System.out.println();
        return messageDtos;
    }
}
