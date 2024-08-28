package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PlanoAssinaturaContabilTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static PlanoAssinaturaContabil getPlanoAssinaturaContabilSample1() {
        return new PlanoAssinaturaContabil().id(1L).nome("nome1").sociosIsentos(1);
    }

    public static PlanoAssinaturaContabil getPlanoAssinaturaContabilSample2() {
        return new PlanoAssinaturaContabil().id(2L).nome("nome2").sociosIsentos(2);
    }

    public static PlanoAssinaturaContabil getPlanoAssinaturaContabilRandomSampleGenerator() {
        return new PlanoAssinaturaContabil()
            .id(longCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .sociosIsentos(intCount.incrementAndGet());
    }
}
