package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class PagamentoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Pagamento getPagamentoSample1() {
        return new Pagamento().id(1L);
    }

    public static Pagamento getPagamentoSample2() {
        return new Pagamento().id(2L);
    }

    public static Pagamento getPagamentoRandomSampleGenerator() {
        return new Pagamento().id(longCount.incrementAndGet());
    }
}
