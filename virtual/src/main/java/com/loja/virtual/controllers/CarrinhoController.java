package com.loja.virtual.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.loja.virtual.models.ItensCompra;
import com.loja.virtual.models.Produto;
import com.loja.virtual.repository.ProdutoRepository;

@Controller
public class CarrinhoController {

	private List<ItensCompra> itensCompra = new ArrayList<ItensCompra>();

	@Autowired
	private ProdutoRepository produtoRepository;

	@GetMapping("/carrinho")
	public ModelAndView chamarCarrinho() {
		ModelAndView mv = new ModelAndView("cliente/carrinho");
		mv.addObject("listaItens", itensCompra);
		return mv;
	}
	
	@GetMapping("/alterarQuantidade")
	public ModelAndView altersrQuantidade(@PathVariable Long id, @PathVariable Integer acao) {
		ModelAndView mv = new ModelAndView("cliente/carrinho");
		mv.addObject("listaItens", itensCompra);
		return mv;
	}

	@GetMapping("/adicionarCarrinho/{id}")
	public ModelAndView adicionarCarrinho(@PathVariable Long id) {
		ModelAndView mv = new ModelAndView("cliente/carrinho");

		Optional<Produto> prod = produtoRepository.findById(id);
		Produto produto = prod.get();

		int controle = 0;
		for (ItensCompra it : itensCompra) {
			if (it.getProduto().getId().equals(produto.getId())) {
				it.setQuantidade(it.getQuantidade() + 1);
				controle = 1;
				break;
			}
		}

		if (controle == 0) {
			ItensCompra item = new ItensCompra();
			item.setProduto(produto);
			item.setValorUnitario(produto.getValorVenda());
			item.setQuantidade(item.getQuantidade() + 1);
			item.setValorTotal(item.getQuantidade() * item.getValorUnitario());
			itensCompra.add(item);

		}

		mv.addObject("listaItens", itensCompra);
		return mv;

	}
}
