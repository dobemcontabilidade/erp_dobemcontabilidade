package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.AdicionalEnquadramentoAsserts.*;
import static com.dobemcontabilidade.domain.AdicionalEnquadramentoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdicionalEnquadramentoMapperTest {

    private AdicionalEnquadramentoMapper adicionalEnquadramentoMapper;

    @BeforeEach
    void setUp() {
        adicionalEnquadramentoMapper = new AdicionalEnquadramentoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAdicionalEnquadramentoSample1();
        var actual = adicionalEnquadramentoMapper.toEntity(adicionalEnquadramentoMapper.toDto(expected));
        assertAdicionalEnquadramentoAllPropertiesEquals(expected, actual);
    }
}
