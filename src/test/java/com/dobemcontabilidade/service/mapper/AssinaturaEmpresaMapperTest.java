package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.AssinaturaEmpresaAsserts.*;
import static com.dobemcontabilidade.domain.AssinaturaEmpresaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AssinaturaEmpresaMapperTest {

    private AssinaturaEmpresaMapper assinaturaEmpresaMapper;

    @BeforeEach
    void setUp() {
        assinaturaEmpresaMapper = new AssinaturaEmpresaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAssinaturaEmpresaSample1();
        var actual = assinaturaEmpresaMapper.toEntity(assinaturaEmpresaMapper.toDto(expected));
        assertAssinaturaEmpresaAllPropertiesEquals(expected, actual);
    }
}
