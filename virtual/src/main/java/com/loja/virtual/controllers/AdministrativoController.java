package com.loja.virtual.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdministrativoController {
	
	@GetMapping("/administrativo")
	public String AdmController() {
		return "administrativo/indexAdm";
	}

}
