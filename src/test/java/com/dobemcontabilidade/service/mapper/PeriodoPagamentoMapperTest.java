package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.PeriodoPagamentoAsserts.*;
import static com.dobemcontabilidade.domain.PeriodoPagamentoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PeriodoPagamentoMapperTest {

    private PeriodoPagamentoMapper periodoPagamentoMapper;

    @BeforeEach
    void setUp() {
        periodoPagamentoMapper = new PeriodoPagamentoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPeriodoPagamentoSample1();
        var actual = periodoPagamentoMapper.toEntity(periodoPagamentoMapper.toDto(expected));
        assertPeriodoPagamentoAllPropertiesEquals(expected, actual);
    }
}
