package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PerfilAcessoUsuarioTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static PerfilAcessoUsuario getPerfilAcessoUsuarioSample1() {
        return new PerfilAcessoUsuario().id(1L).nome("nome1");
    }

    public static PerfilAcessoUsuario getPerfilAcessoUsuarioSample2() {
        return new PerfilAcessoUsuario().id(2L).nome("nome2");
    }

    public static PerfilAcessoUsuario getPerfilAcessoUsuarioRandomSampleGenerator() {
        return new PerfilAcessoUsuario().id(longCount.incrementAndGet()).nome(UUID.randomUUID().toString());
    }
}
