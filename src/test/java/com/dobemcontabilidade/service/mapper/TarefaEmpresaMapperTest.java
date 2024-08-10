package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.TarefaEmpresaAsserts.*;
import static com.dobemcontabilidade.domain.TarefaEmpresaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TarefaEmpresaMapperTest {

    private TarefaEmpresaMapper tarefaEmpresaMapper;

    @BeforeEach
    void setUp() {
        tarefaEmpresaMapper = new TarefaEmpresaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTarefaEmpresaSample1();
        var actual = tarefaEmpresaMapper.toEntity(tarefaEmpresaMapper.toDto(expected));
        assertTarefaEmpresaAllPropertiesEquals(expected, actual);
    }
}
