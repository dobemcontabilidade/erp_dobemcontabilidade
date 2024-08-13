package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DivisaoCnaeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static DivisaoCnae getDivisaoCnaeSample1() {
        return new DivisaoCnae().id(1L).codigo("codigo1");
    }

    public static DivisaoCnae getDivisaoCnaeSample2() {
        return new DivisaoCnae().id(2L).codigo("codigo2");
    }

    public static DivisaoCnae getDivisaoCnaeRandomSampleGenerator() {
        return new DivisaoCnae().id(longCount.incrementAndGet()).codigo(UUID.randomUUID().toString());
    }
}
