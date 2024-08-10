package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.AreaContabilContadorAsserts.*;
import static com.dobemcontabilidade.domain.AreaContabilContadorTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AreaContabilContadorMapperTest {

    private AreaContabilContadorMapper areaContabilContadorMapper;

    @BeforeEach
    void setUp() {
        areaContabilContadorMapper = new AreaContabilContadorMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAreaContabilContadorSample1();
        var actual = areaContabilContadorMapper.toEntity(areaContabilContadorMapper.toDto(expected));
        assertAreaContabilContadorAllPropertiesEquals(expected, actual);
    }
}
