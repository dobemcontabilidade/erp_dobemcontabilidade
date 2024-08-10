package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AreaContabilContadorTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AreaContabilContador getAreaContabilContadorSample1() {
        return new AreaContabilContador().id(1L).descricaoExperiencia("descricaoExperiencia1");
    }

    public static AreaContabilContador getAreaContabilContadorSample2() {
        return new AreaContabilContador().id(2L).descricaoExperiencia("descricaoExperiencia2");
    }

    public static AreaContabilContador getAreaContabilContadorRandomSampleGenerator() {
        return new AreaContabilContador().id(longCount.incrementAndGet()).descricaoExperiencia(UUID.randomUUID().toString());
    }
}
