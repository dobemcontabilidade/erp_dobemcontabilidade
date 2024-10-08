package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TermoContratoContabilTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TermoContratoContabil getTermoContratoContabilSample1() {
        return new TermoContratoContabil().id(1L).documento("documento1").titulo("titulo1");
    }

    public static TermoContratoContabil getTermoContratoContabilSample2() {
        return new TermoContratoContabil().id(2L).documento("documento2").titulo("titulo2");
    }

    public static TermoContratoContabil getTermoContratoContabilRandomSampleGenerator() {
        return new TermoContratoContabil()
            .id(longCount.incrementAndGet())
            .documento(UUID.randomUUID().toString())
            .titulo(UUID.randomUUID().toString());
    }
}
