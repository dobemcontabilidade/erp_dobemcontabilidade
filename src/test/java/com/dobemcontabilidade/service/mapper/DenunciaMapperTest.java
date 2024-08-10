package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.DenunciaAsserts.*;
import static com.dobemcontabilidade.domain.DenunciaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DenunciaMapperTest {

    private DenunciaMapper denunciaMapper;

    @BeforeEach
    void setUp() {
        denunciaMapper = new DenunciaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDenunciaSample1();
        var actual = denunciaMapper.toEntity(denunciaMapper.toDto(expected));
        assertDenunciaAllPropertiesEquals(expected, actual);
    }
}
