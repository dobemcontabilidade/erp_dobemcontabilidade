package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.UsuarioErpAsserts.*;
import static com.dobemcontabilidade.domain.UsuarioErpTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UsuarioErpMapperTest {

    private UsuarioErpMapper usuarioErpMapper;

    @BeforeEach
    void setUp() {
        usuarioErpMapper = new UsuarioErpMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getUsuarioErpSample1();
        var actual = usuarioErpMapper.toEntity(usuarioErpMapper.toDto(expected));
        assertUsuarioErpAllPropertiesEquals(expected, actual);
    }
}
