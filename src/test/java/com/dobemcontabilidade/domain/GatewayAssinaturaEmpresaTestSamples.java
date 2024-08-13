package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class GatewayAssinaturaEmpresaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static GatewayAssinaturaEmpresa getGatewayAssinaturaEmpresaSample1() {
        return new GatewayAssinaturaEmpresa().id(1L).codigoAssinatura("codigoAssinatura1");
    }

    public static GatewayAssinaturaEmpresa getGatewayAssinaturaEmpresaSample2() {
        return new GatewayAssinaturaEmpresa().id(2L).codigoAssinatura("codigoAssinatura2");
    }

    public static GatewayAssinaturaEmpresa getGatewayAssinaturaEmpresaRandomSampleGenerator() {
        return new GatewayAssinaturaEmpresa().id(longCount.incrementAndGet()).codigoAssinatura(UUID.randomUUID().toString());
    }
}
