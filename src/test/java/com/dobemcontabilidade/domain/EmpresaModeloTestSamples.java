package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EmpresaModeloTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static EmpresaModelo getEmpresaModeloSample1() {
        return new EmpresaModelo().id(1L).nome("nome1").observacao("observacao1");
    }

    public static EmpresaModelo getEmpresaModeloSample2() {
        return new EmpresaModelo().id(2L).nome("nome2").observacao("observacao2");
    }

    public static EmpresaModelo getEmpresaModeloRandomSampleGenerator() {
        return new EmpresaModelo()
            .id(longCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .observacao(UUID.randomUUID().toString());
    }
}
