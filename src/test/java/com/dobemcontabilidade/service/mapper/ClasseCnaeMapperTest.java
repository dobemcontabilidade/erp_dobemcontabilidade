package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.ClasseCnaeAsserts.*;
import static com.dobemcontabilidade.domain.ClasseCnaeTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClasseCnaeMapperTest {

    private ClasseCnaeMapper classeCnaeMapper;

    @BeforeEach
    void setUp() {
        classeCnaeMapper = new ClasseCnaeMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getClasseCnaeSample1();
        var actual = classeCnaeMapper.toEntity(classeCnaeMapper.toDto(expected));
        assertClasseCnaeAllPropertiesEquals(expected, actual);
    }
}
