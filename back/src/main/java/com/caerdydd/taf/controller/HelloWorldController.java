package com.caerdydd.taf.controller;


import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.caerdydd.taf.bean.HelloWorld;

@RequestMapping("/api")
@RestController
public class HelloWorldController {
    
    private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@RequestMapping("/helloworld")
	public HelloWorld helloWorld(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new HelloWorld(counter.incrementAndGet(), String.format(template, name));
	}

	@GetMapping("/hello")
    public Collection<String> sayHello() {
        return IntStream.range(0, 10)
          .mapToObj(i -> "Hello number " + i)
          .collect(Collectors.toList());
    }
}
