package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.TermoContratoContabilAsserts.*;
import static com.dobemcontabilidade.domain.TermoContratoContabilTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TermoContratoContabilMapperTest {

    private TermoContratoContabilMapper termoContratoContabilMapper;

    @BeforeEach
    void setUp() {
        termoContratoContabilMapper = new TermoContratoContabilMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTermoContratoContabilSample1();
        var actual = termoContratoContabilMapper.toEntity(termoContratoContabilMapper.toDto(expected));
        assertTermoContratoContabilAllPropertiesEquals(expected, actual);
    }
}
