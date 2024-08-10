package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.EnquadramentoAsserts.*;
import static com.dobemcontabilidade.domain.EnquadramentoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EnquadramentoMapperTest {

    private EnquadramentoMapper enquadramentoMapper;

    @BeforeEach
    void setUp() {
        enquadramentoMapper = new EnquadramentoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getEnquadramentoSample1();
        var actual = enquadramentoMapper.toEntity(enquadramentoMapper.toDto(expected));
        assertEnquadramentoAllPropertiesEquals(expected, actual);
    }
}
