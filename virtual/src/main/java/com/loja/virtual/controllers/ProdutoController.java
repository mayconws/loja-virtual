package com.loja.virtual.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.loja.virtual.models.Produto;
import com.loja.virtual.repository.ProdutoRepository;

@Controller
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository produtoRepository;	
	
	@GetMapping("/adicionarProduto")
	public ModelAndView add(Produto funcionario) {
		ModelAndView mv = new ModelAndView("/administrativo/formProduto");
		mv.addObject("funcionario", funcionario);		
		
		return mv;
	}
	
	@PostMapping("/salvarProduto")
	public ModelAndView salvar(@Valid Produto funcionario, BindingResult result) {
		produtoRepository.saveAndFlush(funcionario);
		return lista();
		
	}
	
	@PostMapping(value = "/pesquisarProduto")		
	public ModelAndView buscar(@RequestParam ("buscar") String buscar) {
		ModelAndView mv = new ModelAndView("/administrativo/listaProduto");
		mv.addObject("produto", produtoRepository.FindProdutoByname(buscar));
		
		return mv;
	}
	
	@RequestMapping(value = "/listarProduto")
	public ModelAndView lista() {
		ModelAndView mv = new ModelAndView("/administrativo/listaProduto");
		List<Produto> produto = produtoRepository.findAll();
		mv.addObject("produto", produto);
		return mv;
	}
	
	@GetMapping("/editarProduto/{id}")
	public ModelAndView edit(@PathVariable("id") long id) {
		
		Optional<Produto> funcionario = produtoRepository.findById(id);
		Produto p = funcionario.get();	
		
		return add(p);
	}
	
	@GetMapping("/removerProduto/{id}")
	public ModelAndView delete(@PathVariable("id") long id) {
		
		Optional<Produto> produto = produtoRepository.findById(id);
		Produto p = produto.get();
		produtoRepository.delete(p);	
		
		return lista();
	}	

}
