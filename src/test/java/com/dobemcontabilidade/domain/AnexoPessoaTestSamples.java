package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AnexoPessoaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AnexoPessoa getAnexoPessoaSample1() {
        return new AnexoPessoa().id(1L).tipo("tipo1");
    }

    public static AnexoPessoa getAnexoPessoaSample2() {
        return new AnexoPessoa().id(2L).tipo("tipo2");
    }

    public static AnexoPessoa getAnexoPessoaRandomSampleGenerator() {
        return new AnexoPessoa().id(longCount.incrementAndGet()).tipo(UUID.randomUUID().toString());
    }
}
