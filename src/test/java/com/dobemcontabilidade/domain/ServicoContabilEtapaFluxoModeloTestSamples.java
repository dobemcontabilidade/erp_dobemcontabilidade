package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ServicoContabilEtapaFluxoModeloTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static ServicoContabilEtapaFluxoModelo getServicoContabilEtapaFluxoModeloSample1() {
        return new ServicoContabilEtapaFluxoModelo().id(1L).ordem(1).prazo(1);
    }

    public static ServicoContabilEtapaFluxoModelo getServicoContabilEtapaFluxoModeloSample2() {
        return new ServicoContabilEtapaFluxoModelo().id(2L).ordem(2).prazo(2);
    }

    public static ServicoContabilEtapaFluxoModelo getServicoContabilEtapaFluxoModeloRandomSampleGenerator() {
        return new ServicoContabilEtapaFluxoModelo()
            .id(longCount.incrementAndGet())
            .ordem(intCount.incrementAndGet())
            .prazo(intCount.incrementAndGet());
    }
}
