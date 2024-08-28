package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class TermoContratoAssinaturaEmpresaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TermoContratoAssinaturaEmpresa getTermoContratoAssinaturaEmpresaSample1() {
        return new TermoContratoAssinaturaEmpresa().id(1L);
    }

    public static TermoContratoAssinaturaEmpresa getTermoContratoAssinaturaEmpresaSample2() {
        return new TermoContratoAssinaturaEmpresa().id(2L);
    }

    public static TermoContratoAssinaturaEmpresa getTermoContratoAssinaturaEmpresaRandomSampleGenerator() {
        return new TermoContratoAssinaturaEmpresa().id(longCount.incrementAndGet());
    }
}
