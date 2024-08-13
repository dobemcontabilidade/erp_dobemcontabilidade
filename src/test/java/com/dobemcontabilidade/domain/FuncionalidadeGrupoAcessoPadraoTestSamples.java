package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class FuncionalidadeGrupoAcessoPadraoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static FuncionalidadeGrupoAcessoPadrao getFuncionalidadeGrupoAcessoPadraoSample1() {
        return new FuncionalidadeGrupoAcessoPadrao().id(1L);
    }

    public static FuncionalidadeGrupoAcessoPadrao getFuncionalidadeGrupoAcessoPadraoSample2() {
        return new FuncionalidadeGrupoAcessoPadrao().id(2L);
    }

    public static FuncionalidadeGrupoAcessoPadrao getFuncionalidadeGrupoAcessoPadraoRandomSampleGenerator() {
        return new FuncionalidadeGrupoAcessoPadrao().id(longCount.incrementAndGet());
    }
}
