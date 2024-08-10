package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DepartamentoContadorTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static DepartamentoContador getDepartamentoContadorSample1() {
        return new DepartamentoContador().id(1L).descricaoExperiencia("descricaoExperiencia1");
    }

    public static DepartamentoContador getDepartamentoContadorSample2() {
        return new DepartamentoContador().id(2L).descricaoExperiencia("descricaoExperiencia2");
    }

    public static DepartamentoContador getDepartamentoContadorRandomSampleGenerator() {
        return new DepartamentoContador().id(longCount.incrementAndGet()).descricaoExperiencia(UUID.randomUUID().toString());
    }
}
