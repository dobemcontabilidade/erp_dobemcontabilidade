package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SistemaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Sistema getSistemaSample1() {
        return new Sistema().id(1L).nome("nome1").descricao("descricao1");
    }

    public static Sistema getSistemaSample2() {
        return new Sistema().id(2L).nome("nome2").descricao("descricao2");
    }

    public static Sistema getSistemaRandomSampleGenerator() {
        return new Sistema().id(longCount.incrementAndGet()).nome(UUID.randomUUID().toString()).descricao(UUID.randomUUID().toString());
    }
}
