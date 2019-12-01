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

import com.loja.virtual.models.Papel;
import com.loja.virtual.repository.PapelRepository;

@Controller
public class PapelController {

	@Autowired
	private PapelRepository papelRepository;	

	@GetMapping("/adicionarPapel")
	public ModelAndView add(Papel papel) {
		ModelAndView mv = new ModelAndView("/administrativo/formPapel");
		mv.addObject("papel", papel);		

		return mv;
	}

	@PostMapping("/salvarPapel")
	public ModelAndView salvar(@Valid Papel papel, BindingResult result) {
		papelRepository.saveAndFlush(papel);
		return lista();

	}

	@PostMapping(value = "/pesquisarPapel")
	public ModelAndView buscar(@RequestParam("buscar") String buscar) {
		ModelAndView mv = new ModelAndView("/administrativo/listaPapel");
		mv.addObject("papel", papelRepository.FindPapelByname(buscar));

		return mv;
	}

	@RequestMapping(value = "/listarPapel")
	public ModelAndView lista() {
		ModelAndView mv = new ModelAndView("/administrativo/listaPapel");
		List<Papel> papel = papelRepository.findAll();
		mv.addObject("papel", papel);
		return mv;
	}

	@GetMapping("/editarPapel/{id}")
	public ModelAndView edit(@PathVariable("id") long id) {

		Optional<Papel> papel = papelRepository.findById(id);
		Papel p = papel.get();

		return add(p);
	}

	@GetMapping("/removerPapel/{id}")
	public ModelAndView delete(@PathVariable("id") long id) {

		Optional<Papel> papel = papelRepository.findById(id);
		Papel p = papel.get();
		papelRepository.delete(p);

		return lista();
	}

}
