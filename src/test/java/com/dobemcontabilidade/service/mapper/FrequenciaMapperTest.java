package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.FrequenciaAsserts.*;
import static com.dobemcontabilidade.domain.FrequenciaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FrequenciaMapperTest {

    private FrequenciaMapper frequenciaMapper;

    @BeforeEach
    void setUp() {
        frequenciaMapper = new FrequenciaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getFrequenciaSample1();
        var actual = frequenciaMapper.toEntity(frequenciaMapper.toDto(expected));
        assertFrequenciaAllPropertiesEquals(expected, actual);
    }
}
