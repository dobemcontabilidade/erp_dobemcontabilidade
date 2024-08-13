package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class AnexoRequeridoEmpresaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AnexoRequeridoEmpresa getAnexoRequeridoEmpresaSample1() {
        return new AnexoRequeridoEmpresa().id(1L);
    }

    public static AnexoRequeridoEmpresa getAnexoRequeridoEmpresaSample2() {
        return new AnexoRequeridoEmpresa().id(2L);
    }

    public static AnexoRequeridoEmpresa getAnexoRequeridoEmpresaRandomSampleGenerator() {
        return new AnexoRequeridoEmpresa().id(longCount.incrementAndGet());
    }
}
