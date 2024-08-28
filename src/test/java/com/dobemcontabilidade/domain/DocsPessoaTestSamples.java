package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DocsPessoaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static DocsPessoa getDocsPessoaSample1() {
        return new DocsPessoa().id(1L).tipo("tipo1");
    }

    public static DocsPessoa getDocsPessoaSample2() {
        return new DocsPessoa().id(2L).tipo("tipo2");
    }

    public static DocsPessoa getDocsPessoaRandomSampleGenerator() {
        return new DocsPessoa().id(longCount.incrementAndGet()).tipo(UUID.randomUUID().toString());
    }
}
