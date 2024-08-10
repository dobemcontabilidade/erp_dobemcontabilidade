package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PlanoContaAzulTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static PlanoContaAzul getPlanoContaAzulSample1() {
        return new PlanoContaAzul().id(1L).nome("nome1").usuarios(1).boletos(1).notaFiscalProduto(1).notaFiscalServico(1).notaFiscalCe(1);
    }

    public static PlanoContaAzul getPlanoContaAzulSample2() {
        return new PlanoContaAzul().id(2L).nome("nome2").usuarios(2).boletos(2).notaFiscalProduto(2).notaFiscalServico(2).notaFiscalCe(2);
    }

    public static PlanoContaAzul getPlanoContaAzulRandomSampleGenerator() {
        return new PlanoContaAzul()
            .id(longCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .usuarios(intCount.incrementAndGet())
            .boletos(intCount.incrementAndGet())
            .notaFiscalProduto(intCount.incrementAndGet())
            .notaFiscalServico(intCount.incrementAndGet())
            .notaFiscalCe(intCount.incrementAndGet());
    }
}
