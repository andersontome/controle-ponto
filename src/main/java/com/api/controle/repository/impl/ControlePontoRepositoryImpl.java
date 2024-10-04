package com.api.controle.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.controle.model.Ponto;
import com.api.controle.repository.ControlePontoRepository;
import com.api.controle.repository.jpa.ControlePontoJpaRepository;

@Component
public class ControlePontoRepositoryImpl implements ControlePontoRepository {

    private final ControlePontoJpaRepository controlePontoJpaRepository;

    @Autowired
    public ControlePontoRepositoryImpl(ControlePontoJpaRepository controlePontoJpaRepository) {
        this.controlePontoJpaRepository = controlePontoJpaRepository;
    }

    @Override
    public Ponto salvar(Ponto ponto) {
        return controlePontoJpaRepository.save(ponto);
    }

    @Override
    public List<Ponto> buscarTodasPorId(Long id) {
        return controlePontoJpaRepository.findAllById(id);
    }

    @Override
    public Optional<Ponto> buscarPorIdUsuario(Long idUsuario) {
        return controlePontoJpaRepository.findByIdUsuario(idUsuario);
    }
}
