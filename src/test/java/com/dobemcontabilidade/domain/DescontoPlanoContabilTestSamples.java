package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class DescontoPlanoContabilTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static DescontoPlanoContabil getDescontoPlanoContabilSample1() {
        return new DescontoPlanoContabil().id(1L);
    }

    public static DescontoPlanoContabil getDescontoPlanoContabilSample2() {
        return new DescontoPlanoContabil().id(2L);
    }

    public static DescontoPlanoContabil getDescontoPlanoContabilRandomSampleGenerator() {
        return new DescontoPlanoContabil().id(longCount.incrementAndGet());
    }
}
