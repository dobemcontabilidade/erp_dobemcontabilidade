package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.CidadeAsserts.*;
import static com.dobemcontabilidade.domain.CidadeTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CidadeMapperTest {

    private CidadeMapper cidadeMapper;

    @BeforeEach
    void setUp() {
        cidadeMapper = new CidadeMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCidadeSample1();
        var actual = cidadeMapper.toEntity(cidadeMapper.toDto(expected));
        assertCidadeAllPropertiesEquals(expected, actual);
    }
}
