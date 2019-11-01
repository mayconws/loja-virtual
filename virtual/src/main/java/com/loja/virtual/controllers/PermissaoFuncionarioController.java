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

import com.loja.virtual.models.PermissaoFuncionario;
import com.loja.virtual.repository.FuncionarioRepository;
import com.loja.virtual.repository.PapelRepository;
import com.loja.virtual.repository.PermissaoFuncionarioRepository;

@Controller
public class PermissaoFuncionarioController {

	@Autowired
	private PermissaoFuncionarioRepository permissaoFuncionarioRepository;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private PapelRepository papelRepository;

	@GetMapping("/adicionarPermissaoFuncionario")
	public ModelAndView add(PermissaoFuncionario permissaoFuncionario) {
		ModelAndView mv = new ModelAndView("/administrativo/formPermissaoFuncionario");
		mv.addObject("permissaoFuncionario", permissaoFuncionario);
		mv.addObject("funcionarios", funcionarioRepository.findAll());
		mv.addObject("papel", papelRepository.findAll());

		return mv;
	}

	@PostMapping("/salvarPermissaoFuncionario")
	public ModelAndView salvar(@Valid PermissaoFuncionario permissaoFuncionario, BindingResult result) {
		permissaoFuncionarioRepository.saveAndFlush(permissaoFuncionario);
		return lista();

	}

	@PostMapping(value = "/pesquisarPermissaoFuncionario")
	public ModelAndView buscar(@RequestParam("buscar") String buscar) {
		ModelAndView mv = new ModelAndView("/administrativo/listaPermissaoFuncionario");
		mv.addObject("permissaoFuncionario", permissaoFuncionarioRepository.FindPermissaoByname(buscar));

		return mv;
	}

	@RequestMapping(value = "/listarPermissaoFuncionario")
	public ModelAndView lista() {
		ModelAndView mv = new ModelAndView("/administrativo/listaPermissaoFuncionario");
		List<PermissaoFuncionario> permissaoFuncionario = permissaoFuncionarioRepository.findAll();
		mv.addObject("permissaoFuncionario", permissaoFuncionario);
		return mv;
	}

	@GetMapping("/editarPermissaoFuncionario/{id}")
	public ModelAndView edit(@PathVariable("id") long id) {

		Optional<PermissaoFuncionario> permissaoFuncionario = permissaoFuncionarioRepository.findById(id);
		PermissaoFuncionario p = permissaoFuncionario.get();

		return add(p);
	}

	@GetMapping("/removerPermissaoFuncionario/{id}")
	public ModelAndView delete(@PathVariable("id") long id) {

		Optional<PermissaoFuncionario> permissaoFuncionario = permissaoFuncionarioRepository.findById(id);
		PermissaoFuncionario p = permissaoFuncionario.get();
		permissaoFuncionarioRepository.delete(p);

		return lista();
	}

}
