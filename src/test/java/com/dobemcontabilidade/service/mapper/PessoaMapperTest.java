package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.PessoaAsserts.*;
import static com.dobemcontabilidade.domain.PessoaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PessoaMapperTest {

    private PessoaMapper pessoaMapper;

    @BeforeEach
    void setUp() {
        pessoaMapper = new PessoaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPessoaSample1();
        var actual = pessoaMapper.toEntity(pessoaMapper.toDto(expected));
        assertPessoaAllPropertiesEquals(expected, actual);
    }
}
