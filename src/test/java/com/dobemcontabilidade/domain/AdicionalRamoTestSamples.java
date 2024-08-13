package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class AdicionalRamoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AdicionalRamo getAdicionalRamoSample1() {
        return new AdicionalRamo().id(1L);
    }

    public static AdicionalRamo getAdicionalRamoSample2() {
        return new AdicionalRamo().id(2L);
    }

    public static AdicionalRamo getAdicionalRamoRandomSampleGenerator() {
        return new AdicionalRamo().id(longCount.incrementAndGet());
    }
}
