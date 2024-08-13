package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AnexoOrdemServicoExecucaoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AnexoOrdemServicoExecucao getAnexoOrdemServicoExecucaoSample1() {
        return new AnexoOrdemServicoExecucao().id(1L).url("url1").descricao("descricao1");
    }

    public static AnexoOrdemServicoExecucao getAnexoOrdemServicoExecucaoSample2() {
        return new AnexoOrdemServicoExecucao().id(2L).url("url2").descricao("descricao2");
    }

    public static AnexoOrdemServicoExecucao getAnexoOrdemServicoExecucaoRandomSampleGenerator() {
        return new AnexoOrdemServicoExecucao()
            .id(longCount.incrementAndGet())
            .url(UUID.randomUUID().toString())
            .descricao(UUID.randomUUID().toString());
    }
}
