package com.loja.virtual.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.loja.virtual.models.PermissaoFuncionario;;

public interface PermissaoFuncionarioRepository extends JpaRepository<PermissaoFuncionario, Long> {
	
	@Query("select p from PermissaoFuncionario p where p.funcionario like %?1%")
	List<PermissaoFuncionario> FindPermissaoByname(String nome);	

}
