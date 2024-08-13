package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ServicoContabilTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static ServicoContabil getServicoContabilSample1() {
        return new ServicoContabil()
            .id(1L)
            .nome("nome1")
            .descricao("descricao1")
            .diasExecucao(1)
            .periodoExecucao(1)
            .diaLegal(1)
            .mesLegal(1);
    }

    public static ServicoContabil getServicoContabilSample2() {
        return new ServicoContabil()
            .id(2L)
            .nome("nome2")
            .descricao("descricao2")
            .diasExecucao(2)
            .periodoExecucao(2)
            .diaLegal(2)
            .mesLegal(2);
    }

    public static ServicoContabil getServicoContabilRandomSampleGenerator() {
        return new ServicoContabil()
            .id(longCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .descricao(UUID.randomUUID().toString())
            .diasExecucao(intCount.incrementAndGet())
            .periodoExecucao(intCount.incrementAndGet())
            .diaLegal(intCount.incrementAndGet())
            .mesLegal(intCount.incrementAndGet());
    }
}
