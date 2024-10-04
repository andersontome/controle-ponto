package com.api.controle.repository.jpa;

import org.springframework.data.repository.CrudRepository;

import com.api.controle.model.Funcionario;

public interface FuncionarioJpaRepository extends CrudRepository<Funcionario, Long>{

}