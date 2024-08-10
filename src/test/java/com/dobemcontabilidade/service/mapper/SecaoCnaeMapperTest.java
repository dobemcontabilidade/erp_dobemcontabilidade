package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.SecaoCnaeAsserts.*;
import static com.dobemcontabilidade.domain.SecaoCnaeTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SecaoCnaeMapperTest {

    private SecaoCnaeMapper secaoCnaeMapper;

    @BeforeEach
    void setUp() {
        secaoCnaeMapper = new SecaoCnaeMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getSecaoCnaeSample1();
        var actual = secaoCnaeMapper.toEntity(secaoCnaeMapper.toDto(expected));
        assertSecaoCnaeAllPropertiesEquals(expected, actual);
    }
}
