package com.api.controle.service;

import java.util.Optional;

import com.api.controle.model.Funcionario;

public interface FuncionarioService {
	
	public Funcionario cadastro(String nome);
	public Optional<Funcionario> consultaPorId(Long id);

}