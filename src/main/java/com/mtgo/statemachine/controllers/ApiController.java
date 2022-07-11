package com.mtgo.statemachine.controllers;

import java.util.concurrent.atomic.AtomicLong;

import com.mtgo.statemachine.model.Status;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
  private final AtomicLong counter = new AtomicLong();
  private static final String template = "Hello, %s!";

  @GetMapping("/api")
  public Status apiStatus(@RequestParam(value = "name", defaultValue = "World") String name) {
    System.out.println("API accessed");
    return new Status(counter.incrementAndGet(), String.format(template, name));
  }
}
