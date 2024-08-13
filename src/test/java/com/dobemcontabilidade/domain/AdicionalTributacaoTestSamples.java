package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class AdicionalTributacaoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AdicionalTributacao getAdicionalTributacaoSample1() {
        return new AdicionalTributacao().id(1L);
    }

    public static AdicionalTributacao getAdicionalTributacaoSample2() {
        return new AdicionalTributacao().id(2L);
    }

    public static AdicionalTributacao getAdicionalTributacaoRandomSampleGenerator() {
        return new AdicionalTributacao().id(longCount.incrementAndGet());
    }
}
