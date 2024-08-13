package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ProfissaoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Profissao getProfissaoSample1() {
        return new Profissao().id(1L).nome("nome1").cbo(1).categoria("categoria1");
    }

    public static Profissao getProfissaoSample2() {
        return new Profissao().id(2L).nome("nome2").cbo(2).categoria("categoria2");
    }

    public static Profissao getProfissaoRandomSampleGenerator() {
        return new Profissao()
            .id(longCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .cbo(intCount.incrementAndGet())
            .categoria(UUID.randomUUID().toString());
    }
}
