package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.DepartamentoFuncionarioAsserts.*;
import static com.dobemcontabilidade.domain.DepartamentoFuncionarioTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DepartamentoFuncionarioMapperTest {

    private DepartamentoFuncionarioMapper departamentoFuncionarioMapper;

    @BeforeEach
    void setUp() {
        departamentoFuncionarioMapper = new DepartamentoFuncionarioMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDepartamentoFuncionarioSample1();
        var actual = departamentoFuncionarioMapper.toEntity(departamentoFuncionarioMapper.toDto(expected));
        assertDepartamentoFuncionarioAllPropertiesEquals(expected, actual);
    }
}
