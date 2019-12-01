package com.loja.virtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.loja.virtual.models.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {
	
	@Query("select c from Cidade c where c.nome like %?1%")
	List<Cidade> FindCidadeByname(String nome);	

}
