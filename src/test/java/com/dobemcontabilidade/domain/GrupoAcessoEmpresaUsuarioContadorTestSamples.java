package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class GrupoAcessoEmpresaUsuarioContadorTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static GrupoAcessoEmpresaUsuarioContador getGrupoAcessoEmpresaUsuarioContadorSample1() {
        return new GrupoAcessoEmpresaUsuarioContador().id(1L).nome("nome1");
    }

    public static GrupoAcessoEmpresaUsuarioContador getGrupoAcessoEmpresaUsuarioContadorSample2() {
        return new GrupoAcessoEmpresaUsuarioContador().id(2L).nome("nome2");
    }

    public static GrupoAcessoEmpresaUsuarioContador getGrupoAcessoEmpresaUsuarioContadorRandomSampleGenerator() {
        return new GrupoAcessoEmpresaUsuarioContador().id(longCount.incrementAndGet()).nome(UUID.randomUUID().toString());
    }
}
