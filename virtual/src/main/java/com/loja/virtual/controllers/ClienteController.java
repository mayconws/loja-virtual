package com.loja.virtual.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
		ModelAndView mv = new ModelAndView("/cliente/cadastrarCliente");
		mv.addObject("cliente", cliente);
		mv.addObject("cidade", cidadeRepository.findAll());
		
		return mv;
	}
	
	@PostMapping("/salvarCliente")
public ModelAndView salvar(@Valid Cliente cliente, BindingResult result) {
		
		if(result.hasErrors()) {
			return add(cliente);
		}
		cliente.setSenha(new BCryptPasswordEncoder().encode(cliente.getSenha()));
		clienteRepository.saveAndFlush(cliente);
		
		return add(new Cliente());
	}

	
	@GetMapping("/editarCliente/{id}")
	public ModelAndView edit(@PathVariable("id") long id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		return add(cliente.get());
		
	}
	
	@RequestMapping(value = "/listarCliente")
	public ModelAndView lista() {
		ModelAndView mv = new ModelAndView("/administrativo/ListaCliente");
		List<Cliente> cliente = clienteRepository.findAll();
		mv.addObject("cliente", cliente);
		return mv;
	}
	
	@GetMapping("/removerCliente/{id}")
	public ModelAndView delete(@PathVariable("id") long id) {
		
		Optional<Cliente> cliente = clienteRepository.findById(id);
		Cliente c = cliente.get();
		clienteRepository.delete(c);	
		
		return lista();
	}	

}
