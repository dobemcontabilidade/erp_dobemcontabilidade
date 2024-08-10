package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.RamoAsserts.*;
import static com.dobemcontabilidade.domain.RamoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RamoMapperTest {

    private RamoMapper ramoMapper;

    @BeforeEach
    void setUp() {
        ramoMapper = new RamoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getRamoSample1();
        var actual = ramoMapper.toEntity(ramoMapper.toDto(expected));
        assertRamoAllPropertiesEquals(expected, actual);
    }
}
