package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.FuncionarioAsserts.*;
import static com.dobemcontabilidade.domain.FuncionarioTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FuncionarioMapperTest {

    private FuncionarioMapper funcionarioMapper;

    @BeforeEach
    void setUp() {
        funcionarioMapper = new FuncionarioMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getFuncionarioSample1();
        var actual = funcionarioMapper.toEntity(funcionarioMapper.toDto(expected));
        assertFuncionarioAllPropertiesEquals(expected, actual);
    }
}
