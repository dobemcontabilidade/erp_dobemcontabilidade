package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EnquadramentoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Enquadramento getEnquadramentoSample1() {
        return new Enquadramento().id(1L).nome("nome1").sigla("sigla1");
    }

    public static Enquadramento getEnquadramentoSample2() {
        return new Enquadramento().id(2L).nome("nome2").sigla("sigla2");
    }

    public static Enquadramento getEnquadramentoRandomSampleGenerator() {
        return new Enquadramento().id(longCount.incrementAndGet()).nome(UUID.randomUUID().toString()).sigla(UUID.randomUUID().toString());
    }
}
