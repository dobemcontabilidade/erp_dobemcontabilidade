package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DocumentoTarefaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static DocumentoTarefa getDocumentoTarefaSample1() {
        return new DocumentoTarefa().id(1L).nome("nome1");
    }

    public static DocumentoTarefa getDocumentoTarefaSample2() {
        return new DocumentoTarefa().id(2L).nome("nome2");
    }

    public static DocumentoTarefa getDocumentoTarefaRandomSampleGenerator() {
        return new DocumentoTarefa().id(longCount.incrementAndGet()).nome(UUID.randomUUID().toString());
    }
}
