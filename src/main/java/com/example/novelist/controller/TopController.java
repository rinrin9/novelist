package com.example.novelist.controller;

import java.io.IOException;
import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.novelist.entity.UserInf;

@Controller
public class TopController {

	@GetMapping(path = "/top")
	public String topTopic(Principal principal, Model model) throws IOException {
    	
        
		return "top/index";
	}
}
