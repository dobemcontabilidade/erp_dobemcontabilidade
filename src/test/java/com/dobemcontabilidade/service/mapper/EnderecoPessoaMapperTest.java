package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.EnderecoPessoaAsserts.*;
import static com.dobemcontabilidade.domain.EnderecoPessoaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EnderecoPessoaMapperTest {

    private EnderecoPessoaMapper enderecoPessoaMapper;

    @BeforeEach
    void setUp() {
        enderecoPessoaMapper = new EnderecoPessoaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getEnderecoPessoaSample1();
        var actual = enderecoPessoaMapper.toEntity(enderecoPessoaMapper.toDto(expected));
        assertEnderecoPessoaAllPropertiesEquals(expected, actual);
    }
}
