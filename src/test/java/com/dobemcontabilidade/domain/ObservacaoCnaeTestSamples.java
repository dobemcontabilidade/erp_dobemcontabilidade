package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ObservacaoCnaeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ObservacaoCnae getObservacaoCnaeSample1() {
        return new ObservacaoCnae().id(1L);
    }

    public static ObservacaoCnae getObservacaoCnaeSample2() {
        return new ObservacaoCnae().id(2L);
    }

    public static ObservacaoCnae getObservacaoCnaeRandomSampleGenerator() {
        return new ObservacaoCnae().id(longCount.incrementAndGet());
    }
}
