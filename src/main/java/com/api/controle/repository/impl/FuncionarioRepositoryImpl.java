package com.api.controle.repository.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.controle.model.Funcionario;
import com.api.controle.repository.FuncionarioRepository;
import com.api.controle.repository.jpa.FuncionarioJpaRepository;

@Component
public class FuncionarioRepositoryImpl implements FuncionarioRepository{

	private final FuncionarioJpaRepository funcionarioJpaRepository;
	
	@Autowired
    public FuncionarioRepositoryImpl(FuncionarioJpaRepository funcionarioJpaRepository) {
        this.funcionarioJpaRepository = funcionarioJpaRepository;
    }
	
	@Override
	public Funcionario salvar(Funcionario funcionario) {
		return funcionarioJpaRepository.save(funcionario);
	}

	@Override
	public Optional<Funcionario> findById(Long id) {
		return funcionarioJpaRepository.findById(id);
	}
}