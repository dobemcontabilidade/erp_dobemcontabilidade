package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AreaContabilTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AreaContabil getAreaContabilSample1() {
        return new AreaContabil().id(1L).nome("nome1").descricao("descricao1");
    }

    public static AreaContabil getAreaContabilSample2() {
        return new AreaContabil().id(2L).nome("nome2").descricao("descricao2");
    }

    public static AreaContabil getAreaContabilRandomSampleGenerator() {
        return new AreaContabil()
            .id(longCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .descricao(UUID.randomUUID().toString());
    }
}
