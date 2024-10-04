package com.api.controle.config.dataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.api.controle.model.Funcionario;
import com.api.controle.model.Ponto;
import com.api.controle.repository.ControlePontoRepository;
import com.api.controle.repository.FuncionarioRepository;

@Configuration
public class FuncionarioConfig {
	
    @Autowired
    private FuncionarioRepository funcionarioRepository;
    
    @Autowired
    private ControlePontoRepository controlePontoRepository;

    @Bean
    public CommandLineRunner initDatabase() {
        return args -> {

            Funcionario usuarioPadrao = new Funcionario();
            usuarioPadrao.setId(1L);
            usuarioPadrao.setNome("teste");

            funcionarioRepository.salvar(usuarioPadrao);
            
            Ponto usuarioPonto = new Ponto();
            usuarioPonto.setIdUsuario(1L);
            usuarioPonto.setNome("teste");
            
            controlePontoRepository.salvar(usuarioPonto);
        };
    }
}
