package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TermoAdesaoEmpresaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TermoAdesaoEmpresa getTermoAdesaoEmpresaSample1() {
        return new TermoAdesaoEmpresa().id(1L).urlDoc("urlDoc1");
    }

    public static TermoAdesaoEmpresa getTermoAdesaoEmpresaSample2() {
        return new TermoAdesaoEmpresa().id(2L).urlDoc("urlDoc2");
    }

    public static TermoAdesaoEmpresa getTermoAdesaoEmpresaRandomSampleGenerator() {
        return new TermoAdesaoEmpresa().id(longCount.incrementAndGet()).urlDoc(UUID.randomUUID().toString());
    }
}
