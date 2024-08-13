package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class GrupoAcessoEmpresaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static GrupoAcessoEmpresa getGrupoAcessoEmpresaSample1() {
        return new GrupoAcessoEmpresa().id(1L).nome("nome1");
    }

    public static GrupoAcessoEmpresa getGrupoAcessoEmpresaSample2() {
        return new GrupoAcessoEmpresa().id(2L).nome("nome2");
    }

    public static GrupoAcessoEmpresa getGrupoAcessoEmpresaRandomSampleGenerator() {
        return new GrupoAcessoEmpresa().id(longCount.incrementAndGet()).nome(UUID.randomUUID().toString());
    }
}
