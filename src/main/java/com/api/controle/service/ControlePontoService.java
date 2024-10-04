package com.api.controle.service;

import java.util.List;

import com.api.controle.model.Ponto;

public interface ControlePontoService {
    public Ponto marcarEntrada(Long idUsuario, String nome);
    public Ponto marcarSaidaAlmoco(Long idUsuario);
    public Ponto marcarEntradaAlmoco(Long idUsuario);
    public Ponto marcarSaida(Long idUsuario);
    public List<Ponto> listarFrequencias(Long id);
}