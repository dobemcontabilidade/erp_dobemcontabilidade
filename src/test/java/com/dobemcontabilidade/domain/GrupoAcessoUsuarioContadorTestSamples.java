package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class GrupoAcessoUsuarioContadorTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static GrupoAcessoUsuarioContador getGrupoAcessoUsuarioContadorSample1() {
        return new GrupoAcessoUsuarioContador().id(1L);
    }

    public static GrupoAcessoUsuarioContador getGrupoAcessoUsuarioContadorSample2() {
        return new GrupoAcessoUsuarioContador().id(2L);
    }

    public static GrupoAcessoUsuarioContador getGrupoAcessoUsuarioContadorRandomSampleGenerator() {
        return new GrupoAcessoUsuarioContador().id(longCount.incrementAndGet());
    }
}
