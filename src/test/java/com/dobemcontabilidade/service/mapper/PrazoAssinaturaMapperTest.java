package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.PrazoAssinaturaAsserts.*;
import static com.dobemcontabilidade.domain.PrazoAssinaturaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PrazoAssinaturaMapperTest {

    private PrazoAssinaturaMapper prazoAssinaturaMapper;

    @BeforeEach
    void setUp() {
        prazoAssinaturaMapper = new PrazoAssinaturaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPrazoAssinaturaSample1();
        var actual = prazoAssinaturaMapper.toEntity(prazoAssinaturaMapper.toDto(expected));
        assertPrazoAssinaturaAllPropertiesEquals(expected, actual);
    }
}
