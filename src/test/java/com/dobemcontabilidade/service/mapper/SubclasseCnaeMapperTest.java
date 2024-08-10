package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.SubclasseCnaeAsserts.*;
import static com.dobemcontabilidade.domain.SubclasseCnaeTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SubclasseCnaeMapperTest {

    private SubclasseCnaeMapper subclasseCnaeMapper;

    @BeforeEach
    void setUp() {
        subclasseCnaeMapper = new SubclasseCnaeMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getSubclasseCnaeSample1();
        var actual = subclasseCnaeMapper.toEntity(subclasseCnaeMapper.toDto(expected));
        assertSubclasseCnaeAllPropertiesEquals(expected, actual);
    }
}
