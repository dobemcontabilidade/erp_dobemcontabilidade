package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EscolaridadeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Escolaridade getEscolaridadeSample1() {
        return new Escolaridade().id(1L).nome("nome1");
    }

    public static Escolaridade getEscolaridadeSample2() {
        return new Escolaridade().id(2L).nome("nome2");
    }

    public static Escolaridade getEscolaridadeRandomSampleGenerator() {
        return new Escolaridade().id(longCount.incrementAndGet()).nome(UUID.randomUUID().toString());
    }
}
