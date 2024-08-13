package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PermisaoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Permisao getPermisaoSample1() {
        return new Permisao().id(1L).nome("nome1").descricao("descricao1").label("label1");
    }

    public static Permisao getPermisaoSample2() {
        return new Permisao().id(2L).nome("nome2").descricao("descricao2").label("label2");
    }

    public static Permisao getPermisaoRandomSampleGenerator() {
        return new Permisao()
            .id(longCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .descricao(UUID.randomUUID().toString())
            .label(UUID.randomUUID().toString());
    }
}
