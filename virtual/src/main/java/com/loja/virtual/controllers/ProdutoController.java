package com.loja.virtual.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.loja.virtual.models.Produto;
import com.loja.virtual.repository.ProdutoRepository;

@Controller
public class ProdutoController {
	
	private static String caminhoImagens = "C:\\Users\\Julio\\git\\Loja_Virtual\\imagens";
	
	@Autowired
	private ProdutoRepository produtoRepository;	
	
	@GetMapping("/adicionarProduto")
	public ModelAndView add(Produto produto) {
		ModelAndView mv = new ModelAndView("/administrativo/formProduto");
		mv.addObject("produto", produto);		
		
		return mv;
	}
	
	@PostMapping("/salvarProduto")
	public ModelAndView salvar(@Valid Produto produto, BindingResult result,
			@RequestParam("file") MultipartFile arquivo) {

		if (result.hasErrors()) {
			return add(produto);
		}

		produtoRepository.saveAndFlush(produto);

		try {
			if (!arquivo.isEmpty()) {
				byte[] bytes = arquivo.getBytes();
				Path caminho = Paths
						.get(caminhoImagens + String.valueOf(produto.getId()) + arquivo.getOriginalFilename());
				Files.write(caminho, bytes);

				produto.setNomeImagem(String.valueOf(produto.getId()) + arquivo.getOriginalFilename());
				produtoRepository.saveAndFlush(produto);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return lista();
		
	}
	
	@GetMapping("/administrativo/mostrarImagem/{imagem}")
	@ResponseBody
	public byte[] retornarImagem(@PathVariable("imagem") String imagem) throws IOException {

		File imagemArquivo = new File(caminhoImagens + imagem);
		if (imagem != null || imagem.trim().length() > 0) {			
			return Files.readAllBytes(imagemArquivo.toPath());
		}
		return null;
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
		
		Optional<Produto> produto = produtoRepository.findById(id);
		Produto p = produto.get();	
		
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
