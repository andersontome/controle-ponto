package com.api.controle.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.controle.exception.NotFoundException;
import com.api.controle.model.Ponto;
import com.api.controle.repository.ControlePontoRepository;
import com.api.controle.service.ControlePontoService;
import com.api.controle.service.CalculoPontoService;

@Component
public class ControlePontoServiceImpl implements ControlePontoService {

    private final ControlePontoRepository controlePontoRepository;
    private final CalculoPontoService calculoPontoService;

    @Autowired
    public ControlePontoServiceImpl(ControlePontoRepository controlePontoRepository, CalculoPontoService calculoPontoService) {
        this.controlePontoRepository = controlePontoRepository;
        this.calculoPontoService = calculoPontoService;
    }

    @Override
    public Ponto marcarEntrada(Long idUsuario, String nome) {
        Optional<Ponto> funcionarioOpt = controlePontoRepository.buscarPorIdUsuario(idUsuario);
        Ponto funcionario = funcionarioOpt.orElseThrow(() -> new IllegalArgumentException("Funcionário não encontrado."));
        if (funcionario.getEntrada() != null) {
            throw new IllegalArgumentException("Entrada já registrada em: " + funcionario.getEntrada());
        } else {
            funcionario.setEntrada(LocalDateTime.now());
            return controlePontoRepository.salvar(funcionario);
        }
    }

    @Override
    public Ponto marcarSaidaAlmoco(Long idUsuario) {
        Optional<Ponto> optionalFrequencia = controlePontoRepository.buscarPorIdUsuario(idUsuario);
        Ponto ponto = optionalFrequencia.orElseThrow(() -> new IllegalArgumentException("Frequência não encontrada para o usuário e data fornecidos."));
        if (ponto.getEntrada() != null) {
            if (ponto.getSaidaAlmoco() != null) {
                throw new IllegalArgumentException("Saída Almoço já registrada em: " + ponto.getSaidaAlmoco());
            } else {
                ponto.setSaidaAlmoco(LocalDateTime.now());
                return controlePontoRepository.salvar(ponto);
            }
        } else {
            throw new IllegalArgumentException("Funcionário não tem registro de entrada");
        }
    }

    @Override
    public Ponto marcarEntradaAlmoco(Long idUsuario) {
        Optional<Ponto> optionalFrequencia = controlePontoRepository.buscarPorIdUsuario(idUsuario);
        Ponto ponto = optionalFrequencia.orElseThrow(() -> new IllegalArgumentException("Frequência não encontrada para o usuário e data fornecidos."));
        if (ponto.getEntrada() != null) {
            if (ponto.getEntradaAlmoco() != null) {
                throw new IllegalArgumentException("Entrada Almoço já registrada em: " + ponto.getEntradaAlmoco());
            } else {
                ponto.setEntradaAlmoco(LocalDateTime.now());
                return controlePontoRepository.salvar(ponto);
            }
        } else {
            throw new IllegalArgumentException("Funcionário não tem registro de entrada");
        }
    }

    @Override
    public Ponto marcarSaida(Long idUsuario) {
        Optional<Ponto> optionalFrequencia = controlePontoRepository.buscarPorIdUsuario(idUsuario);
        Ponto ponto = optionalFrequencia.orElseThrow(() -> new IllegalArgumentException("Frequência não encontrada para o usuário e data fornecidos."));
        if (ponto.getEntrada() != null) {
            if (ponto.getSaida() != null) {
                throw new IllegalArgumentException("Saída já registrada em: " + ponto.getSaida());
            } else {
                LocalDateTime now = LocalDateTime.now();
                if (now.toLocalDate().isAfter(ponto.getEntrada().toLocalDate())) {
                    ponto.setSaida(ponto.getEntrada().withHour(17).withMinute(0).withSecond(0));
                } else {
                    ponto.setSaida(now);
                }

                calculoPontoService.calcularHorasTrabalhadas(ponto);

                return controlePontoRepository.salvar(ponto);
            }
        } else {
            throw new IllegalArgumentException("Funcionário não tem registro de entrada");
        }
    }

    @Override
    public List<Ponto> listarFrequencias(Long id) {
        List<Ponto> list = controlePontoRepository.buscarTodasPorId(id);
        if (list.isEmpty()) {
            throw new NotFoundException("Não há dados para listar");
        }
        return list;
    }
}