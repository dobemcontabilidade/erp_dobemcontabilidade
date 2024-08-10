package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.BancoContadorAsserts.*;
import static com.dobemcontabilidade.domain.BancoContadorTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BancoContadorMapperTest {

    private BancoContadorMapper bancoContadorMapper;

    @BeforeEach
    void setUp() {
        bancoContadorMapper = new BancoContadorMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getBancoContadorSample1();
        var actual = bancoContadorMapper.toEntity(bancoContadorMapper.toDto(expected));
        assertBancoContadorAllPropertiesEquals(expected, actual);
    }
}
