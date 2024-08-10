package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.TarefaAsserts.*;
import static com.dobemcontabilidade.domain.TarefaTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TarefaMapperTest {

    private TarefaMapper tarefaMapper;

    @BeforeEach
    void setUp() {
        tarefaMapper = new TarefaMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTarefaSample1();
        var actual = tarefaMapper.toEntity(tarefaMapper.toDto(expected));
        assertTarefaAllPropertiesEquals(expected, actual);
    }
}
