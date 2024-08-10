package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TipoDenunciaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TipoDenuncia getTipoDenunciaSample1() {
        return new TipoDenuncia().id(1L).tipo("tipo1");
    }

    public static TipoDenuncia getTipoDenunciaSample2() {
        return new TipoDenuncia().id(2L).tipo("tipo2");
    }

    public static TipoDenuncia getTipoDenunciaRandomSampleGenerator() {
        return new TipoDenuncia().id(longCount.incrementAndGet()).tipo(UUID.randomUUID().toString());
    }
}
