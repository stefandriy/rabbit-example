package com.rabbit.example.client.rest;

import com.rabbit.example.client.queue.Manager;
import com.rabbit.example.client.queue.Publisher;
import com.rabbit.example.client.queue.Subscriber;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private final Manager manager;
    private final Publisher publisher;
    private final Subscriber subscriber;

    @Autowired
    public Controller(Manager manager, Publisher publisher, Subscriber subscriber) {
        this.manager = manager;
        this.publisher = publisher;
        this.subscriber = subscriber;
    }

    @GetMapping("/openConnection")
    public String openConnection(@RequestParam String key) {
        Queue outboundQueue = manager.createOutboundQueue(key);
        subscriber.addQueue(outboundQueue);
        return "Queue created with routing key: " + key;
    }

    @GetMapping("/closeConnection")
    public String closeConnection(@RequestParam String key) {
        subscriber.removeQueue(manager.getOutboundQueueName(key));
        manager.removeOutboundQueue(key);
        return "Queue removed with routing key: " + key;
    }

    @GetMapping("/send")
    public String sendMessage(@RequestParam String key,
                              @RequestParam String message) {
        publisher.send(key, message);
        return String.format("Message '%s' was sent with key %s", message, key);
    }
}
