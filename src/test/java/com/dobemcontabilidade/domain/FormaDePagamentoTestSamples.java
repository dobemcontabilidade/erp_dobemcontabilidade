package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FormaDePagamentoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static FormaDePagamento getFormaDePagamentoSample1() {
        return new FormaDePagamento().id(1L).forma("forma1");
    }

    public static FormaDePagamento getFormaDePagamentoSample2() {
        return new FormaDePagamento().id(2L).forma("forma2");
    }

    public static FormaDePagamento getFormaDePagamentoRandomSampleGenerator() {
        return new FormaDePagamento().id(longCount.incrementAndGet()).forma(UUID.randomUUID().toString());
    }
}
