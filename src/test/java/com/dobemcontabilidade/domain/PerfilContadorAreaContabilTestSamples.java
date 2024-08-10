package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PerfilContadorAreaContabilTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static PerfilContadorAreaContabil getPerfilContadorAreaContabilSample1() {
        return new PerfilContadorAreaContabil().id(1L).quantidadeEmpresas(1);
    }

    public static PerfilContadorAreaContabil getPerfilContadorAreaContabilSample2() {
        return new PerfilContadorAreaContabil().id(2L).quantidadeEmpresas(2);
    }

    public static PerfilContadorAreaContabil getPerfilContadorAreaContabilRandomSampleGenerator() {
        return new PerfilContadorAreaContabil().id(longCount.incrementAndGet()).quantidadeEmpresas(intCount.incrementAndGet());
    }
}
