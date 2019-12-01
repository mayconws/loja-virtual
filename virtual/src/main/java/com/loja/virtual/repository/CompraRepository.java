package com.loja.virtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loja.virtual.models.Compra;

public interface CompraRepository extends JpaRepository<Compra, Long> {
	
	
}
