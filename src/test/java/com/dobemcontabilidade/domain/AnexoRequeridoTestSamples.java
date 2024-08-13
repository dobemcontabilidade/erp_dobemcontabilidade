package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AnexoRequeridoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AnexoRequerido getAnexoRequeridoSample1() {
        return new AnexoRequerido().id(1L).nome("nome1");
    }

    public static AnexoRequerido getAnexoRequeridoSample2() {
        return new AnexoRequerido().id(2L).nome("nome2");
    }

    public static AnexoRequerido getAnexoRequeridoRandomSampleGenerator() {
        return new AnexoRequerido().id(longCount.incrementAndGet()).nome(UUID.randomUUID().toString());
    }
}
