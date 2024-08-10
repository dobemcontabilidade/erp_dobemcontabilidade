package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.PagamentoAsserts.*;
import static com.dobemcontabilidade.domain.PagamentoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PagamentoMapperTest {

    private PagamentoMapper pagamentoMapper;

    @BeforeEach
    void setUp() {
        pagamentoMapper = new PagamentoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPagamentoSample1();
        var actual = pagamentoMapper.toEntity(pagamentoMapper.toDto(expected));
        assertPagamentoAllPropertiesEquals(expected, actual);
    }
}
