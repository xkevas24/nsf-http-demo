package com.qz.nsf.ddeemmoo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class WebController {
    @GetMapping("/")
    public String index() {
        return "hi";
    }
    @GetMapping("/health")
    public String health() {
        return "I'm good";
    }
}

