package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.FormaDePagamentoAsserts.*;
import static com.dobemcontabilidade.domain.FormaDePagamentoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FormaDePagamentoMapperTest {

    private FormaDePagamentoMapper formaDePagamentoMapper;

    @BeforeEach
    void setUp() {
        formaDePagamentoMapper = new FormaDePagamentoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getFormaDePagamentoSample1();
        var actual = formaDePagamentoMapper.toEntity(formaDePagamentoMapper.toDto(expected));
        assertFormaDePagamentoAllPropertiesEquals(expected, actual);
    }
}
