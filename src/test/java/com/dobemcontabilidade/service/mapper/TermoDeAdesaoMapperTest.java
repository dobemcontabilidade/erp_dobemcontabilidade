package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.TermoDeAdesaoAsserts.*;
import static com.dobemcontabilidade.domain.TermoDeAdesaoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TermoDeAdesaoMapperTest {

    private TermoDeAdesaoMapper termoDeAdesaoMapper;

    @BeforeEach
    void setUp() {
        termoDeAdesaoMapper = new TermoDeAdesaoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTermoDeAdesaoSample1();
        var actual = termoDeAdesaoMapper.toEntity(termoDeAdesaoMapper.toDto(expected));
        assertTermoDeAdesaoAllPropertiesEquals(expected, actual);
    }
}
