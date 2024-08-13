package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EstrangeiroTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Estrangeiro getEstrangeiroSample1() {
        return new Estrangeiro().id(1L).dataChegada("dataChegada1").dataNaturalizacao("dataNaturalizacao1");
    }

    public static Estrangeiro getEstrangeiroSample2() {
        return new Estrangeiro().id(2L).dataChegada("dataChegada2").dataNaturalizacao("dataNaturalizacao2");
    }

    public static Estrangeiro getEstrangeiroRandomSampleGenerator() {
        return new Estrangeiro()
            .id(longCount.incrementAndGet())
            .dataChegada(UUID.randomUUID().toString())
            .dataNaturalizacao(UUID.randomUUID().toString());
    }
}
