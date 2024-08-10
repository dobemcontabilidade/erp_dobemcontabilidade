package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class GrupoCnaeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static GrupoCnae getGrupoCnaeSample1() {
        return new GrupoCnae().id(1L).codigo("codigo1");
    }

    public static GrupoCnae getGrupoCnaeSample2() {
        return new GrupoCnae().id(2L).codigo("codigo2");
    }

    public static GrupoCnae getGrupoCnaeRandomSampleGenerator() {
        return new GrupoCnae().id(longCount.incrementAndGet()).codigo(UUID.randomUUID().toString());
    }
}
