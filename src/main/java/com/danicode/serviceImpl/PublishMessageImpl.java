package com.danicode.serviceImpl;

import com.danicode.configuration.MessagingConfig;
import com.danicode.dto.Message;
import com.danicode.service.PublishService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class PublishMessageImpl implements PublishService {

    public static final String DANI_CODE_KEY = "message_routingkey";
    public static final String DANI_CODE_EXCHANGE = "message_exchange";
    public static final String DANI_CODE_QUEUE = "message_queue";

    @Autowired
    private RabbitTemplate template;

    @Value("${queue.name}")
    private static String queue;

    @Value("${topic.exchange.name}")
    private static  String topicExchange;

    @Value("${routing.key}")
    private static  String routingKay;

    @Override
    public void publishMessage(Message message) {
        message.setDate(new Date());
        message.setMessageId(UUID.randomUUID().toString());
        template.convertAndSend(MessagingConfig.EXCHANGE,
                MessagingConfig.ROUTING_KEY, message);
    }
}
