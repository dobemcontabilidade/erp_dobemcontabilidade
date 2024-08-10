package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PeriodoPagamentoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static PeriodoPagamento getPeriodoPagamentoSample1() {
        return new PeriodoPagamento().id(1L).periodo("periodo1").numeroDias(1).idPlanGnet("idPlanGnet1");
    }

    public static PeriodoPagamento getPeriodoPagamentoSample2() {
        return new PeriodoPagamento().id(2L).periodo("periodo2").numeroDias(2).idPlanGnet("idPlanGnet2");
    }

    public static PeriodoPagamento getPeriodoPagamentoRandomSampleGenerator() {
        return new PeriodoPagamento()
            .id(longCount.incrementAndGet())
            .periodo(UUID.randomUUID().toString())
            .numeroDias(intCount.incrementAndGet())
            .idPlanGnet(UUID.randomUUID().toString());
    }
}
