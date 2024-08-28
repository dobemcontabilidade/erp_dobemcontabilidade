package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DocsEmpresaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static DocsEmpresa getDocsEmpresaSample1() {
        return new DocsEmpresa().id(1L).documento("documento1").orgaoEmissor("orgaoEmissor1");
    }

    public static DocsEmpresa getDocsEmpresaSample2() {
        return new DocsEmpresa().id(2L).documento("documento2").orgaoEmissor("orgaoEmissor2");
    }

    public static DocsEmpresa getDocsEmpresaRandomSampleGenerator() {
        return new DocsEmpresa()
            .id(longCount.incrementAndGet())
            .documento(UUID.randomUUID().toString())
            .orgaoEmissor(UUID.randomUUID().toString());
    }
}
