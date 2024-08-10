package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.UsuarioGestaoAsserts.*;
import static com.dobemcontabilidade.domain.UsuarioGestaoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UsuarioGestaoMapperTest {

    private UsuarioGestaoMapper usuarioGestaoMapper;

    @BeforeEach
    void setUp() {
        usuarioGestaoMapper = new UsuarioGestaoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getUsuarioGestaoSample1();
        var actual = usuarioGestaoMapper.toEntity(usuarioGestaoMapper.toDto(expected));
        assertUsuarioGestaoAllPropertiesEquals(expected, actual);
    }
}
