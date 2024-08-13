package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AtorAvaliadoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AtorAvaliado getAtorAvaliadoSample1() {
        return new AtorAvaliado().id(1L).nome("nome1").descricao("descricao1");
    }

    public static AtorAvaliado getAtorAvaliadoSample2() {
        return new AtorAvaliado().id(2L).nome("nome2").descricao("descricao2");
    }

    public static AtorAvaliado getAtorAvaliadoRandomSampleGenerator() {
        return new AtorAvaliado()
            .id(longCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .descricao(UUID.randomUUID().toString());
    }
}
