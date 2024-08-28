package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RedeSocialEmpresaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static RedeSocialEmpresa getRedeSocialEmpresaSample1() {
        return new RedeSocialEmpresa().id(1L).perfil("perfil1");
    }

    public static RedeSocialEmpresa getRedeSocialEmpresaSample2() {
        return new RedeSocialEmpresa().id(2L).perfil("perfil2");
    }

    public static RedeSocialEmpresa getRedeSocialEmpresaRandomSampleGenerator() {
        return new RedeSocialEmpresa().id(longCount.incrementAndGet()).perfil(UUID.randomUUID().toString());
    }
}
