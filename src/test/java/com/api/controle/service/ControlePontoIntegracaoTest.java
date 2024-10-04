package com.api.controle.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import com.api.controle.model.Funcionario;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ControlePontoIntegracaoTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Funcionario funcionario;

    @BeforeEach
    void setUp() {
    	funcionario = new Funcionario();
        funcionario.setId(1L);
        funcionario.setNome("teste");
    }

    @Test
    void testMarcarEntrada() throws Exception {
        mockMvc.perform(post("/api/v1/ponto/primeira-entrada")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(funcionario)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/ponto/listar?id=1"))
                .andExpect(status().isOk())
                .andExpect(result -> assertThat(result.getResponse().getContentAsString())
                        .contains("entrada"));
    }

    @Test
    void testMarcarSaidaAlmoco() throws Exception {
        mockMvc.perform(post("/api/v1/ponto/primeira-entrada")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(funcionario)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/v1/ponto/primeira-saida")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(funcionario)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/ponto/listar?id=1"))
                .andExpect(status().isOk())
                .andExpect(result -> assertThat(result.getResponse().getContentAsString())
                        .contains("saidaAlmoco"));
    }

    @Test
    void testMarcarEntradaAlmoco() throws Exception {
        mockMvc.perform(post("/api/v1/ponto/primeira-entrada")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(funcionario)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/v1/ponto/primeira-saida")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(funcionario)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/v1/ponto/segunda-entrada")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(funcionario)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/ponto/listar?id=1"))
                .andExpect(status().isOk())
                .andExpect(result -> assertThat(result.getResponse().getContentAsString())
                        .contains("entradaAlmoco"));
    }

    @Test
    void testMarcarSaida() throws Exception {
        mockMvc.perform(post("/api/v1/ponto/primeira-entrada")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(funcionario)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/v1/ponto/primeira-saida")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(funcionario)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/v1/ponto/segunda-entrada")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(funcionario)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/v1/ponto/segunda-saida")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(funcionario)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/ponto/listar?id=1"))
                .andExpect(status().isOk())
                .andExpect(result -> assertThat(result.getResponse().getContentAsString())
                        .contains("saida"));
    }

    @Test
    void testListarFrequencias() throws Exception {
        mockMvc.perform(get("/api/v1/ponto/listar?id=1"))
                .andExpect(status().isOk())
                .andExpect(result -> assertThat(result.getResponse().getContentAsString())
                        .contains("teste"));
    }
}
