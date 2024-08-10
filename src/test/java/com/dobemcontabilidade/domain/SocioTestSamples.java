package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SocioTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Socio getSocioSample1() {
        return new Socio().id(1L).nome("nome1");
    }

    public static Socio getSocioSample2() {
        return new Socio().id(2L).nome("nome2");
    }

    public static Socio getSocioRandomSampleGenerator() {
        return new Socio().id(longCount.incrementAndGet()).nome(UUID.randomUUID().toString());
    }
}
