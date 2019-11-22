package com.loja.virtual.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.loja.virtual.repository.ProdutoRepository;

@Controller
public class InicioController {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@GetMapping("/inicio")
	public ModelAndView index() {
		ModelAndView mv =  new ModelAndView("loja/index");	
		mv.addObject("produto", produtoRepository.findAll());
		return mv;
	}

}
