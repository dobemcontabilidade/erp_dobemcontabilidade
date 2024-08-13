package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BancoPessoaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static BancoPessoa getBancoPessoaSample1() {
        return new BancoPessoa().id(1L).agencia("agencia1").conta("conta1");
    }

    public static BancoPessoa getBancoPessoaSample2() {
        return new BancoPessoa().id(2L).agencia("agencia2").conta("conta2");
    }

    public static BancoPessoa getBancoPessoaRandomSampleGenerator() {
        return new BancoPessoa().id(longCount.incrementAndGet()).agencia(UUID.randomUUID().toString()).conta(UUID.randomUUID().toString());
    }
}
