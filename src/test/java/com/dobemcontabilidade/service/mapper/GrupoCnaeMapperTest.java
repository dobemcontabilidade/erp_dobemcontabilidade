package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.GrupoCnaeAsserts.*;
import static com.dobemcontabilidade.domain.GrupoCnaeTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GrupoCnaeMapperTest {

    private GrupoCnaeMapper grupoCnaeMapper;

    @BeforeEach
    void setUp() {
        grupoCnaeMapper = new GrupoCnaeMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getGrupoCnaeSample1();
        var actual = grupoCnaeMapper.toEntity(grupoCnaeMapper.toDto(expected));
        assertGrupoCnaeAllPropertiesEquals(expected, actual);
    }
}
