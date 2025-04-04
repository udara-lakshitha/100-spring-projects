package com.udara.project_001_echo_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EchoController {
    @GetMapping("/echo")
    public String echo(@RequestParam(value = "message", defaultValue = "Hello") String message) {
        return "Echo: " + message;
    }

    @GetMapping("/")
    public String home() {
        return "Echo Chamber API is running";
    }
}
