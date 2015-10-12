package com.valeriisosliuk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GameController {

	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/game/name") 
	public String joinGame(@RequestParam(value="name", required=true) String name, Model model) {
		model.addAttribute("name", name);
		return "game";
	}
}
