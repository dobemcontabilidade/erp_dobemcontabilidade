package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.AdicionalTributacaoAsserts.*;
import static com.dobemcontabilidade.domain.AdicionalTributacaoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdicionalTributacaoMapperTest {

    private AdicionalTributacaoMapper adicionalTributacaoMapper;

    @BeforeEach
    void setUp() {
        adicionalTributacaoMapper = new AdicionalTributacaoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAdicionalTributacaoSample1();
        var actual = adicionalTributacaoMapper.toEntity(adicionalTributacaoMapper.toDto(expected));
        assertAdicionalTributacaoAllPropertiesEquals(expected, actual);
    }
}
