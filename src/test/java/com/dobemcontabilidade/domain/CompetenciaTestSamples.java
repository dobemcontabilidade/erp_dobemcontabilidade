package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class CompetenciaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Competencia getCompetenciaSample1() {
        return new Competencia().id(1L).nome("nome1").numero(1);
    }

    public static Competencia getCompetenciaSample2() {
        return new Competencia().id(2L).nome("nome2").numero(2);
    }

    public static Competencia getCompetenciaRandomSampleGenerator() {
        return new Competencia().id(longCount.incrementAndGet()).nome(UUID.randomUUID().toString()).numero(intCount.incrementAndGet());
    }
}
