package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.DepartamentoEmpresaAsserts.*;
import static com.dobemcontabilidade.domain.DepartamentoEmpresaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DepartamentoEmpresaMapperTest {

    private DepartamentoEmpresaMapper departamentoEmpresaMapper;

    @BeforeEach
    void setUp() {
        departamentoEmpresaMapper = new DepartamentoEmpresaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDepartamentoEmpresaSample1();
        var actual = departamentoEmpresaMapper.toEntity(departamentoEmpresaMapper.toDto(expected));
        assertDepartamentoEmpresaAllPropertiesEquals(expected, actual);
    }
}
