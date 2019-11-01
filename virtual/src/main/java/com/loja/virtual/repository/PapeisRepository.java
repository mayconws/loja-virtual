package com.loja.virtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.loja.virtual.models.Papeis;

public interface PapeisRepository extends JpaRepository<Papeis, Long> {
	
	@Query("select p from Papeis p where p.funcao like %?1%")
	List<Papeis> FindPapeisByname(String nome);	

}
