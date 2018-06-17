package com.rabbit.example.server.rest;

import com.rabbit.example.server.queue.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private final Publisher publisher;

    @Autowired
    public Controller(Publisher publisher) {
        this.publisher = publisher;
    }

    @GetMapping("/send")
    public String sendMessage(@RequestParam String key,
                              @RequestParam String message) {
        publisher.send(key, message);
        return "Message sent: " + message;
    }
}
