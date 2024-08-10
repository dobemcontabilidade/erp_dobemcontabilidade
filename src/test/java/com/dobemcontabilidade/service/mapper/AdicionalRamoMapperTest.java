package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.AdicionalRamoAsserts.*;
import static com.dobemcontabilidade.domain.AdicionalRamoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AdicionalRamoMapperTest {

    private AdicionalRamoMapper adicionalRamoMapper;

    @BeforeEach
    void setUp() {
        adicionalRamoMapper = new AdicionalRamoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAdicionalRamoSample1();
        var actual = adicionalRamoMapper.toEntity(adicionalRamoMapper.toDto(expected));
        assertAdicionalRamoAllPropertiesEquals(expected, actual);
    }
}
