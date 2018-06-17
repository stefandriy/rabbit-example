package com.rabbit.example.client.queue;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.amqp.core.BindingBuilder.bind;
import static org.springframework.amqp.core.ExchangeBuilder.topicExchange;
import static org.springframework.amqp.core.QueueBuilder.nonDurable;

@Service
public class Manager {

    private final RabbitAdmin rabbitAdmin;
    private final String outbound = "outbound";

    @Autowired
    public Manager(RabbitAdmin rabbitAdmin) {
        this.rabbitAdmin = rabbitAdmin;
    }

    public String getOutboundQueueName(String key) {
        return outbound + "." + key;
    }

    public Queue createOutboundQueue(String routingKey) {
        Exchange exchange = topicExchange(outbound).build();
        Queue queue = nonDurable(getOutboundQueueName(routingKey)).build();
        Binding binding = bind(queue).to(exchange).with(routingKey).noargs();
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareBinding(binding);
        return queue;
    }

    public void removeOutboundQueue(String key) {
        rabbitAdmin.deleteQueue(getOutboundQueueName(key));
    }
}
