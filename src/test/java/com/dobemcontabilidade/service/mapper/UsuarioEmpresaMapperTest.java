package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.UsuarioEmpresaAsserts.*;
import static com.dobemcontabilidade.domain.UsuarioEmpresaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UsuarioEmpresaMapperTest {

    private UsuarioEmpresaMapper usuarioEmpresaMapper;

    @BeforeEach
    void setUp() {
        usuarioEmpresaMapper = new UsuarioEmpresaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getUsuarioEmpresaSample1();
        var actual = usuarioEmpresaMapper.toEntity(usuarioEmpresaMapper.toDto(expected));
        assertUsuarioEmpresaAllPropertiesEquals(expected, actual);
    }
}
