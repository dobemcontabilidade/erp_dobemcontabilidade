package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class FrequenciaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Frequencia getFrequenciaSample1() {
        return new Frequencia().id(1L).nome("nome1").numeroDias(1);
    }

    public static Frequencia getFrequenciaSample2() {
        return new Frequencia().id(2L).nome("nome2").numeroDias(2);
    }

    public static Frequencia getFrequenciaRandomSampleGenerator() {
        return new Frequencia().id(longCount.incrementAndGet()).nome(UUID.randomUUID().toString()).numeroDias(intCount.incrementAndGet());
    }
}
