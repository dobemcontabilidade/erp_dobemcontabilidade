package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CobrancaEmpresaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CobrancaEmpresa getCobrancaEmpresaSample1() {
        return new CobrancaEmpresa().id(1L).urlCobranca("urlCobranca1").urlArquivo("urlArquivo1");
    }

    public static CobrancaEmpresa getCobrancaEmpresaSample2() {
        return new CobrancaEmpresa().id(2L).urlCobranca("urlCobranca2").urlArquivo("urlArquivo2");
    }

    public static CobrancaEmpresa getCobrancaEmpresaRandomSampleGenerator() {
        return new CobrancaEmpresa()
            .id(longCount.incrementAndGet())
            .urlCobranca(UUID.randomUUID().toString())
            .urlArquivo(UUID.randomUUID().toString());
    }
}
