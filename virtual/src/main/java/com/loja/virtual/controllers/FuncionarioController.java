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

import com.loja.virtual.models.Funcionario;
import com.loja.virtual.repository.CidadeRepository;
import com.loja.virtual.repository.FuncionarioRepository;

@Controller
public class FuncionarioController {
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@GetMapping("/adicionarFuncionario")
	public ModelAndView add(Funcionario funcionario) {
		ModelAndView mv = new ModelAndView("/administrativo/formFuncionario");
		mv.addObject("funcionario", funcionario);
		mv.addObject("cidades", cidadeRepository.findAll());
		
		return mv;
	}
	
	@PostMapping("/salvarFuncionario")
	public ModelAndView salvar(@Valid Funcionario funcionario, BindingResult result) {
		funcionarioRepository.saveAndFlush(funcionario);
		return lista();
		
	}
	
	@PostMapping(value = "/pesquisarFuncionario")		
	public ModelAndView buscar(@RequestParam ("buscar") String buscar) {
		ModelAndView mv = new ModelAndView("/administrativo/ListaFuncionario");
		mv.addObject("funcionario", funcionarioRepository.FindFuncionarioByname(buscar));
		
		return mv;
	}
	
	@RequestMapping(value = "/listarFuncionario")
	public ModelAndView lista() {
		ModelAndView mv = new ModelAndView("/administrativo/ListaFuncionario");
		List<Funcionario> funcionario = funcionarioRepository.findAll();
		mv.addObject("funcionario", funcionario);
		return mv;
	}
	
	@GetMapping("/editarFuncionario/{id}")
	public ModelAndView edit(@PathVariable("id") long id) {
		
		Optional<Funcionario> funcionario = funcionarioRepository.findById(id);
		Funcionario f = funcionario.get();	
		
		return add(f);
	}
	
	@GetMapping("/removerFuncionario/{id}")
	public ModelAndView delete(@PathVariable("id") long id) {
		
		Optional<Funcionario> funcionario = funcionarioRepository.findById(id);
		Funcionario f = funcionario.get();
		funcionarioRepository.delete(f);	
		
		return lista();
	}	

}
