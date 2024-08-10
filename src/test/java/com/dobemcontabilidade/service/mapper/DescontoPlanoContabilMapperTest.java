package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.DescontoPlanoContabilAsserts.*;
import static com.dobemcontabilidade.domain.DescontoPlanoContabilTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DescontoPlanoContabilMapperTest {

    private DescontoPlanoContabilMapper descontoPlanoContabilMapper;

    @BeforeEach
    void setUp() {
        descontoPlanoContabilMapper = new DescontoPlanoContabilMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDescontoPlanoContabilSample1();
        var actual = descontoPlanoContabilMapper.toEntity(descontoPlanoContabilMapper.toDto(expected));
        assertDescontoPlanoContabilAllPropertiesEquals(expected, actual);
    }
}
