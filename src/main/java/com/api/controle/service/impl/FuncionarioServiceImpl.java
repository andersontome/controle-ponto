package com.api.controle.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.controle.model.Funcionario;
import com.api.controle.model.Ponto;
import com.api.controle.repository.ControlePontoRepository;
import com.api.controle.repository.FuncionarioRepository;
import com.api.controle.service.FuncionarioService;

@Component
public class FuncionarioServiceImpl implements FuncionarioService{

	private final FuncionarioRepository funcionarioRepository;
	
	private final ControlePontoRepository controlePontoRepository;
	
	@Autowired
    public FuncionarioServiceImpl(FuncionarioRepository funcionarioRepository, ControlePontoRepository controlePontoRepository) {
        this.funcionarioRepository = funcionarioRepository;
        this.controlePontoRepository = controlePontoRepository;
    }
	
	@Override
	public Funcionario cadastro(String nome) {
		Funcionario funcionario = new Funcionario();
		funcionario.setNome(nome);
		funcionario = funcionarioRepository.salvar(funcionario);
		
		Ponto ponto = new Ponto();
		ponto.setIdUsuario(funcionario.getId());
		ponto.setNome(funcionario.getNome());
		controlePontoRepository.salvar(ponto);
		
		return funcionario;
	}

	@Override
	public Optional<Funcionario> consultaPorId(Long id) {
		return funcionarioRepository.findById(id);
	}
}