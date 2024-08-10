package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.ObservacaoCnaeAsserts.*;
import static com.dobemcontabilidade.domain.ObservacaoCnaeTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ObservacaoCnaeMapperTest {

    private ObservacaoCnaeMapper observacaoCnaeMapper;

    @BeforeEach
    void setUp() {
        observacaoCnaeMapper = new ObservacaoCnaeMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getObservacaoCnaeSample1();
        var actual = observacaoCnaeMapper.toEntity(observacaoCnaeMapper.toDto(expected));
        assertObservacaoCnaeAllPropertiesEquals(expected, actual);
    }
}
