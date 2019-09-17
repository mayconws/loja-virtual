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

import com.loja.virtual.models.Estado;
import com.loja.virtual.repository.EstadoRepository;

@Controller
public class EstadoController {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@GetMapping("/adicionarEstado")
	public ModelAndView add(Estado estado) {
		ModelAndView mv = new ModelAndView("/administrativo/formEstado");
		mv.addObject("estado", estado);
		
		return mv;
	}
	
	@PostMapping("/salvarEstado")
	public ModelAndView salvar(@Valid Estado estado, BindingResult result) {
		estadoRepository.saveAndFlush(estado);
		return lista();
		
	}		
	
	@PostMapping(value = "/pesquisarEstado")		
	public ModelAndView buscar(@RequestParam ("buscar") String buscar) {
		ModelAndView mv = new ModelAndView("/administrativo/ListaEstado");
		mv.addObject("estado", estadoRepository.FindEstadoByname(buscar));
		
		return mv;			
		
	}
	
	@RequestMapping(value = "/listaEstados")
	public ModelAndView lista() {
		ModelAndView mv = new ModelAndView("/administrativo/ListaEstado");
		List<Estado> estado = estadoRepository.findAll();
		mv.addObject("estado", estado);
		return mv;
	}
	
	@GetMapping("/editarEstado/{id}")
	public ModelAndView edit(@PathVariable("id") long id) {
		
		Optional<Estado> estados = estadoRepository.findById(id);
		Estado e = estados.get();
		
		return add(e);
	}
	

	@GetMapping("/removerEstado/{id}")
	public ModelAndView delete(@PathVariable("id") long id) {
		Optional<Estado> estados = estadoRepository.findById(id);
		Estado e = estados.get();
		estadoRepository.delete(e);	
		
		return lista();
	}	

}
