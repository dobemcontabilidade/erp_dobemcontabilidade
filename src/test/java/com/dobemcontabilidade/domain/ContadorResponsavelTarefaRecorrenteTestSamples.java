package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ContadorResponsavelTarefaRecorrenteTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ContadorResponsavelTarefaRecorrente getContadorResponsavelTarefaRecorrenteSample1() {
        return new ContadorResponsavelTarefaRecorrente().id(1L);
    }

    public static ContadorResponsavelTarefaRecorrente getContadorResponsavelTarefaRecorrenteSample2() {
        return new ContadorResponsavelTarefaRecorrente().id(2L);
    }

    public static ContadorResponsavelTarefaRecorrente getContadorResponsavelTarefaRecorrenteRandomSampleGenerator() {
        return new ContadorResponsavelTarefaRecorrente().id(longCount.incrementAndGet());
    }
}
