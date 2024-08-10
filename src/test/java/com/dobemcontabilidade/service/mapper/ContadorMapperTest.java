package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.ContadorAsserts.*;
import static com.dobemcontabilidade.domain.ContadorTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContadorMapperTest {

    private ContadorMapper contadorMapper;

    @BeforeEach
    void setUp() {
        contadorMapper = new ContadorMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getContadorSample1();
        var actual = contadorMapper.toEntity(contadorMapper.toDto(expected));
        assertContadorAllPropertiesEquals(expected, actual);
    }
}
