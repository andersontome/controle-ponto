package com.api.controle.repository;

import java.util.Optional;

import com.api.controle.model.Funcionario;

public interface FuncionarioRepository {

	Funcionario salvar(Funcionario funcionario);
	Optional<Funcionario> findById(Long id);
}