package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EmpresaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Empresa getEmpresaSample1() {
        return new Empresa().id(1L).razaoSocial("razaoSocial1").urlContratoSocial("urlContratoSocial1").cnae("cnae1");
    }

    public static Empresa getEmpresaSample2() {
        return new Empresa().id(2L).razaoSocial("razaoSocial2").urlContratoSocial("urlContratoSocial2").cnae("cnae2");
    }

    public static Empresa getEmpresaRandomSampleGenerator() {
        return new Empresa()
            .id(longCount.incrementAndGet())
            .razaoSocial(UUID.randomUUID().toString())
            .urlContratoSocial(UUID.randomUUID().toString())
            .cnae(UUID.randomUUID().toString());
    }
}
