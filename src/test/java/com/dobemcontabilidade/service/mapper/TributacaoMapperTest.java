package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.TributacaoAsserts.*;
import static com.dobemcontabilidade.domain.TributacaoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TributacaoMapperTest {

    private TributacaoMapper tributacaoMapper;

    @BeforeEach
    void setUp() {
        tributacaoMapper = new TributacaoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTributacaoSample1();
        var actual = tributacaoMapper.toEntity(tributacaoMapper.toDto(expected));
        assertTributacaoAllPropertiesEquals(expected, actual);
    }
}
