package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AreaContabilEmpresaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AreaContabilEmpresa getAreaContabilEmpresaSample1() {
        return new AreaContabilEmpresa().id(1L).depoimento("depoimento1").reclamacao("reclamacao1");
    }

    public static AreaContabilEmpresa getAreaContabilEmpresaSample2() {
        return new AreaContabilEmpresa().id(2L).depoimento("depoimento2").reclamacao("reclamacao2");
    }

    public static AreaContabilEmpresa getAreaContabilEmpresaRandomSampleGenerator() {
        return new AreaContabilEmpresa()
            .id(longCount.incrementAndGet())
            .depoimento(UUID.randomUUID().toString())
            .reclamacao(UUID.randomUUID().toString());
    }
}
