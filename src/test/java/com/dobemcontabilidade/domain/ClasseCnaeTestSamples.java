package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ClasseCnaeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ClasseCnae getClasseCnaeSample1() {
        return new ClasseCnae().id(1L).codigo("codigo1");
    }

    public static ClasseCnae getClasseCnaeSample2() {
        return new ClasseCnae().id(2L).codigo("codigo2");
    }

    public static ClasseCnae getClasseCnaeRandomSampleGenerator() {
        return new ClasseCnae().id(longCount.incrementAndGet()).codigo(UUID.randomUUID().toString());
    }
}
