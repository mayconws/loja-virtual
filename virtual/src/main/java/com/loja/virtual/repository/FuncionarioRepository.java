package com.loja.virtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.loja.virtual.models.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
	
	@Query("select f from Cidade f where f.nome like %?1%")
	List<Funcionario> FindFuncionarioByname(String nome);	

}
