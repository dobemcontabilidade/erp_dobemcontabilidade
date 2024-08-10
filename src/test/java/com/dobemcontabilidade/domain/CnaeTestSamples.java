package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class CnaeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Cnae getCnaeSample1() {
        return new Cnae().id(1L).codigo("codigo1").anexo(1).categoria("categoria1");
    }

    public static Cnae getCnaeSample2() {
        return new Cnae().id(2L).codigo("codigo2").anexo(2).categoria("categoria2");
    }

    public static Cnae getCnaeRandomSampleGenerator() {
        return new Cnae()
            .id(longCount.incrementAndGet())
            .codigo(UUID.randomUUID().toString())
            .anexo(intCount.incrementAndGet())
            .categoria(UUID.randomUUID().toString());
    }
}
