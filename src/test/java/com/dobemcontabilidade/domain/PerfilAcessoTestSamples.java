package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PerfilAcessoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static PerfilAcesso getPerfilAcessoSample1() {
        return new PerfilAcesso().id(1L).nome("nome1").descricao("descricao1");
    }

    public static PerfilAcesso getPerfilAcessoSample2() {
        return new PerfilAcesso().id(2L).nome("nome2").descricao("descricao2");
    }

    public static PerfilAcesso getPerfilAcessoRandomSampleGenerator() {
        return new PerfilAcesso()
            .id(longCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .descricao(UUID.randomUUID().toString());
    }
}
