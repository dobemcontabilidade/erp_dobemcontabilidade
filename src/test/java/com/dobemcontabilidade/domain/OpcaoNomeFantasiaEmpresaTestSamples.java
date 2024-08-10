package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class OpcaoNomeFantasiaEmpresaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static OpcaoNomeFantasiaEmpresa getOpcaoNomeFantasiaEmpresaSample1() {
        return new OpcaoNomeFantasiaEmpresa().id(1L).nome("nome1").ordem(1);
    }

    public static OpcaoNomeFantasiaEmpresa getOpcaoNomeFantasiaEmpresaSample2() {
        return new OpcaoNomeFantasiaEmpresa().id(2L).nome("nome2").ordem(2);
    }

    public static OpcaoNomeFantasiaEmpresa getOpcaoNomeFantasiaEmpresaRandomSampleGenerator() {
        return new OpcaoNomeFantasiaEmpresa()
            .id(longCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .ordem(intCount.incrementAndGet());
    }
}
