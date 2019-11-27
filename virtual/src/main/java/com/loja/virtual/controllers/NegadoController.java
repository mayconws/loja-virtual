package com.loja.virtual.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NegadoController {
	
	@GetMapping("/negadoAdministrativo")
	public ModelAndView acessoNegado() {		
		ModelAndView mv = new ModelAndView("negadoAdministrativo");		
		
		return mv;
	}
	
	@GetMapping("/negadoCliente")
	public ModelAndView negadoCliente() {		
		ModelAndView mv = new ModelAndView("negadoCliente");		
		
		return mv;
	}

}
