package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class TarefaRecorrenteEmpresaModeloTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static TarefaRecorrenteEmpresaModelo getTarefaRecorrenteEmpresaModeloSample1() {
        return new TarefaRecorrenteEmpresaModelo().id(1L).diaAdmin(1);
    }

    public static TarefaRecorrenteEmpresaModelo getTarefaRecorrenteEmpresaModeloSample2() {
        return new TarefaRecorrenteEmpresaModelo().id(2L).diaAdmin(2);
    }

    public static TarefaRecorrenteEmpresaModelo getTarefaRecorrenteEmpresaModeloRandomSampleGenerator() {
        return new TarefaRecorrenteEmpresaModelo().id(longCount.incrementAndGet()).diaAdmin(intCount.incrementAndGet());
    }
}
