package com.api.controle.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.api.controle.model.Ponto;
import com.api.controle.repository.ControlePontoRepository;
import com.api.controle.service.impl.ControlePontoServiceImpl;

class ControlePontoServiceTest {

    @InjectMocks
    private ControlePontoServiceImpl controlePontoService;  // Use the correct implementation class

    @Mock
    private ControlePontoRepository controlePontoRepository;

    @Mock
    private CalculoPontoService calculoPontoService; // Mock the PontoService

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMarcarEntrada() {
        Ponto ponto = new Ponto();
        ponto.setIdUsuario(1L);
        ponto.setNome("Test User");

        when(controlePontoRepository.buscarPorIdUsuario(1L)).thenReturn(Optional.of(ponto));
        when(controlePontoRepository.salvar(ponto)).thenReturn(ponto);

        Ponto frequenciaSalva = controlePontoService.marcarEntrada(1L, "Test User");

        assertNotNull(frequenciaSalva);
        assertNotNull(frequenciaSalva.getEntrada());
        assertEquals("Test User", frequenciaSalva.getNome());
    }

    @Test
    void testMarcarEntradaJaRegistrada() {
        LocalDateTime now = LocalDateTime.of(2024, 7, 10, 8, 0);
        Ponto ponto = new Ponto();
        ponto.setIdUsuario(1L);
        ponto.setNome("Test User");
        ponto.setEntrada(now);

        when(controlePontoRepository.buscarPorIdUsuario(1L)).thenReturn(Optional.of(ponto));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controlePontoService.marcarEntrada(1L, "Test User");
        });

        assertEquals("Entrada já registrada em: " + now, exception.getMessage());
    }

    @Test
    void testMarcarEntradaComErro() {
        when(controlePontoRepository.buscarPorIdUsuario(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controlePontoService.marcarEntrada(1L, "Test User");
        });

        assertEquals("Funcionário não encontrado.", exception.getMessage());
    }

    @Test
    void testMarcarSaidaAlmoco() {
        LocalDateTime now = LocalDateTime.of(2024, 7, 10, 8, 0);
        Ponto ponto = new Ponto();
        ponto.setIdUsuario(1L);
        ponto.setEntrada(now);

        when(controlePontoRepository.buscarPorIdUsuario(1L)).thenReturn(Optional.of(ponto));
        when(controlePontoRepository.salvar(ponto)).thenReturn(ponto);

        Ponto frequenciaAtualizada = controlePontoService.marcarSaidaAlmoco(1L);

        assertNotNull(frequenciaAtualizada);
        assertNotNull(frequenciaAtualizada.getSaidaAlmoco());
        assertEquals(ponto.getEntrada(), frequenciaAtualizada.getEntrada());
    }

    @Test
    void testMarcarSaidaAlmocoJaRegistrada() {
        LocalDateTime now = LocalDateTime.of(2024, 7, 10, 8, 0);
        Ponto ponto = new Ponto();
        ponto.setIdUsuario(1L);
        ponto.setEntrada(now);
        ponto.setSaidaAlmoco(now.plusHours(4));

        when(controlePontoRepository.buscarPorIdUsuario(1L)).thenReturn(Optional.of(ponto));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controlePontoService.marcarSaidaAlmoco(1L);
        });

        assertEquals("Saída Almoço já registrada em: " + ponto.getSaidaAlmoco(), exception.getMessage());
    }

    @Test
    void testMarcarSaidaAlmocoComErro() {
        Ponto ponto = new Ponto();
        ponto.setIdUsuario(1L);

        when(controlePontoRepository.buscarPorIdUsuario(1L)).thenReturn(Optional.of(ponto));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controlePontoService.marcarSaidaAlmoco(1L);
        });

        assertEquals("Funcionário não tem registro de entrada", exception.getMessage());
    }

    @Test
    void testMarcarSaida() {
        LocalDateTime now = LocalDateTime.of(2024, 7, 10, 8, 0);
        LocalDateTime saidaAlmoco = now.plusHours(4);
        LocalDateTime entradaAlmoco = saidaAlmoco.plusHours(1);
        LocalDateTime saida = entradaAlmoco.plusHours(4);

        Ponto ponto = new Ponto();
        ponto.setIdUsuario(1L);
        ponto.setEntrada(now);
        ponto.setSaidaAlmoco(saidaAlmoco);
        ponto.setEntradaAlmoco(entradaAlmoco);

        when(controlePontoRepository.buscarPorIdUsuario(1L)).thenReturn(Optional.of(ponto));

        // Simule apenas o retorno do ponto salvo, sem chamar novamente o cálculo de horas
        when(controlePontoRepository.salvar(ponto)).thenAnswer(invocation -> {
            Ponto arg = invocation.getArgument(0);
            arg.setSaida(saida);
            return arg;
        });

        Ponto frequenciaAtualizada = controlePontoService.marcarSaida(1L);

        assertNotNull(frequenciaAtualizada);
        assertNotNull(frequenciaAtualizada.getSaida());
        assertEquals(ponto.getEntrada(), frequenciaAtualizada.getEntrada());

        // Verifique que o método calcularHorasTrabalhadas foi chamado apenas uma vez
        verify(calculoPontoService).calcularHorasTrabalhadas(frequenciaAtualizada);
    }


    @Test
    void testMarcarSaidaJaRegistrada() {
        LocalDateTime now = LocalDateTime.of(2024, 7, 10, 8, 0);
        LocalDateTime saidaAlmoco = now.plusHours(4);
        LocalDateTime entradaAlmoco = saidaAlmoco.plusHours(1);
        LocalDateTime saida = entradaAlmoco.plusHours(4);

        Ponto ponto = new Ponto();
        ponto.setIdUsuario(1L);
        ponto.setEntrada(now);
        ponto.setSaidaAlmoco(saidaAlmoco);
        ponto.setEntradaAlmoco(entradaAlmoco);
        ponto.setSaida(saida);

        when(controlePontoRepository.buscarPorIdUsuario(1L)).thenReturn(Optional.of(ponto));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            controlePontoService.marcarSaida(1L);
        });

        assertEquals("Saída já registrada em: " + ponto.getSaida(), exception.getMessage());
    }

    @Test
    void testListarFrequencias() {
        LocalDateTime now = LocalDateTime.of(2024, 7, 10, 8, 0);
        Ponto ponto = new Ponto();
        ponto.setEntrada(now);
        ponto.setSaida(now.plusHours(4));

        when(controlePontoRepository.buscarTodasPorId(anyLong())).thenReturn(Collections.singletonList(ponto));

        List<Ponto> pontos = controlePontoService.listarFrequencias(1L);

        assertNotNull(pontos);
        assertEquals(1, pontos.size());
        assertEquals(ponto.getEntrada(), pontos.get(0).getEntrada());
        assertEquals(ponto.getSaida(), pontos.get(0).getSaida());
    }

    @Test
    void testListarFrequenciasComVariosDias() {
        LocalDateTime dia1Entrada = LocalDateTime.of(2024, 7, 11, 8, 0);
        LocalDateTime dia1Saida = LocalDateTime.of(2024, 7, 11, 17, 0);
        Ponto frequenciaDia1 = new Ponto();
        frequenciaDia1.setId(1L);
        frequenciaDia1.setNome("teste");
        frequenciaDia1.setIdUsuario(15215L);
        frequenciaDia1.setEntrada(dia1Entrada);
        frequenciaDia1.setSaidaAlmoco(dia1Entrada.plusHours(4));
        frequenciaDia1.setEntradaAlmoco(dia1Entrada.plusHours(5));
        frequenciaDia1.setSaida(dia1Saida);

        LocalDateTime dia2Entrada = LocalDateTime.of(2024, 7, 12, 8, 0);
        LocalDateTime dia2Saida = LocalDateTime.of(2024, 7, 12, 17, 0);
        Ponto frequenciaDia2 = new Ponto();
        frequenciaDia2.setId(2L);
        frequenciaDia2.setNome("teste");
        frequenciaDia2.setIdUsuario(15215L);
        frequenciaDia2.setEntrada(dia2Entrada);
        frequenciaDia2.setSaidaAlmoco(dia2Entrada.plusHours(4));
        frequenciaDia2.setEntradaAlmoco(dia2Entrada.plusHours(5));
        frequenciaDia2.setSaida(dia2Saida);

        when(controlePontoRepository.buscarTodasPorId(anyLong())).thenReturn(Arrays.asList(frequenciaDia1, frequenciaDia2));

        List<Ponto> pontos = controlePontoService.listarFrequencias(1L);

        assertNotNull(pontos);
        assertEquals(2, pontos.size());
        assertEquals(frequenciaDia1.getEntrada(), pontos.get(0).getEntrada());
        assertEquals(frequenciaDia1.getSaida(), pontos.get(0).getSaida());
        assertEquals(frequenciaDia2.getEntrada(), pontos.get(1).getEntrada());
        assertEquals(frequenciaDia2.getSaida(), pontos.get(1).getSaida());
    }
}