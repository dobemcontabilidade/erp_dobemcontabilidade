package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class UsuarioErpTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static UsuarioErp getUsuarioErpSample1() {
        return new UsuarioErp().id(1L).email("email1");
    }

    public static UsuarioErp getUsuarioErpSample2() {
        return new UsuarioErp().id(2L).email("email2");
    }

    public static UsuarioErp getUsuarioErpRandomSampleGenerator() {
        return new UsuarioErp().id(longCount.incrementAndGet()).email(UUID.randomUUID().toString());
    }
}
