package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TributacaoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Tributacao getTributacaoSample1() {
        return new Tributacao().id(1L).nome("nome1");
    }

    public static Tributacao getTributacaoSample2() {
        return new Tributacao().id(2L).nome("nome2");
    }

    public static Tributacao getTributacaoRandomSampleGenerator() {
        return new Tributacao().id(longCount.incrementAndGet()).nome(UUID.randomUUID().toString());
    }
}
