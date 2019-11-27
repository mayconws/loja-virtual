package com.loja.virtual.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.loja.virtual.models.Cliente;
import com.loja.virtual.models.Compra;
import com.loja.virtual.models.ItensCompra;
import com.loja.virtual.models.Produto;
import com.loja.virtual.repository.ClienteRepository;
import com.loja.virtual.repository.CompraRepository;
import com.loja.virtual.repository.ItensCompraRepository;
import com.loja.virtual.repository.ProdutoRepository;

@Controller
public class CarrinhoController {

	private List<ItensCompra> itensCompra = new ArrayList<ItensCompra>();
	private Compra compra = new Compra();
	private Cliente cliente;
	
	private void calcularTotal() {
		compra.setValorTotal(0.);
		for(ItensCompra it: itensCompra) {
			compra.setValorTotal(compra.getValorTotal() + it.getValorTotal());
		}
	}

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private CompraRepository compraRepository;
	
	@Autowired
	private ItensCompraRepository itensCompraRepository;

	@GetMapping("/carrinho")
	public ModelAndView chamarCarrinho() {
		ModelAndView mv = new ModelAndView("cliente/carrinho");		
		calcularTotal();		
		mv.addObject("compra",compra);
		mv.addObject("listaItens", itensCompra);
		return mv;
	}
	
	private void buscarUsuarioLogado() {
		Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
		if (!(autenticado instanceof AnonymousAuthenticationToken)) {
			String email = autenticado.getName();
			cliente = clienteRepository.buscarClienteEmail(email).get(0);
		}
	}
	
	@PostMapping("/finalizar/confirmar")
	public ModelAndView confirmarCompra(String formaPagamento) {
		ModelAndView mv = new ModelAndView("clientecompraFinalizado");
		compra.setCliente(cliente);
		compra.setFormaPagamento(formaPagamento);
		compraRepository.saveAndFlush(compra);

		for (ItensCompra c : itensCompra) {
			c.setCompra(compra);
			itensCompraRepository.saveAndFlush(c);
		}
		itensCompra = new ArrayList<>();
		compra = new Compra();
		return mv;
	}
	
	@GetMapping("/finalizar")
	public ModelAndView finalizarCompra() {
		buscarUsuarioLogado();
		ModelAndView mv = new ModelAndView("cliente/finalizar");
		calcularTotal();		
		mv.addObject("compra",compra);
		mv.addObject("listaItens", itensCompra);
		mv.addObject("cliente", cliente);
		return mv;		
	}
	
	@GetMapping("/alterarQuantidade/{id}/{acao}")
	public String alterarQuantidade(@PathVariable Long id, @PathVariable Integer acao) {
		ModelAndView mv = new ModelAndView("cliente/carrinho");

		for (ItensCompra it : itensCompra) {
			if (it.getProduto().getId().equals(id)) {
				//System.out.println(it.getValorTotal());
				if (acao.equals(1)) {
					it.setQuantidade(it.getQuantidade() + 1);
					it.setValorTotal(0.);
					it.setValorTotal(it.getValorTotal() + (it.getQuantidade() * it.getValorUnitario()));
				} else if (acao == 0) {
					it.setQuantidade(it.getQuantidade() - 1);
					it.setValorTotal(0.);
					it.setValorTotal(it.getValorTotal() + (it.getQuantidade() * it.getValorUnitario()));
				}				
				break;
			}
		}

		return "redirect:/carrinho";
	}
	
	@GetMapping("/removerProduto")
	public String removerprodutoCarrinho(@PathVariable Long id) {		
		
		for (ItensCompra it : itensCompra) {
			if (it.getProduto().getId().equals(id)) {
				itensCompra.remove(it);
				break;
			}
		}		
		
		return "redirect:/carrinho";
	}

	@GetMapping("/adicionarCarrinho/{id}")
	public String adicionarCarrinho(@PathVariable Long id) {	

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
	
		return "redirect:/carrinho";

	}
}
