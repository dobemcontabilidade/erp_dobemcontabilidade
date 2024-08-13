package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class AnexoRequeridoServicoContabilTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AnexoRequeridoServicoContabil getAnexoRequeridoServicoContabilSample1() {
        return new AnexoRequeridoServicoContabil().id(1L);
    }

    public static AnexoRequeridoServicoContabil getAnexoRequeridoServicoContabilSample2() {
        return new AnexoRequeridoServicoContabil().id(2L);
    }

    public static AnexoRequeridoServicoContabil getAnexoRequeridoServicoContabilRandomSampleGenerator() {
        return new AnexoRequeridoServicoContabil().id(longCount.incrementAndGet());
    }
}
