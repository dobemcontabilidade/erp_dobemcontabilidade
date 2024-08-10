package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.PerfilRedeSocialAsserts.*;
import static com.dobemcontabilidade.domain.PerfilRedeSocialTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PerfilRedeSocialMapperTest {

    private PerfilRedeSocialMapper perfilRedeSocialMapper;

    @BeforeEach
    void setUp() {
        perfilRedeSocialMapper = new PerfilRedeSocialMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPerfilRedeSocialSample1();
        var actual = perfilRedeSocialMapper.toEntity(perfilRedeSocialMapper.toDto(expected));
        assertPerfilRedeSocialAllPropertiesEquals(expected, actual);
    }
}
