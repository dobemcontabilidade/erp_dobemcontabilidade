package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AvaliacaoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Avaliacao getAvaliacaoSample1() {
        return new Avaliacao().id(1L).nome("nome1");
    }

    public static Avaliacao getAvaliacaoSample2() {
        return new Avaliacao().id(2L).nome("nome2");
    }

    public static Avaliacao getAvaliacaoRandomSampleGenerator() {
        return new Avaliacao().id(longCount.incrementAndGet()).nome(UUID.randomUUID().toString());
    }
}
