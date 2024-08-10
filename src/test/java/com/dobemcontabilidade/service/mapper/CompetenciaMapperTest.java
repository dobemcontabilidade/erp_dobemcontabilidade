package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.CompetenciaAsserts.*;
import static com.dobemcontabilidade.domain.CompetenciaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CompetenciaMapperTest {

    private CompetenciaMapper competenciaMapper;

    @BeforeEach
    void setUp() {
        competenciaMapper = new CompetenciaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCompetenciaSample1();
        var actual = competenciaMapper.toEntity(competenciaMapper.toDto(expected));
        assertCompetenciaAllPropertiesEquals(expected, actual);
    }
}
