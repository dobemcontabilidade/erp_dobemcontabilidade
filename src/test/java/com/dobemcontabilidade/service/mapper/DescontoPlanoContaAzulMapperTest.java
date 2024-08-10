package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.DescontoPlanoContaAzulAsserts.*;
import static com.dobemcontabilidade.domain.DescontoPlanoContaAzulTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DescontoPlanoContaAzulMapperTest {

    private DescontoPlanoContaAzulMapper descontoPlanoContaAzulMapper;

    @BeforeEach
    void setUp() {
        descontoPlanoContaAzulMapper = new DescontoPlanoContaAzulMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDescontoPlanoContaAzulSample1();
        var actual = descontoPlanoContaAzulMapper.toEntity(descontoPlanoContaAzulMapper.toDto(expected));
        assertDescontoPlanoContaAzulAllPropertiesEquals(expected, actual);
    }
}
