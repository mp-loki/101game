package com.valeriisosliuk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.valeriisosliuk.model.Player;

@Controller
public class GameController {

	@RequestMapping(value="/", method=RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("player", new Player());
		return "index";
	}
	
	@RequestMapping(value="/game", method=RequestMethod.POST) 
	public String joinGame(@ModelAttribute Player player, Model model) {
		model.addAttribute("name", player.getName());
		return "game";
	}
}
