package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ValorBaseRamoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ValorBaseRamo getValorBaseRamoSample1() {
        return new ValorBaseRamo().id(1L);
    }

    public static ValorBaseRamo getValorBaseRamoSample2() {
        return new ValorBaseRamo().id(2L);
    }

    public static ValorBaseRamo getValorBaseRamoRandomSampleGenerator() {
        return new ValorBaseRamo().id(longCount.incrementAndGet());
    }
}
