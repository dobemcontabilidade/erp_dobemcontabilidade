package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.TipoDenunciaAsserts.*;
import static com.dobemcontabilidade.domain.TipoDenunciaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TipoDenunciaMapperTest {

    private TipoDenunciaMapper tipoDenunciaMapper;

    @BeforeEach
    void setUp() {
        tipoDenunciaMapper = new TipoDenunciaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTipoDenunciaSample1();
        var actual = tipoDenunciaMapper.toEntity(tipoDenunciaMapper.toDto(expected));
        assertTipoDenunciaAllPropertiesEquals(expected, actual);
    }
}
