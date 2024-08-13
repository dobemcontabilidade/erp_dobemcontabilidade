package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class AgendaContadorConfigTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AgendaContadorConfig getAgendaContadorConfigSample1() {
        return new AgendaContadorConfig().id(1L);
    }

    public static AgendaContadorConfig getAgendaContadorConfigSample2() {
        return new AgendaContadorConfig().id(2L);
    }

    public static AgendaContadorConfig getAgendaContadorConfigRandomSampleGenerator() {
        return new AgendaContadorConfig().id(longCount.incrementAndGet());
    }
}
