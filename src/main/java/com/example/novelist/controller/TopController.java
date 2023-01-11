package com.example.novelist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TopController {

	@GetMapping(path = "/top")
	public String index(Model model) {
		return "top/index";
	}
}
