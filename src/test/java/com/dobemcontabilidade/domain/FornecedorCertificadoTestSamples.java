package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FornecedorCertificadoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static FornecedorCertificado getFornecedorCertificadoSample1() {
        return new FornecedorCertificado().id(1L).razaoSocial("razaoSocial1").sigla("sigla1");
    }

    public static FornecedorCertificado getFornecedorCertificadoSample2() {
        return new FornecedorCertificado().id(2L).razaoSocial("razaoSocial2").sigla("sigla2");
    }

    public static FornecedorCertificado getFornecedorCertificadoRandomSampleGenerator() {
        return new FornecedorCertificado()
            .id(longCount.incrementAndGet())
            .razaoSocial(UUID.randomUUID().toString())
            .sigla(UUID.randomUUID().toString());
    }
}
