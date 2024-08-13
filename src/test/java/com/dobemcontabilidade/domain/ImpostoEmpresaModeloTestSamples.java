package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ImpostoEmpresaModeloTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ImpostoEmpresaModelo getImpostoEmpresaModeloSample1() {
        return new ImpostoEmpresaModelo().id(1L).nome("nome1").observacao("observacao1");
    }

    public static ImpostoEmpresaModelo getImpostoEmpresaModeloSample2() {
        return new ImpostoEmpresaModelo().id(2L).nome("nome2").observacao("observacao2");
    }

    public static ImpostoEmpresaModelo getImpostoEmpresaModeloRandomSampleGenerator() {
        return new ImpostoEmpresaModelo()
            .id(longCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .observacao(UUID.randomUUID().toString());
    }
}
