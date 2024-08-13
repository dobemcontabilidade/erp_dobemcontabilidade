package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FluxoModeloTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static FluxoModelo getFluxoModeloSample1() {
        return new FluxoModelo().id(1L).nome("nome1").descricao("descricao1");
    }

    public static FluxoModelo getFluxoModeloSample2() {
        return new FluxoModelo().id(2L).nome("nome2").descricao("descricao2");
    }

    public static FluxoModelo getFluxoModeloRandomSampleGenerator() {
        return new FluxoModelo().id(longCount.incrementAndGet()).nome(UUID.randomUUID().toString()).descricao(UUID.randomUUID().toString());
    }
}
