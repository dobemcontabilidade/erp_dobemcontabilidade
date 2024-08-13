package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AgendaTarefaRecorrenteExecucaoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AgendaTarefaRecorrenteExecucao getAgendaTarefaRecorrenteExecucaoSample1() {
        return new AgendaTarefaRecorrenteExecucao().id(1L).comentario("comentario1");
    }

    public static AgendaTarefaRecorrenteExecucao getAgendaTarefaRecorrenteExecucaoSample2() {
        return new AgendaTarefaRecorrenteExecucao().id(2L).comentario("comentario2");
    }

    public static AgendaTarefaRecorrenteExecucao getAgendaTarefaRecorrenteExecucaoRandomSampleGenerator() {
        return new AgendaTarefaRecorrenteExecucao().id(longCount.incrementAndGet()).comentario(UUID.randomUUID().toString());
    }
}
