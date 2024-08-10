package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SecaoCnaeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static SecaoCnae getSecaoCnaeSample1() {
        return new SecaoCnae().id(1L).codigo("codigo1");
    }

    public static SecaoCnae getSecaoCnaeSample2() {
        return new SecaoCnae().id(2L).codigo("codigo2");
    }

    public static SecaoCnae getSecaoCnaeRandomSampleGenerator() {
        return new SecaoCnae().id(longCount.incrementAndGet()).codigo(UUID.randomUUID().toString());
    }
}
