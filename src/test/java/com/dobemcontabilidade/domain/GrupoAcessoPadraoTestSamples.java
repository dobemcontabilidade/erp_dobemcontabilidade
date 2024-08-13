package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class GrupoAcessoPadraoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static GrupoAcessoPadrao getGrupoAcessoPadraoSample1() {
        return new GrupoAcessoPadrao().id(1L).nome("nome1");
    }

    public static GrupoAcessoPadrao getGrupoAcessoPadraoSample2() {
        return new GrupoAcessoPadrao().id(2L).nome("nome2");
    }

    public static GrupoAcessoPadrao getGrupoAcessoPadraoRandomSampleGenerator() {
        return new GrupoAcessoPadrao().id(longCount.incrementAndGet()).nome(UUID.randomUUID().toString());
    }
}
