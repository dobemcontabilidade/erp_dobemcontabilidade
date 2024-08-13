package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class GrupoAcessoUsuarioEmpresaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static GrupoAcessoUsuarioEmpresa getGrupoAcessoUsuarioEmpresaSample1() {
        return new GrupoAcessoUsuarioEmpresa().id(1L).nome("nome1");
    }

    public static GrupoAcessoUsuarioEmpresa getGrupoAcessoUsuarioEmpresaSample2() {
        return new GrupoAcessoUsuarioEmpresa().id(2L).nome("nome2");
    }

    public static GrupoAcessoUsuarioEmpresa getGrupoAcessoUsuarioEmpresaRandomSampleGenerator() {
        return new GrupoAcessoUsuarioEmpresa().id(longCount.incrementAndGet()).nome(UUID.randomUUID().toString());
    }
}
