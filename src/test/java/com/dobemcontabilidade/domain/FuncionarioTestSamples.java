package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FuncionarioTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Funcionario getFuncionarioSample1() {
        return new Funcionario().id(1L).nome("nome1").ctps("ctps1").cargo("cargo1");
    }

    public static Funcionario getFuncionarioSample2() {
        return new Funcionario().id(2L).nome("nome2").ctps("ctps2").cargo("cargo2");
    }

    public static Funcionario getFuncionarioRandomSampleGenerator() {
        return new Funcionario()
            .id(longCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .ctps(UUID.randomUUID().toString())
            .cargo(UUID.randomUUID().toString());
    }
}
