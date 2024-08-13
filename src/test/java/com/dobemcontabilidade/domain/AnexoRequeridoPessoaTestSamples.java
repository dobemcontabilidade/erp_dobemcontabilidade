package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class AnexoRequeridoPessoaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AnexoRequeridoPessoa getAnexoRequeridoPessoaSample1() {
        return new AnexoRequeridoPessoa().id(1L);
    }

    public static AnexoRequeridoPessoa getAnexoRequeridoPessoaSample2() {
        return new AnexoRequeridoPessoa().id(2L);
    }

    public static AnexoRequeridoPessoa getAnexoRequeridoPessoaRandomSampleGenerator() {
        return new AnexoRequeridoPessoa().id(longCount.incrementAndGet());
    }
}
