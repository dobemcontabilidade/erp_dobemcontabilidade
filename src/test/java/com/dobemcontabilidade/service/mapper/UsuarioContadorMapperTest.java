package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.UsuarioContadorAsserts.*;
import static com.dobemcontabilidade.domain.UsuarioContadorTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UsuarioContadorMapperTest {

    private UsuarioContadorMapper usuarioContadorMapper;

    @BeforeEach
    void setUp() {
        usuarioContadorMapper = new UsuarioContadorMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getUsuarioContadorSample1();
        var actual = usuarioContadorMapper.toEntity(usuarioContadorMapper.toDto(expected));
        assertUsuarioContadorAllPropertiesEquals(expected, actual);
    }
}
