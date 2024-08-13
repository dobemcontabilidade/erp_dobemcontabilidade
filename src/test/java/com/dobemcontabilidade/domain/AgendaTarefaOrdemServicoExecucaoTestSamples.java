package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class AgendaTarefaOrdemServicoExecucaoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AgendaTarefaOrdemServicoExecucao getAgendaTarefaOrdemServicoExecucaoSample1() {
        return new AgendaTarefaOrdemServicoExecucao().id(1L);
    }

    public static AgendaTarefaOrdemServicoExecucao getAgendaTarefaOrdemServicoExecucaoSample2() {
        return new AgendaTarefaOrdemServicoExecucao().id(2L);
    }

    public static AgendaTarefaOrdemServicoExecucao getAgendaTarefaOrdemServicoExecucaoRandomSampleGenerator() {
        return new AgendaTarefaOrdemServicoExecucao().id(longCount.incrementAndGet());
    }
}
