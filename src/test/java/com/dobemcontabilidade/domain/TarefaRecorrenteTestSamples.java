package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class TarefaRecorrenteTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static TarefaRecorrente getTarefaRecorrenteSample1() {
        return new TarefaRecorrente().id(1L).nome("nome1").descricao("descricao1").anoReferencia(1);
    }

    public static TarefaRecorrente getTarefaRecorrenteSample2() {
        return new TarefaRecorrente().id(2L).nome("nome2").descricao("descricao2").anoReferencia(2);
    }

    public static TarefaRecorrente getTarefaRecorrenteRandomSampleGenerator() {
        return new TarefaRecorrente()
            .id(longCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .descricao(UUID.randomUUID().toString())
            .anoReferencia(intCount.incrementAndGet());
    }
}
