package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class TermoAdesaoContadorTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TermoAdesaoContador getTermoAdesaoContadorSample1() {
        return new TermoAdesaoContador().id(1L);
    }

    public static TermoAdesaoContador getTermoAdesaoContadorSample2() {
        return new TermoAdesaoContador().id(2L);
    }

    public static TermoAdesaoContador getTermoAdesaoContadorRandomSampleGenerator() {
        return new TermoAdesaoContador().id(longCount.incrementAndGet());
    }
}
