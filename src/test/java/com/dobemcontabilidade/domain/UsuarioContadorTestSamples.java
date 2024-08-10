package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class UsuarioContadorTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static UsuarioContador getUsuarioContadorSample1() {
        return new UsuarioContador().id(1L).email("email1");
    }

    public static UsuarioContador getUsuarioContadorSample2() {
        return new UsuarioContador().id(2L).email("email2");
    }

    public static UsuarioContador getUsuarioContadorRandomSampleGenerator() {
        return new UsuarioContador().id(longCount.incrementAndGet()).email(UUID.randomUUID().toString());
    }
}
