package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.AnexoPessoaAsserts.*;
import static com.dobemcontabilidade.domain.AnexoPessoaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AnexoPessoaMapperTest {

    private AnexoPessoaMapper anexoPessoaMapper;

    @BeforeEach
    void setUp() {
        anexoPessoaMapper = new AnexoPessoaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getAnexoPessoaSample1();
        var actual = anexoPessoaMapper.toEntity(anexoPessoaMapper.toDto(expected));
        assertAnexoPessoaAllPropertiesEquals(expected, actual);
    }
}
