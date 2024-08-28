package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class CertificadoDigitalEmpresaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static CertificadoDigitalEmpresa getCertificadoDigitalEmpresaSample1() {
        return new CertificadoDigitalEmpresa().id(1L).diasUso(1);
    }

    public static CertificadoDigitalEmpresa getCertificadoDigitalEmpresaSample2() {
        return new CertificadoDigitalEmpresa().id(2L).diasUso(2);
    }

    public static CertificadoDigitalEmpresa getCertificadoDigitalEmpresaRandomSampleGenerator() {
        return new CertificadoDigitalEmpresa().id(longCount.incrementAndGet()).diasUso(intCount.incrementAndGet());
    }
}
