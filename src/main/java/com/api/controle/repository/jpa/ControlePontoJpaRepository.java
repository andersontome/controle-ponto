package com.api.controle.repository.jpa;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.controle.model.Ponto;

public interface ControlePontoJpaRepository extends JpaRepository<Ponto, Long> {
    List<Ponto> findByEntradaBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);
    Optional<Ponto> findByIdUsuarioAndEntradaBetween(Long idUsuario, LocalDateTime startOfDay, LocalDateTime endOfDay);
    Optional<Ponto> findByIdUsuario(Long idUsuario);
	List<Ponto> findAllById(Long id);
}