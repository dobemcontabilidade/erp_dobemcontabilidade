package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.BancoAsserts.*;
import static com.dobemcontabilidade.domain.BancoTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BancoMapperTest {

    private BancoMapper bancoMapper;

    @BeforeEach
    void setUp() {
        bancoMapper = new BancoMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getBancoSample1();
        var actual = bancoMapper.toEntity(bancoMapper.toDto(expected));
        assertBancoAllPropertiesEquals(expected, actual);
    }
}
