package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BancoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Banco getBancoSample1() {
        return new Banco().id(1L).nome("nome1").codigo("codigo1");
    }

    public static Banco getBancoSample2() {
        return new Banco().id(2L).nome("nome2").codigo("codigo2");
    }

    public static Banco getBancoRandomSampleGenerator() {
        return new Banco().id(longCount.incrementAndGet()).nome(UUID.randomUUID().toString()).codigo(UUID.randomUUID().toString());
    }
}
