package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.AvaliacaoContadorAsserts.*;
import static com.dobemcontabilidade.domain.AvaliacaoContadorTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AvaliacaoContadorMapperTest {

    private AvaliacaoContadorMapper avaliacaoContadorMapper;

    @BeforeEach
    void setUp() {
        avaliacaoContadorMapper = new AvaliacaoContadorMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAvaliacaoContadorSample1();
        var actual = avaliacaoContadorMapper.toEntity(avaliacaoContadorMapper.toDto(expected));
        assertAvaliacaoContadorAllPropertiesEquals(expected, actual);
    }
}
