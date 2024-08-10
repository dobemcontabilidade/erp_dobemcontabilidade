package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.ValorBaseRamoAsserts.*;
import static com.dobemcontabilidade.domain.ValorBaseRamoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ValorBaseRamoMapperTest {

    private ValorBaseRamoMapper valorBaseRamoMapper;

    @BeforeEach
    void setUp() {
        valorBaseRamoMapper = new ValorBaseRamoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getValorBaseRamoSample1();
        var actual = valorBaseRamoMapper.toEntity(valorBaseRamoMapper.toDto(expected));
        assertValorBaseRamoAllPropertiesEquals(expected, actual);
    }
}
