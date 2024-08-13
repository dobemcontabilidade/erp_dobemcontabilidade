package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class AreaContabilAssinaturaEmpresaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AreaContabilAssinaturaEmpresa getAreaContabilAssinaturaEmpresaSample1() {
        return new AreaContabilAssinaturaEmpresa().id(1L);
    }

    public static AreaContabilAssinaturaEmpresa getAreaContabilAssinaturaEmpresaSample2() {
        return new AreaContabilAssinaturaEmpresa().id(2L);
    }

    public static AreaContabilAssinaturaEmpresa getAreaContabilAssinaturaEmpresaRandomSampleGenerator() {
        return new AreaContabilAssinaturaEmpresa().id(longCount.incrementAndGet());
    }
}
