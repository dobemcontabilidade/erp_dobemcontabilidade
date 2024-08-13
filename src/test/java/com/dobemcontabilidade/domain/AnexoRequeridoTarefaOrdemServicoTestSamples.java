package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class AnexoRequeridoTarefaOrdemServicoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AnexoRequeridoTarefaOrdemServico getAnexoRequeridoTarefaOrdemServicoSample1() {
        return new AnexoRequeridoTarefaOrdemServico().id(1L);
    }

    public static AnexoRequeridoTarefaOrdemServico getAnexoRequeridoTarefaOrdemServicoSample2() {
        return new AnexoRequeridoTarefaOrdemServico().id(2L);
    }

    public static AnexoRequeridoTarefaOrdemServico getAnexoRequeridoTarefaOrdemServicoRandomSampleGenerator() {
        return new AnexoRequeridoTarefaOrdemServico().id(longCount.incrementAndGet());
    }
}
