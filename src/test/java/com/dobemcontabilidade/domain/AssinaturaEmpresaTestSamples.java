package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class AssinaturaEmpresaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static AssinaturaEmpresa getAssinaturaEmpresaSample1() {
        return new AssinaturaEmpresa().id(1L).codigoAssinatura("codigoAssinatura1").diaVencimento(1);
    }

    public static AssinaturaEmpresa getAssinaturaEmpresaSample2() {
        return new AssinaturaEmpresa().id(2L).codigoAssinatura("codigoAssinatura2").diaVencimento(2);
    }

    public static AssinaturaEmpresa getAssinaturaEmpresaRandomSampleGenerator() {
        return new AssinaturaEmpresa()
            .id(longCount.incrementAndGet())
            .codigoAssinatura(UUID.randomUUID().toString())
            .diaVencimento(intCount.incrementAndGet());
    }
}
