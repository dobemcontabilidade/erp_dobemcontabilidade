package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FuncionalidadeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Funcionalidade getFuncionalidadeSample1() {
        return new Funcionalidade().id(1L).nome("nome1");
    }

    public static Funcionalidade getFuncionalidadeSample2() {
        return new Funcionalidade().id(2L).nome("nome2");
    }

    public static Funcionalidade getFuncionalidadeRandomSampleGenerator() {
        return new Funcionalidade().id(longCount.incrementAndGet()).nome(UUID.randomUUID().toString());
    }
}
