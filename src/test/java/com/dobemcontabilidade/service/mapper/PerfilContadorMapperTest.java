package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.PerfilContadorAsserts.*;
import static com.dobemcontabilidade.domain.PerfilContadorTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PerfilContadorMapperTest {

    private PerfilContadorMapper perfilContadorMapper;

    @BeforeEach
    void setUp() {
        perfilContadorMapper = new PerfilContadorMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPerfilContadorSample1();
        var actual = perfilContadorMapper.toEntity(perfilContadorMapper.toDto(expected));
        assertPerfilContadorAllPropertiesEquals(expected, actual);
    }
}
