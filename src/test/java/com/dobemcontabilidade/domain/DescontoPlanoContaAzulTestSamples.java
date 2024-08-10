package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class DescontoPlanoContaAzulTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static DescontoPlanoContaAzul getDescontoPlanoContaAzulSample1() {
        return new DescontoPlanoContaAzul().id(1L);
    }

    public static DescontoPlanoContaAzul getDescontoPlanoContaAzulSample2() {
        return new DescontoPlanoContaAzul().id(2L);
    }

    public static DescontoPlanoContaAzul getDescontoPlanoContaAzulRandomSampleGenerator() {
        return new DescontoPlanoContaAzul().id(longCount.incrementAndGet());
    }
}
