package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.PerfilContadorAreaContabilAsserts.*;
import static com.dobemcontabilidade.domain.PerfilContadorAreaContabilTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PerfilContadorAreaContabilMapperTest {

    private PerfilContadorAreaContabilMapper perfilContadorAreaContabilMapper;

    @BeforeEach
    void setUp() {
        perfilContadorAreaContabilMapper = new PerfilContadorAreaContabilMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPerfilContadorAreaContabilSample1();
        var actual = perfilContadorAreaContabilMapper.toEntity(perfilContadorAreaContabilMapper.toDto(expected));
        assertPerfilContadorAreaContabilAllPropertiesEquals(expected, actual);
    }
}
