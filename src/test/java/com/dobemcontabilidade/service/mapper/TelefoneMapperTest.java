package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.TelefoneAsserts.*;
import static com.dobemcontabilidade.domain.TelefoneTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TelefoneMapperTest {

    private TelefoneMapper telefoneMapper;

    @BeforeEach
    void setUp() {
        telefoneMapper = new TelefoneMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTelefoneSample1();
        var actual = telefoneMapper.toEntity(telefoneMapper.toDto(expected));
        assertTelefoneAllPropertiesEquals(expected, actual);
    }
}
