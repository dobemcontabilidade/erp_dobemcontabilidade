package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class CertificadoDigitalTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static CertificadoDigital getCertificadoDigitalSample1() {
        return new CertificadoDigital().id(1L).validade(1);
    }

    public static CertificadoDigital getCertificadoDigitalSample2() {
        return new CertificadoDigital().id(2L).validade(2);
    }

    public static CertificadoDigital getCertificadoDigitalRandomSampleGenerator() {
        return new CertificadoDigital().id(longCount.incrementAndGet()).validade(intCount.incrementAndGet());
    }
}
