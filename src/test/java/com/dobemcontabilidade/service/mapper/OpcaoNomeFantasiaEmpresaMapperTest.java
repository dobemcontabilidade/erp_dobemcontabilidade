package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.OpcaoNomeFantasiaEmpresaAsserts.*;
import static com.dobemcontabilidade.domain.OpcaoNomeFantasiaEmpresaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OpcaoNomeFantasiaEmpresaMapperTest {

    private OpcaoNomeFantasiaEmpresaMapper opcaoNomeFantasiaEmpresaMapper;

    @BeforeEach
    void setUp() {
        opcaoNomeFantasiaEmpresaMapper = new OpcaoNomeFantasiaEmpresaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getOpcaoNomeFantasiaEmpresaSample1();
        var actual = opcaoNomeFantasiaEmpresaMapper.toEntity(opcaoNomeFantasiaEmpresaMapper.toDto(expected));
        assertOpcaoNomeFantasiaEmpresaAllPropertiesEquals(expected, actual);
    }
}
