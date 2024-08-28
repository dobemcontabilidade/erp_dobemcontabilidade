package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class DescontoPeriodoPagamentoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static DescontoPeriodoPagamento getDescontoPeriodoPagamentoSample1() {
        return new DescontoPeriodoPagamento().id(1L);
    }

    public static DescontoPeriodoPagamento getDescontoPeriodoPagamentoSample2() {
        return new DescontoPeriodoPagamento().id(2L);
    }

    public static DescontoPeriodoPagamento getDescontoPeriodoPagamentoRandomSampleGenerator() {
        return new DescontoPeriodoPagamento().id(longCount.incrementAndGet());
    }
}
