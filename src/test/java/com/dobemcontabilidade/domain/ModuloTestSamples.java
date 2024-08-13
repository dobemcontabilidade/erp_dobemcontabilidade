package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ModuloTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Modulo getModuloSample1() {
        return new Modulo().id(1L).nome("nome1").descricao("descricao1");
    }

    public static Modulo getModuloSample2() {
        return new Modulo().id(2L).nome("nome2").descricao("descricao2");
    }

    public static Modulo getModuloRandomSampleGenerator() {
        return new Modulo().id(longCount.incrementAndGet()).nome(UUID.randomUUID().toString()).descricao(UUID.randomUUID().toString());
    }
}
