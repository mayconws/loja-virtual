package com.loja.virtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.loja.virtual.models.Papel;

public interface PapelRepository extends JpaRepository<Papel, Long> {
	
	@Query("select p from Papel p where p.nome like %?1%")
	List<Papel> FindPapelByname(String nome);	

}
