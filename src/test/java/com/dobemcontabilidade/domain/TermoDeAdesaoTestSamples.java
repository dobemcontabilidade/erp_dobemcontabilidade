package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TermoDeAdesaoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TermoDeAdesao getTermoDeAdesaoSample1() {
        return new TermoDeAdesao().id(1L).titulo("titulo1");
    }

    public static TermoDeAdesao getTermoDeAdesaoSample2() {
        return new TermoDeAdesao().id(2L).titulo("titulo2");
    }

    public static TermoDeAdesao getTermoDeAdesaoRandomSampleGenerator() {
        return new TermoDeAdesao().id(longCount.incrementAndGet()).titulo(UUID.randomUUID().toString());
    }
}
