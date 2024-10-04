package com.api.controle.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RetornoMarcacao<T> {
    private T dados;
    private String mensagem;
}