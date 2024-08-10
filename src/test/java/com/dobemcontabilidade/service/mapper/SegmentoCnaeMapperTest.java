package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.SegmentoCnaeAsserts.*;
import static com.dobemcontabilidade.domain.SegmentoCnaeTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SegmentoCnaeMapperTest {

    private SegmentoCnaeMapper segmentoCnaeMapper;

    @BeforeEach
    void setUp() {
        segmentoCnaeMapper = new SegmentoCnaeMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getSegmentoCnaeSample1();
        var actual = segmentoCnaeMapper.toEntity(segmentoCnaeMapper.toDto(expected));
        assertSegmentoCnaeAllPropertiesEquals(expected, actual);
    }
}
