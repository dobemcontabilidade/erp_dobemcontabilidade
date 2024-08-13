package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ImpostoParceladoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static ImpostoParcelado getImpostoParceladoSample1() {
        return new ImpostoParcelado().id(1L).diasAtraso(1);
    }

    public static ImpostoParcelado getImpostoParceladoSample2() {
        return new ImpostoParcelado().id(2L).diasAtraso(2);
    }

    public static ImpostoParcelado getImpostoParceladoRandomSampleGenerator() {
        return new ImpostoParcelado().id(longCount.incrementAndGet()).diasAtraso(intCount.incrementAndGet());
    }
}
