package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class EtapaFluxoModeloTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static EtapaFluxoModelo getEtapaFluxoModeloSample1() {
        return new EtapaFluxoModelo().id(1L).nome("nome1").descricao("descricao1").ordem(1);
    }

    public static EtapaFluxoModelo getEtapaFluxoModeloSample2() {
        return new EtapaFluxoModelo().id(2L).nome("nome2").descricao("descricao2").ordem(2);
    }

    public static EtapaFluxoModelo getEtapaFluxoModeloRandomSampleGenerator() {
        return new EtapaFluxoModelo()
            .id(longCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .descricao(UUID.randomUUID().toString())
            .ordem(intCount.incrementAndGet());
    }
}
