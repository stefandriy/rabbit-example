package com.rabbit.example.server.queue;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class Subscriber {

    @RabbitListener(queues = "inbound.all")
    public void listen(Message message) {
        System.out.println(message);
    }

}
