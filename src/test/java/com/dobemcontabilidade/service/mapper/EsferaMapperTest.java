package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.EsferaAsserts.*;
import static com.dobemcontabilidade.domain.EsferaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EsferaMapperTest {

    private EsferaMapper esferaMapper;

    @BeforeEach
    void setUp() {
        esferaMapper = new EsferaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getEsferaSample1();
        var actual = esferaMapper.toEntity(esferaMapper.toDto(expected));
        assertEsferaAllPropertiesEquals(expected, actual);
    }
}
