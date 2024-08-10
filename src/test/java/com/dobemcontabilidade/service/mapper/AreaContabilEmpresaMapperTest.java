package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.AreaContabilEmpresaAsserts.*;
import static com.dobemcontabilidade.domain.AreaContabilEmpresaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AreaContabilEmpresaMapperTest {

    private AreaContabilEmpresaMapper areaContabilEmpresaMapper;

    @BeforeEach
    void setUp() {
        areaContabilEmpresaMapper = new AreaContabilEmpresaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAreaContabilEmpresaSample1();
        var actual = areaContabilEmpresaMapper.toEntity(areaContabilEmpresaMapper.toDto(expected));
        assertAreaContabilEmpresaAllPropertiesEquals(expected, actual);
    }
}
