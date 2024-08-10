package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class OpcaoRazaoSocialEmpresaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static OpcaoRazaoSocialEmpresa getOpcaoRazaoSocialEmpresaSample1() {
        return new OpcaoRazaoSocialEmpresa().id(1L).nome("nome1").ordem(1);
    }

    public static OpcaoRazaoSocialEmpresa getOpcaoRazaoSocialEmpresaSample2() {
        return new OpcaoRazaoSocialEmpresa().id(2L).nome("nome2").ordem(2);
    }

    public static OpcaoRazaoSocialEmpresa getOpcaoRazaoSocialEmpresaRandomSampleGenerator() {
        return new OpcaoRazaoSocialEmpresa()
            .id(longCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .ordem(intCount.incrementAndGet());
    }
}
