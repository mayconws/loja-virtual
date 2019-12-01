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

import com.loja.virtual.models.Cidade;
import com.loja.virtual.repository.CidadeRepository;
import com.loja.virtual.repository.EstadoRepository;

@Controller
public class CidadeController {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@GetMapping("/adicionarCidade")
	public ModelAndView add(Cidade cidade) {
		ModelAndView mv = new ModelAndView("/administrativo/formCidade");
		mv.addObject("cidade", cidade);
		mv.addObject("estados", estadoRepository.findAll());
		
		return mv;
	}
	
	@PostMapping("/salvarCidade")
	public ModelAndView salvar(@Valid Cidade cidade, BindingResult result) {
		cidadeRepository.saveAndFlush(cidade);
		return lista();
		
	}
	
	@PostMapping(value = "/pesquisarCidade")		
	public ModelAndView buscar(@RequestParam ("buscar") String buscar) {
		ModelAndView mv = new ModelAndView("/administrativo/listaCidade");
		mv.addObject("cidade", cidadeRepository.FindCidadeByname(buscar));
		
		return mv;			
		
	}
	
	@RequestMapping(value = "/consultaCidade")
	public ModelAndView lista() {
		ModelAndView mv = new ModelAndView("/administrativo/listaCidade");
		List<Cidade> cidade = cidadeRepository.findAll();
		mv.addObject("cidade", cidade);
		return mv;
	}
	
	@GetMapping("/editarCidade/{id}")
	public ModelAndView edit(@PathVariable("id") long id) {
		
		Optional<Cidade> cidade = cidadeRepository.findById(id);
		Cidade a = cidade.get();	
		
		return add(a);
	}
	
	@GetMapping("/removerCidade/{id}")
	public ModelAndView delete(@PathVariable("id") long id) {
		
		Optional<Cidade> cidade = cidadeRepository.findById(id);
		Cidade c = cidade.get();
		cidadeRepository.delete(c);	
		
		return lista();
	}	

}
