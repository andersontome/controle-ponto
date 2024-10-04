package com.api.controle.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.controle.exception.NotFoundException;
import com.api.controle.model.Funcionario;
import com.api.controle.service.FuncionarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/funcionario")
@Tag(name = "Controle de funcionario", description = "APIs para gerenciamento de funcionarios")
public class FuncionarioController {
	
	private final FuncionarioService funcionarioService;
	
	@Autowired
    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }
	
	@Operation(summary = "Cadastro de funcionario", description = "Gerar cadastro de funcionario")
    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastro(@RequestBody String nome) {
        try {
        	Funcionario resposta = funcionarioService.cadastro(nome);
            return ResponseEntity.ok(resposta);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @Operation(summary = "Consulta de dados funcionario", description = "Consultar dados do funcionario")
    @GetMapping("/consultaId")
    public ResponseEntity<?> consultaId(@RequestParam Long id) {
        try {
        	Optional<Funcionario> resposta = funcionarioService.consultaPorId(id);
            return ResponseEntity.ok(resposta);
        } catch (NotFoundException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        }
    }

}