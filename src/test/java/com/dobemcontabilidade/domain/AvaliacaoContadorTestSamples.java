package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class AvaliacaoContadorTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AvaliacaoContador getAvaliacaoContadorSample1() {
        return new AvaliacaoContador().id(1L);
    }

    public static AvaliacaoContador getAvaliacaoContadorSample2() {
        return new AvaliacaoContador().id(2L);
    }

    public static AvaliacaoContador getAvaliacaoContadorRandomSampleGenerator() {
        return new AvaliacaoContador().id(longCount.incrementAndGet());
    }
}
