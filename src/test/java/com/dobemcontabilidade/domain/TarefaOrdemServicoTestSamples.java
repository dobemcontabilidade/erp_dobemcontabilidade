package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class TarefaOrdemServicoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static TarefaOrdemServico getTarefaOrdemServicoSample1() {
        return new TarefaOrdemServico().id(1L).nome("nome1").descricao("descricao1").anoReferencia(1);
    }

    public static TarefaOrdemServico getTarefaOrdemServicoSample2() {
        return new TarefaOrdemServico().id(2L).nome("nome2").descricao("descricao2").anoReferencia(2);
    }

    public static TarefaOrdemServico getTarefaOrdemServicoRandomSampleGenerator() {
        return new TarefaOrdemServico()
            .id(longCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .descricao(UUID.randomUUID().toString())
            .anoReferencia(intCount.incrementAndGet());
    }
}
