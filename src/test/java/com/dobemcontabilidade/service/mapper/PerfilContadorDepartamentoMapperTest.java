package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.PerfilContadorDepartamentoAsserts.*;
import static com.dobemcontabilidade.domain.PerfilContadorDepartamentoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PerfilContadorDepartamentoMapperTest {

    private PerfilContadorDepartamentoMapper perfilContadorDepartamentoMapper;

    @BeforeEach
    void setUp() {
        perfilContadorDepartamentoMapper = new PerfilContadorDepartamentoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPerfilContadorDepartamentoSample1();
        var actual = perfilContadorDepartamentoMapper.toEntity(perfilContadorDepartamentoMapper.toDto(expected));
        assertPerfilContadorDepartamentoAllPropertiesEquals(expected, actual);
    }
}
