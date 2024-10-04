package com.api.controle.repository;

import java.util.List;
import java.util.Optional;

import com.api.controle.model.Ponto;

public interface ControlePontoRepository {
    Ponto salvar(Ponto ponto);
    List<Ponto> buscarTodasPorId(Long id);
    Optional<Ponto> buscarPorIdUsuario(Long idUsuario);
}