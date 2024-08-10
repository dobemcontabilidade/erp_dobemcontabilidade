package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PrazoAssinaturaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static PrazoAssinatura getPrazoAssinaturaSample1() {
        return new PrazoAssinatura().id(1L).prazo("prazo1").meses(1);
    }

    public static PrazoAssinatura getPrazoAssinaturaSample2() {
        return new PrazoAssinatura().id(2L).prazo("prazo2").meses(2);
    }

    public static PrazoAssinatura getPrazoAssinaturaRandomSampleGenerator() {
        return new PrazoAssinatura().id(longCount.incrementAndGet()).prazo(UUID.randomUUID().toString()).meses(intCount.incrementAndGet());
    }
}
