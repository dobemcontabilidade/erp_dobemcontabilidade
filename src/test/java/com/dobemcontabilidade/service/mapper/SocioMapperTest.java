package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.SocioAsserts.*;
import static com.dobemcontabilidade.domain.SocioTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SocioMapperTest {

    private SocioMapper socioMapper;

    @BeforeEach
    void setUp() {
        socioMapper = new SocioMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getSocioSample1();
        var actual = socioMapper.toEntity(socioMapper.toDto(expected));
        assertSocioAllPropertiesEquals(expected, actual);
    }
}
