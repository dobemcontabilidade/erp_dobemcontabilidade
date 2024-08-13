package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AnexoTarefaRecorrenteExecucaoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AnexoTarefaRecorrenteExecucao getAnexoTarefaRecorrenteExecucaoSample1() {
        return new AnexoTarefaRecorrenteExecucao().id(1L).url("url1").descricao("descricao1");
    }

    public static AnexoTarefaRecorrenteExecucao getAnexoTarefaRecorrenteExecucaoSample2() {
        return new AnexoTarefaRecorrenteExecucao().id(2L).url("url2").descricao("descricao2");
    }

    public static AnexoTarefaRecorrenteExecucao getAnexoTarefaRecorrenteExecucaoRandomSampleGenerator() {
        return new AnexoTarefaRecorrenteExecucao()
            .id(longCount.incrementAndGet())
            .url(UUID.randomUUID().toString())
            .descricao(UUID.randomUUID().toString());
    }
}
