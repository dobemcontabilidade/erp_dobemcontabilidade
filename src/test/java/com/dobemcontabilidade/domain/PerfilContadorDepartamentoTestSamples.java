package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PerfilContadorDepartamentoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static PerfilContadorDepartamento getPerfilContadorDepartamentoSample1() {
        return new PerfilContadorDepartamento().id(1L).quantidadeEmpresas(1);
    }

    public static PerfilContadorDepartamento getPerfilContadorDepartamentoSample2() {
        return new PerfilContadorDepartamento().id(2L).quantidadeEmpresas(2);
    }

    public static PerfilContadorDepartamento getPerfilContadorDepartamentoRandomSampleGenerator() {
        return new PerfilContadorDepartamento().id(longCount.incrementAndGet()).quantidadeEmpresas(intCount.incrementAndGet());
    }
}
