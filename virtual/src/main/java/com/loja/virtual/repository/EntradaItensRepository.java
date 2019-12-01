package com.loja.virtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loja.virtual.models.EntradaItens;

public interface EntradaItensRepository extends JpaRepository<EntradaItens, Long> {
	
	
}
