package com.api.controle.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.controle.dto.RetornoMarcacao;
import com.api.controle.exception.NotFoundException;
import com.api.controle.model.Funcionario;
import com.api.controle.model.Ponto;
import com.api.controle.service.ControlePontoService;
import com.api.controle.service.FuncionarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/ponto")
@Tag(name = "Controle de Ponto", description = "APIs para gerenciamento de marcações de ponto")
public class ControlePontoController {

    private final ControlePontoService controlePontoService;
    private final FuncionarioService funcionarioService;

    @Autowired
    public ControlePontoController(ControlePontoService controlePontoService, FuncionarioService funcionarioService) {
        this.controlePontoService = controlePontoService;
        this.funcionarioService = funcionarioService;
    }

    @Operation(summary = "Marcar entrada", description = "Marca a entrada do funcionário")
    @PostMapping("/primeira-entrada")
    public ResponseEntity<?> marcarEntrada(@RequestBody Funcionario funcionario) {
        try {
        	Funcionario dados = funcionarioService.consultaPorId(funcionario.getId()).orElse(null);
            Ponto ponto = controlePontoService.marcarEntrada(dados.getId(), dados.getNome());
            RetornoMarcacao<Ponto> resposta = new RetornoMarcacao<>(ponto, "Marcação de entrada realizada com sucesso");
            return ResponseEntity.ok(resposta);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @Operation(summary = "Marcar saída para almoço", description = "Marca a saída para o almoço do funcionário")
    @PostMapping("/primeira-saida")
    public ResponseEntity<?> marcarSaidaAlmoco(@RequestBody Funcionario funcionario) {
        try {
        	Funcionario dados = funcionarioService.consultaPorId(funcionario.getId()).orElse(null);
            Ponto ponto = controlePontoService.marcarSaidaAlmoco(dados.getId());
            RetornoMarcacao<Ponto> resposta = new RetornoMarcacao<>(ponto, "Marcação de saída para almoço realizada com sucesso");
            return ResponseEntity.ok(resposta);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @Operation(summary = "Marcar entrada após almoço", description = "Marca a entrada após o almoço do funcionário")
    @PostMapping("/segunda-entrada")
    public ResponseEntity<?> marcarEntradaAlmoco(@RequestBody Funcionario funcionario) {
        try {
        	Funcionario dados = funcionarioService.consultaPorId(funcionario.getId()).orElse(null);
            Ponto ponto = controlePontoService.marcarEntradaAlmoco(dados.getId());
            RetornoMarcacao<Ponto> resposta = new RetornoMarcacao<>(ponto, "Marcação de entrada após almoço realizada com sucesso");
            return ResponseEntity.ok(resposta);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @Operation(summary = "Marcar saída", description = "Marca a saída do do final de expediente")
    @PostMapping("/segunda-saida")
    public ResponseEntity<?> marcarSaida(@RequestBody Funcionario funcionario) {
        try {
        	Funcionario dados = funcionarioService.consultaPorId(funcionario.getId()).orElse(null);
            Ponto ponto = controlePontoService.marcarSaida(dados.getId());
            RetornoMarcacao<Ponto> resposta = new RetornoMarcacao<>(ponto, "Marcação de saída realizada com sucesso");
            return ResponseEntity.ok(resposta);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @Operation(summary = "Listar todas as marcações", description = "Lista todas as marcações registradas")
    @GetMapping("listar")
    public ResponseEntity<?> listarFrequencias(@RequestParam Long id) {
        try {
        	Funcionario dados = funcionarioService.consultaPorId(id).orElse(null);
            List<Ponto> pontos = controlePontoService.listarFrequencias(dados.getId());
            return ResponseEntity.ok(pontos);
        } catch (NotFoundException ex) {
            return ResponseEntity.status(404).body(ex.getMessage());
        }
    }
}
