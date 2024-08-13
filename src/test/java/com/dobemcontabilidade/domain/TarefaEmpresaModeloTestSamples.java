package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class TarefaEmpresaModeloTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TarefaEmpresaModelo getTarefaEmpresaModeloSample1() {
        return new TarefaEmpresaModelo().id(1L);
    }

    public static TarefaEmpresaModelo getTarefaEmpresaModeloSample2() {
        return new TarefaEmpresaModelo().id(2L);
    }

    public static TarefaEmpresaModelo getTarefaEmpresaModeloRandomSampleGenerator() {
        return new TarefaEmpresaModelo().id(longCount.incrementAndGet());
    }
}
