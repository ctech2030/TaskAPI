package com.shiv.taskservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shiv.taskservice.auth.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	  private final JwtUtil jwtUtil = new JwtUtil();

	    @PostMapping("/login")
	    public String login(@RequestParam String username) {
	        // Ideally, validate the username/password first
	        return jwtUtil.generateToken(username);
	    }

	    @GetMapping("/validate")
	    public String validate(@RequestHeader("Authorization") String authHeader) {
	        String token = authHeader.replace("Bearer ", "");
	        String username = jwtUtil.validateTokenAndGetUsername(token);
	        return username != null ? "Valid token for: " + username : "Invalid token";
	    }

}
