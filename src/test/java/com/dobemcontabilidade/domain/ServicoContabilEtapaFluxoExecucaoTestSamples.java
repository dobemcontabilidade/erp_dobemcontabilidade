package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ServicoContabilEtapaFluxoExecucaoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static ServicoContabilEtapaFluxoExecucao getServicoContabilEtapaFluxoExecucaoSample1() {
        return new ServicoContabilEtapaFluxoExecucao().id(1L).ordem(1).prazo(1);
    }

    public static ServicoContabilEtapaFluxoExecucao getServicoContabilEtapaFluxoExecucaoSample2() {
        return new ServicoContabilEtapaFluxoExecucao().id(2L).ordem(2).prazo(2);
    }

    public static ServicoContabilEtapaFluxoExecucao getServicoContabilEtapaFluxoExecucaoRandomSampleGenerator() {
        return new ServicoContabilEtapaFluxoExecucao()
            .id(longCount.incrementAndGet())
            .ordem(intCount.incrementAndGet())
            .prazo(intCount.incrementAndGet());
    }
}
