package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DepartamentoEmpresaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static DepartamentoEmpresa getDepartamentoEmpresaSample1() {
        return new DepartamentoEmpresa().id(1L).depoimento("depoimento1").reclamacao("reclamacao1");
    }

    public static DepartamentoEmpresa getDepartamentoEmpresaSample2() {
        return new DepartamentoEmpresa().id(2L).depoimento("depoimento2").reclamacao("reclamacao2");
    }

    public static DepartamentoEmpresa getDepartamentoEmpresaRandomSampleGenerator() {
        return new DepartamentoEmpresa()
            .id(longCount.incrementAndGet())
            .depoimento(UUID.randomUUID().toString())
            .reclamacao(UUID.randomUUID().toString());
    }
}
