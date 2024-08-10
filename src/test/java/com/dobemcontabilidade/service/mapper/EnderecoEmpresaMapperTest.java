package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.EnderecoEmpresaAsserts.*;
import static com.dobemcontabilidade.domain.EnderecoEmpresaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EnderecoEmpresaMapperTest {

    private EnderecoEmpresaMapper enderecoEmpresaMapper;

    @BeforeEach
    void setUp() {
        enderecoEmpresaMapper = new EnderecoEmpresaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getEnderecoEmpresaSample1();
        var actual = enderecoEmpresaMapper.toEntity(enderecoEmpresaMapper.toDto(expected));
        assertEnderecoEmpresaAllPropertiesEquals(expected, actual);
    }
}
