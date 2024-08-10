package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.TermoAdesaoContadorAsserts.*;
import static com.dobemcontabilidade.domain.TermoAdesaoContadorTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TermoAdesaoContadorMapperTest {

    private TermoAdesaoContadorMapper termoAdesaoContadorMapper;

    @BeforeEach
    void setUp() {
        termoAdesaoContadorMapper = new TermoAdesaoContadorMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTermoAdesaoContadorSample1();
        var actual = termoAdesaoContadorMapper.toEntity(termoAdesaoContadorMapper.toDto(expected));
        assertTermoAdesaoContadorAllPropertiesEquals(expected, actual);
    }
}
