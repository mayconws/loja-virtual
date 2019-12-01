package com.loja.virtual.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.loja.virtual.models.EntradaItens;
import com.loja.virtual.models.EntradaProduto;
import com.loja.virtual.models.Produto;
import com.loja.virtual.repository.EntradaItensRepository;
import com.loja.virtual.repository.EntradaProdutoRepository;
import com.loja.virtual.repository.FuncionarioRepository;
import com.loja.virtual.repository.ProdutoRepository;

@Controller
public class EntradaProdutoController {
	
	private List<EntradaItens> listaEntrada = new ArrayList<EntradaItens>();
	
	@Autowired
	private EntradaProdutoRepository entradaProdutoRepository;
	
	@Autowired
	private EntradaItensRepository entradaItensRepository;

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private ProdutoRepository produtoRepository;
	
	
	@GetMapping("/adicionarEntradaProduto")
	public ModelAndView add(EntradaProduto entradaProduto, EntradaItens entradaItens) {
		ModelAndView mv = new ModelAndView("/administrativo/formEntradaProduto");
		mv.addObject("entradaProduto", entradaProduto);
		mv.addObject("listaEntrada", this.listaEntrada);
		mv.addObject("entradaItens", entradaItens);
		mv.addObject("funcionario", funcionarioRepository.findAll());
		mv.addObject("produto", produtoRepository.findAll());
		
		return mv;
	}
	
	@PostMapping("/salvarEntradaProduto")
	public ModelAndView salvar(String acao, EntradaProduto entradaProduto, EntradaItens entradaItens) {		
	
		if (acao.equals("itens")) {
			this.listaEntrada.add(entradaItens);
		} else if (acao.equals("salvar")) {
			entradaProdutoRepository.saveAndFlush(entradaProduto);
			
			for (EntradaItens it : listaEntrada) {
				it.setEntrada(entradaProduto);
				entradaItensRepository.saveAndFlush(it);
				Optional<Produto> prod = produtoRepository.findById(it.getProduto().getId());
				Produto produto = prod.get();
				produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + it.getQuantidade());
				produto.setValorVenda(it.getValorVenda());
				produtoRepository.saveAndFlush(produto);
				this.listaEntrada = new ArrayList<>();
			}
			return add(new EntradaProduto(), new EntradaItens());
		}

		return add(entradaProduto, new EntradaItens());	
		
	}
	
	/*@RequestMapping(value = "/listaEntradaProduto")
	public ModelAndView lista() {
		ModelAndView mv = new ModelAndView("/administrativo/listaProduto");
		List<Produto> produto = produtoRepository.findAll();
		mv.addObject("produto", produto);
		return mv;
	}
	
	@GetMapping("/editarEntradaProduto/{id}")
	public ModelAndView edit(@PathVariable("id") long id) {
		
		Optional<Produto> produto = produtoRepository.findById(id);
		Produto e = produto.get();
		
		return add(e);
	}
	

	@GetMapping("/removerProduto/{id}")
	public ModelAndView delete(@PathVariable("id") long id) {
		Optional<Produto> produto = produtoRepository.findById(id);
		Produto e = produto.get();
		produtoRepository.delete(e);	
		
		return lista();
	}*/

}
