package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SegmentoCnaeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static SegmentoCnae getSegmentoCnaeSample1() {
        return new SegmentoCnae().id(1L).nome("nome1").icon("icon1").imagem("imagem1");
    }

    public static SegmentoCnae getSegmentoCnaeSample2() {
        return new SegmentoCnae().id(2L).nome("nome2").icon("icon2").imagem("imagem2");
    }

    public static SegmentoCnae getSegmentoCnaeRandomSampleGenerator() {
        return new SegmentoCnae()
            .id(longCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .icon(UUID.randomUUID().toString())
            .imagem(UUID.randomUUID().toString());
    }
}
