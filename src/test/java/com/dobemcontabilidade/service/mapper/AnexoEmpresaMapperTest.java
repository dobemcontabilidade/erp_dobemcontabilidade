package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.AnexoEmpresaAsserts.*;
import static com.dobemcontabilidade.domain.AnexoEmpresaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AnexoEmpresaMapperTest {

    private AnexoEmpresaMapper anexoEmpresaMapper;

    @BeforeEach
    void setUp() {
        anexoEmpresaMapper = new AnexoEmpresaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAnexoEmpresaSample1();
        var actual = anexoEmpresaMapper.toEntity(anexoEmpresaMapper.toDto(expected));
        assertAnexoEmpresaAllPropertiesEquals(expected, actual);
    }
}
