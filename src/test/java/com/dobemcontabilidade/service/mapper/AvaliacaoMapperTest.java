package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.AvaliacaoAsserts.*;
import static com.dobemcontabilidade.domain.AvaliacaoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AvaliacaoMapperTest {

    private AvaliacaoMapper avaliacaoMapper;

    @BeforeEach
    void setUp() {
        avaliacaoMapper = new AvaliacaoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAvaliacaoSample1();
        var actual = avaliacaoMapper.toEntity(avaliacaoMapper.toDto(expected));
        assertAvaliacaoAllPropertiesEquals(expected, actual);
    }
}
