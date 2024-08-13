package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FuncionalidadeGrupoAcessoEmpresaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static FuncionalidadeGrupoAcessoEmpresa getFuncionalidadeGrupoAcessoEmpresaSample1() {
        return new FuncionalidadeGrupoAcessoEmpresa().id(1L).ativa("ativa1");
    }

    public static FuncionalidadeGrupoAcessoEmpresa getFuncionalidadeGrupoAcessoEmpresaSample2() {
        return new FuncionalidadeGrupoAcessoEmpresa().id(2L).ativa("ativa2");
    }

    public static FuncionalidadeGrupoAcessoEmpresa getFuncionalidadeGrupoAcessoEmpresaRandomSampleGenerator() {
        return new FuncionalidadeGrupoAcessoEmpresa().id(longCount.incrementAndGet()).ativa(UUID.randomUUID().toString());
    }
}
