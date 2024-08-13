package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class AdicionalEnquadramentoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AdicionalEnquadramento getAdicionalEnquadramentoSample1() {
        return new AdicionalEnquadramento().id(1L);
    }

    public static AdicionalEnquadramento getAdicionalEnquadramentoSample2() {
        return new AdicionalEnquadramento().id(2L);
    }

    public static AdicionalEnquadramento getAdicionalEnquadramentoRandomSampleGenerator() {
        return new AdicionalEnquadramento().id(longCount.incrementAndGet());
    }
}
