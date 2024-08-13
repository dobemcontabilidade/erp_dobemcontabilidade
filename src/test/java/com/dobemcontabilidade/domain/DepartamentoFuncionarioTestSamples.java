package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DepartamentoFuncionarioTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static DepartamentoFuncionario getDepartamentoFuncionarioSample1() {
        return new DepartamentoFuncionario().id(1L).cargo("cargo1");
    }

    public static DepartamentoFuncionario getDepartamentoFuncionarioSample2() {
        return new DepartamentoFuncionario().id(2L).cargo("cargo2");
    }

    public static DepartamentoFuncionario getDepartamentoFuncionarioRandomSampleGenerator() {
        return new DepartamentoFuncionario().id(longCount.incrementAndGet()).cargo(UUID.randomUUID().toString());
    }
}
