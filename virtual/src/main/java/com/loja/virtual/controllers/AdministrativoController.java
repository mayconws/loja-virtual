package com.loja.virtual.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdministrativoController {
	
	@GetMapping("/adm")
	public String AdmController() {
		return "administrativo/home";
	}

}
