package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EsferaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Esfera getEsferaSample1() {
        return new Esfera().id(1L).nome("nome1");
    }

    public static Esfera getEsferaSample2() {
        return new Esfera().id(2L).nome("nome2");
    }

    public static Esfera getEsferaRandomSampleGenerator() {
        return new Esfera().id(longCount.incrementAndGet()).nome(UUID.randomUUID().toString());
    }
}
