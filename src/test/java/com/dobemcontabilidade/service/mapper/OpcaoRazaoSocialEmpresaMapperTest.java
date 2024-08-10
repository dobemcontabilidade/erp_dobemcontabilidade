package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.OpcaoRazaoSocialEmpresaAsserts.*;
import static com.dobemcontabilidade.domain.OpcaoRazaoSocialEmpresaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OpcaoRazaoSocialEmpresaMapperTest {

    private OpcaoRazaoSocialEmpresaMapper opcaoRazaoSocialEmpresaMapper;

    @BeforeEach
    void setUp() {
        opcaoRazaoSocialEmpresaMapper = new OpcaoRazaoSocialEmpresaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getOpcaoRazaoSocialEmpresaSample1();
        var actual = opcaoRazaoSocialEmpresaMapper.toEntity(opcaoRazaoSocialEmpresaMapper.toDto(expected));
        assertOpcaoRazaoSocialEmpresaAllPropertiesEquals(expected, actual);
    }
}
