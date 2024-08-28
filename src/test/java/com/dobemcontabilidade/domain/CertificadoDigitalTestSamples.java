package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CertificadoDigitalTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CertificadoDigital getCertificadoDigitalSample1() {
        return new CertificadoDigital().id(1L).nome("nome1").sigla("sigla1");
    }

    public static CertificadoDigital getCertificadoDigitalSample2() {
        return new CertificadoDigital().id(2L).nome("nome2").sigla("sigla2");
    }

    public static CertificadoDigital getCertificadoDigitalRandomSampleGenerator() {
        return new CertificadoDigital()
            .id(longCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .sigla(UUID.randomUUID().toString());
    }
}
