package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.AreaContabilAsserts.*;
import static com.dobemcontabilidade.domain.AreaContabilTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AreaContabilMapperTest {

    private AreaContabilMapper areaContabilMapper;

    @BeforeEach
    void setUp() {
        areaContabilMapper = new AreaContabilMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAreaContabilSample1();
        var actual = areaContabilMapper.toEntity(areaContabilMapper.toDto(expected));
        assertAreaContabilAllPropertiesEquals(expected, actual);
    }
}
