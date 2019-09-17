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

import com.loja.virtual.models.Cliente;
import com.loja.virtual.repository.CidadeRepository;
import com.loja.virtual.repository.ClienteRepository;

@Controller
public class ClienteController {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@GetMapping("/adicionarCliente")
	public ModelAndView add(Cliente cliente) {
		ModelAndView mv = new ModelAndView("/administrativo/formCliente");
		mv.addObject("cliente", cliente);
		mv.addObject("cidades", cidadeRepository.findAll());
		
		return mv;
	}
	
	@PostMapping("/salvarCliente")
	public ModelAndView salvar(@Valid Cliente cliente, BindingResult result) {
		clienteRepository.saveAndFlush(cliente);
		return lista();
		
	}
	
	@PostMapping(value = "/pesquisarCliente")		
	public ModelAndView buscar(@RequestParam ("buscar") String buscar) {
		ModelAndView mv = new ModelAndView("/administrativo/ListaCliente");
		mv.addObject("cliente", clienteRepository.FindClienteByname(buscar));
		
		return mv;
	}
	
	@RequestMapping(value = "/consultaCliente")
	public ModelAndView lista() {
		ModelAndView mv = new ModelAndView("/administrativo/ListaCliente");
		List<Cliente> cliente = clienteRepository.findAll();
		mv.addObject("cliente", cliente);
		return mv;
	}
	
	@GetMapping("/editarCliente/{id}")
	public ModelAndView edit(@PathVariable("id") long id) {
		
		Optional<Cliente> clientes = clienteRepository.findById(id);
		Cliente a = clientes.get();	
		
		return add(a);
	}
	
	@GetMapping("/removerCliente/{id}")
	public ModelAndView delete(@PathVariable("id") long id) {
		
		Optional<Cliente> clientes = clienteRepository.findById(id);
		Cliente c = clientes.get();
		clienteRepository.delete(c);	
		
		return lista();
	}	

}
