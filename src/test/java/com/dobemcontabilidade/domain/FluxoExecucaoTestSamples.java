package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FluxoExecucaoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static FluxoExecucao getFluxoExecucaoSample1() {
        return new FluxoExecucao().id(1L).nome("nome1").descricao("descricao1");
    }

    public static FluxoExecucao getFluxoExecucaoSample2() {
        return new FluxoExecucao().id(2L).nome("nome2").descricao("descricao2");
    }

    public static FluxoExecucao getFluxoExecucaoRandomSampleGenerator() {
        return new FluxoExecucao()
            .id(longCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .descricao(UUID.randomUUID().toString());
    }
}
