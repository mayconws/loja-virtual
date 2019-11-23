package com.loja.virtual.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loja.virtual.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	
}
