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

import com.loja.virtual.models.Papeis;
import com.loja.virtual.repository.PapeisRepository;

@Controller
public class PapeisController {

	@Autowired
	private PapeisRepository papeisRepository;	

	@GetMapping("/adicionarPapeis")
	public ModelAndView add(Papeis papeis) {
		ModelAndView mv = new ModelAndView("/administrativo/formPapeis");
		mv.addObject("papeis", papeis);		

		return mv;
	}

	@PostMapping("/salvarPapeis")
	public ModelAndView salvar(@Valid Papeis papeis, BindingResult result) {
		papeisRepository.saveAndFlush(papeis);
		return lista();

	}

	@PostMapping(value = "/pesquisarPapeis")
	public ModelAndView buscar(@RequestParam("buscar") String buscar) {
		ModelAndView mv = new ModelAndView("/administrativo/listaPapeis");
		mv.addObject("papeis", papeisRepository.FindPapeisByname(buscar));

		return mv;
	}

	@RequestMapping(value = "/listarPapeis")
	public ModelAndView lista() {
		ModelAndView mv = new ModelAndView("/administrativo/listaPapeis");
		List<Papeis> papeis = papeisRepository.findAll();
		mv.addObject("papeis", papeis);
		return mv;
	}

	@GetMapping("/editarPapeis/{id}")
	public ModelAndView edit(@PathVariable("id") long id) {

		Optional<Papeis> papeis = papeisRepository.findById(id);
		Papeis p = papeis.get();

		return add(p);
	}

	@GetMapping("/removerPapeis/{id}")
	public ModelAndView delete(@PathVariable("id") long id) {

		Optional<Papeis> papeis = papeisRepository.findById(id);
		Papeis p = papeis.get();
		papeisRepository.delete(p);

		return lista();
	}

}
