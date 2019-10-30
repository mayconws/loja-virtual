package com.loja.virtual.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {
	
	@GetMapping("/inicio")
	public String homeAdm() {
		return "index";
	}

}
