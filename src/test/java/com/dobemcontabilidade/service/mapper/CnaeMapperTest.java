package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.CnaeAsserts.*;
import static com.dobemcontabilidade.domain.CnaeTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CnaeMapperTest {

    private CnaeMapper cnaeMapper;

    @BeforeEach
    void setUp() {
        cnaeMapper = new CnaeMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCnaeSample1();
        var actual = cnaeMapper.toEntity(cnaeMapper.toDto(expected));
        assertCnaeAllPropertiesEquals(expected, actual);
    }
}
