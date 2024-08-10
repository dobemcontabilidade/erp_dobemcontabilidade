package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.AtividadeEmpresaAsserts.*;
import static com.dobemcontabilidade.domain.AtividadeEmpresaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AtividadeEmpresaMapperTest {

    private AtividadeEmpresaMapper atividadeEmpresaMapper;

    @BeforeEach
    void setUp() {
        atividadeEmpresaMapper = new AtividadeEmpresaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAtividadeEmpresaSample1();
        var actual = atividadeEmpresaMapper.toEntity(atividadeEmpresaMapper.toDto(expected));
        assertAtividadeEmpresaAllPropertiesEquals(expected, actual);
    }
}
