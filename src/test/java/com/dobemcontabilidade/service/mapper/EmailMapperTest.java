package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.EmailAsserts.*;
import static com.dobemcontabilidade.domain.EmailTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmailMapperTest {

    private EmailMapper emailMapper;

    @BeforeEach
    void setUp() {
        emailMapper = new EmailMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getEmailSample1();
        var actual = emailMapper.toEntity(emailMapper.toDto(expected));
        assertEmailAllPropertiesEquals(expected, actual);
    }
}
