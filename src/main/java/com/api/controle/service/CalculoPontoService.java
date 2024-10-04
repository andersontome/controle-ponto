package com.api.controle.service;

import java.time.Duration;
import java.util.List;

import org.springframework.stereotype.Service;

import com.api.controle.model.Ponto;

@Service
public class CalculoPontoService {

    public void calcularHorasTrabalhadas(Ponto ponto) {
        if (ponto.getEntrada() == null || ponto.getSaida() == null) {
            ponto.setObservacao("Horário de entrada ou saída não preenchido.");
            return;
        }

        Duration totalTrabalhado = Duration.ZERO;

        if (ponto.getSaidaAlmoco() != null && ponto.getEntradaAlmoco() != null) {
            totalTrabalhado = Duration.between(ponto.getEntrada(), ponto.getSaidaAlmoco())
                                    .plus(Duration.between(ponto.getEntradaAlmoco(), ponto.getSaida()));
        } else {
            totalTrabalhado = Duration.between(ponto.getEntrada(), ponto.getSaida());
        }

        long totalHoras = totalTrabalhado.toMinutes() / 60;
        ponto.setHorasTrabalhadasDia(totalHoras);

        if (totalHoras > 8) {
            long horasExtras = totalHoras - 8;
            ponto.setHorasExtrasDia(horasExtras);
            ajustarObservacaoHorasDia(ponto, horasExtras);
        } else {
            ponto.setHorasExtrasDia(0);
            if(totalHoras == 0) {
            	ponto.setObservacao("Não trabalhou o periodo de horas do dia completo.");
            }
        }
    }

    public void calcularHorasTrabalhadasMes(List<Ponto> registrosDoMes, Ponto pontoAtual) {
        long totalMinutosMes = 0;
        long totalMinutosExtrasMes = 0;

        for (Ponto registro : registrosDoMes) {
            totalMinutosMes += registro.getHorasTrabalhadasDia() * 60;
            totalMinutosExtrasMes += registro.getHorasExtrasDia() * 60;
        }

        long totalHorasMes = totalMinutosMes / 60;
        long totalHorasExtrasMes = totalMinutosExtrasMes / 60;

        pontoAtual.setHorasTrabalhadasMes(totalHorasMes);
        pontoAtual.setHorasExtrasMes(totalHorasExtrasMes);
        ajustarObservacaoHorasMes(pontoAtual, totalHorasExtrasMes);
    }

    private void ajustarObservacaoHorasDia(Ponto ponto, long horasExtras) {
        if (horasExtras > 0) {
            ponto.setObservacao("Banco de horas positivo: " + horasExtras + " horas extras a serem compensadas.");
        } else {
            ponto.setObservacao("Dia regularizado.");
        }
    }

    private void ajustarObservacaoHorasMes(Ponto ponto, long horasExtrasMes) {
        if (horasExtrasMes > 0) {
            ponto.setObservacao("Banco de horas com saldo positivo no mês: " + horasExtrasMes + " horas extras.");
        } else if (horasExtrasMes < 0) {
            ponto.setObservacao("Banco de horas com saldo negativo no mês, regularização necessária.");
        } else {
            ponto.setObservacao("Horas do mês regularizadas.");
        }
    }
}