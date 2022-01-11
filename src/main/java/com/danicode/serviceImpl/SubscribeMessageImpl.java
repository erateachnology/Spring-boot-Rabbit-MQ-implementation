package com.danicode.serviceImpl;

import com.danicode.configuration.MessagingConfig;
import com.danicode.dto.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SubscribeMessageImpl {
    @RabbitListener(queues = MessagingConfig.QUEUE)
    public void listener(Message message) {
        System.out.println(message);
    }
}
