package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.DivisaoCnaeAsserts.*;
import static com.dobemcontabilidade.domain.DivisaoCnaeTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DivisaoCnaeMapperTest {

    private DivisaoCnaeMapper divisaoCnaeMapper;

    @BeforeEach
    void setUp() {
        divisaoCnaeMapper = new DivisaoCnaeMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDivisaoCnaeSample1();
        var actual = divisaoCnaeMapper.toEntity(divisaoCnaeMapper.toDto(expected));
        assertDivisaoCnaeAllPropertiesEquals(expected, actual);
    }
}
