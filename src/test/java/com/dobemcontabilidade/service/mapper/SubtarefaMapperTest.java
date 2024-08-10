package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.SubtarefaAsserts.*;
import static com.dobemcontabilidade.domain.SubtarefaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SubtarefaMapperTest {

    private SubtarefaMapper subtarefaMapper;

    @BeforeEach
    void setUp() {
        subtarefaMapper = new SubtarefaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getSubtarefaSample1();
        var actual = subtarefaMapper.toEntity(subtarefaMapper.toDto(expected));
        assertSubtarefaAllPropertiesEquals(expected, actual);
    }
}
