package com.rec.zadanie4.controllers;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class HelloController {

	@GetMapping({ "/", "/home" })
	public String home(Model model) {


		return "home";

	}

}