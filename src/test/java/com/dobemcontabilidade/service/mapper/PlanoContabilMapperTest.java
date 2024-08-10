package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.PlanoContabilAsserts.*;
import static com.dobemcontabilidade.domain.PlanoContabilTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlanoContabilMapperTest {

    private PlanoContabilMapper planoContabilMapper;

    @BeforeEach
    void setUp() {
        planoContabilMapper = new PlanoContabilMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPlanoContabilSample1();
        var actual = planoContabilMapper.toEntity(planoContabilMapper.toDto(expected));
        assertPlanoContabilAllPropertiesEquals(expected, actual);
    }
}
