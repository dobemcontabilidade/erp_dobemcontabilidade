package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.CalculoPlanoAssinaturaAsserts.*;
import static com.dobemcontabilidade.domain.CalculoPlanoAssinaturaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalculoPlanoAssinaturaMapperTest {

    private CalculoPlanoAssinaturaMapper calculoPlanoAssinaturaMapper;

    @BeforeEach
    void setUp() {
        calculoPlanoAssinaturaMapper = new CalculoPlanoAssinaturaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCalculoPlanoAssinaturaSample1();
        var actual = calculoPlanoAssinaturaMapper.toEntity(calculoPlanoAssinaturaMapper.toDto(expected));
        assertCalculoPlanoAssinaturaAllPropertiesEquals(expected, actual);
    }
}
