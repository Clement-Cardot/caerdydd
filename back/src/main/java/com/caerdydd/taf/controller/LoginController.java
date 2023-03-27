package com.caerdydd.taf.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class LoginController {

	@RequestMapping("/login")
	public boolean login(@RequestParam(value = "username") String username,
                                    @RequestParam(value = "password") String password) {
		return true; 
	}
}
