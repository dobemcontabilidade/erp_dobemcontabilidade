package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RamoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Ramo getRamoSample1() {
        return new Ramo().id(1L).nome("nome1");
    }

    public static Ramo getRamoSample2() {
        return new Ramo().id(2L).nome("nome2");
    }

    public static Ramo getRamoRandomSampleGenerator() {
        return new Ramo().id(longCount.incrementAndGet()).nome(UUID.randomUUID().toString());
    }
}
