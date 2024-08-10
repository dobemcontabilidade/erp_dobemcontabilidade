package com.dobemcontabilidade.service.mapper;

import static com.dobemcontabilidade.domain.CertificadoDigitalAsserts.*;
import static com.dobemcontabilidade.domain.CertificadoDigitalTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CertificadoDigitalMapperTest {

    private CertificadoDigitalMapper certificadoDigitalMapper;

    @BeforeEach
    void setUp() {
        certificadoDigitalMapper = new CertificadoDigitalMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCertificadoDigitalSample1();
        var actual = certificadoDigitalMapper.toEntity(certificadoDigitalMapper.toDto(expected));
        assertCertificadoDigitalAllPropertiesEquals(expected, actual);
    }
}
