package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class AnexoRequeridoTarefaRecorrenteTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AnexoRequeridoTarefaRecorrente getAnexoRequeridoTarefaRecorrenteSample1() {
        return new AnexoRequeridoTarefaRecorrente().id(1L);
    }

    public static AnexoRequeridoTarefaRecorrente getAnexoRequeridoTarefaRecorrenteSample2() {
        return new AnexoRequeridoTarefaRecorrente().id(2L);
    }

    public static AnexoRequeridoTarefaRecorrente getAnexoRequeridoTarefaRecorrenteRandomSampleGenerator() {
        return new AnexoRequeridoTarefaRecorrente().id(longCount.incrementAndGet());
    }
}
