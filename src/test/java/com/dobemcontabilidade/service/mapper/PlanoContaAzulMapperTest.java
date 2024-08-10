package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.PlanoContaAzulAsserts.*;
import static com.dobemcontabilidade.domain.PlanoContaAzulTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlanoContaAzulMapperTest {

    private PlanoContaAzulMapper planoContaAzulMapper;

    @BeforeEach
    void setUp() {
        planoContaAzulMapper = new PlanoContaAzulMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPlanoContaAzulSample1();
        var actual = planoContaAzulMapper.toEntity(planoContaAzulMapper.toDto(expected));
        assertPlanoContaAzulAllPropertiesEquals(expected, actual);
    }
}
