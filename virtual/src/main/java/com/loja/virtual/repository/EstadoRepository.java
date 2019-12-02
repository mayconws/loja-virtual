package com.loja.virtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.loja.virtual.models.Estado;

public interface EstadoRepository extends JpaRepository<Estado, Long> {
	
	@Query("select e from Estado e where e.nome like %?1%")
	List<Estado> FindEstadoByname(String nome);	

}
