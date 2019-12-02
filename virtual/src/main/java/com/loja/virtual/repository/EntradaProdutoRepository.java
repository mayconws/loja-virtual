package com.loja.virtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loja.virtual.models.EntradaProduto;

public interface EntradaProdutoRepository extends JpaRepository<EntradaProduto, Long> {
	
	
}
