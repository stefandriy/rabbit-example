package com.rabbit.example.client.queue;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class Subscriber {

    private final SimpleMessageListenerContainer listenerContainer;

    @Autowired
    public Subscriber(ApplicationContext context, ConnectionFactory connectionFactory) {
        listenerContainer = new SimpleMessageListenerContainer(connectionFactory);
        listenerContainer.setApplicationContext(context);
        listenerContainer.setMessageListener(new MessageListenerAdapter(new MessageProcessor()));
        listenerContainer.start();
    }

    public void addQueue(Queue queue) {
        listenerContainer.addQueues(queue);
    }

    public void removeQueue(String queueName) {
        listenerContainer.removeQueueNames(queueName);
    }

    @SuppressWarnings("unused")
    private static class MessageProcessor {
        private Object handleMessage(Object message) {
            System.out.println(message);
            return "RESPONSE!!!";
        }
    }
}
