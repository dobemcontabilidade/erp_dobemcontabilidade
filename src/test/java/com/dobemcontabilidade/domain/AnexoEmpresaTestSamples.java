package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class AnexoEmpresaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AnexoEmpresa getAnexoEmpresaSample1() {
        return new AnexoEmpresa().id(1L);
    }

    public static AnexoEmpresa getAnexoEmpresaSample2() {
        return new AnexoEmpresa().id(2L);
    }

    public static AnexoEmpresa getAnexoEmpresaRandomSampleGenerator() {
        return new AnexoEmpresa().id(longCount.incrementAndGet());
    }
}
