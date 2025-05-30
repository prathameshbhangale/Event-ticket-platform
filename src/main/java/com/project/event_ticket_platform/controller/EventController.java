package com.project.event_ticket_platform.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/events")
public class EventController {

    @GetMapping
    public String hi(){
        return "server running without any bugs";
    }
}
