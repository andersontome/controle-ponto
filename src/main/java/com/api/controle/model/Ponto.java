package com.api.controle.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ponto")
public class Ponto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Long idUsuario;
    private LocalDate dia;
    private LocalDateTime entrada;
    private LocalDateTime saidaAlmoco;
    private LocalDateTime entradaAlmoco;
    private LocalDateTime saida;
    private long horasTrabalhadasDia;
    private long horasExtrasDia;
    private long horasTrabalhadasMes;
    private long horasExtrasMes;
    private String observacao;
}