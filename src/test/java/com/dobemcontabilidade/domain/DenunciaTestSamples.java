package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DenunciaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Denuncia getDenunciaSample1() {
        return new Denuncia().id(1L).titulo("titulo1").mensagem("mensagem1");
    }

    public static Denuncia getDenunciaSample2() {
        return new Denuncia().id(2L).titulo("titulo2").mensagem("mensagem2");
    }

    public static Denuncia getDenunciaRandomSampleGenerator() {
        return new Denuncia().id(longCount.incrementAndGet()).titulo(UUID.randomUUID().toString()).mensagem(UUID.randomUUID().toString());
    }
}
