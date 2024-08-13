package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class UsuarioEmpresaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static UsuarioEmpresa getUsuarioEmpresaSample1() {
        return new UsuarioEmpresa().id(1L).email("email1");
    }

    public static UsuarioEmpresa getUsuarioEmpresaSample2() {
        return new UsuarioEmpresa().id(2L).email("email2");
    }

    public static UsuarioEmpresa getUsuarioEmpresaRandomSampleGenerator() {
        return new UsuarioEmpresa().id(longCount.incrementAndGet()).email(UUID.randomUUID().toString());
    }
}
