package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class TarefaEmpresaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TarefaEmpresa getTarefaEmpresaSample1() {
        return new TarefaEmpresa().id(1L);
    }

    public static TarefaEmpresa getTarefaEmpresaSample2() {
        return new TarefaEmpresa().id(2L);
    }

    public static TarefaEmpresa getTarefaEmpresaRandomSampleGenerator() {
        return new TarefaEmpresa().id(longCount.incrementAndGet());
    }
}
