package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class UsuarioGestaoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static UsuarioGestao getUsuarioGestaoSample1() {
        return new UsuarioGestao().id(1L).email("email1");
    }

    public static UsuarioGestao getUsuarioGestaoSample2() {
        return new UsuarioGestao().id(2L).email("email2");
    }

    public static UsuarioGestao getUsuarioGestaoRandomSampleGenerator() {
        return new UsuarioGestao().id(longCount.incrementAndGet()).email(UUID.randomUUID().toString());
    }
}
