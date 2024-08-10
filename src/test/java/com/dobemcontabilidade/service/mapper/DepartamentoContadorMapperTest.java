package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.DepartamentoContadorAsserts.*;
import static com.dobemcontabilidade.domain.DepartamentoContadorTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DepartamentoContadorMapperTest {

    private DepartamentoContadorMapper departamentoContadorMapper;

    @BeforeEach
    void setUp() {
        departamentoContadorMapper = new DepartamentoContadorMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDepartamentoContadorSample1();
        var actual = departamentoContadorMapper.toEntity(departamentoContadorMapper.toDto(expected));
        assertDepartamentoContadorAllPropertiesEquals(expected, actual);
    }
}
