package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.EstadoAsserts.*;
import static com.dobemcontabilidade.domain.EstadoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EstadoMapperTest {

    private EstadoMapper estadoMapper;

    @BeforeEach
    void setUp() {
        estadoMapper = new EstadoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getEstadoSample1();
        var actual = estadoMapper.toEntity(estadoMapper.toDto(expected));
        assertEstadoAllPropertiesEquals(expected, actual);
    }
}
