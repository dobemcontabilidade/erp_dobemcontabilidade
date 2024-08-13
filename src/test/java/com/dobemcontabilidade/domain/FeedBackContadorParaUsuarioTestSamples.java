package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FeedBackContadorParaUsuarioTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static FeedBackContadorParaUsuario getFeedBackContadorParaUsuarioSample1() {
        return new FeedBackContadorParaUsuario().id(1L).comentario("comentario1");
    }

    public static FeedBackContadorParaUsuario getFeedBackContadorParaUsuarioSample2() {
        return new FeedBackContadorParaUsuario().id(2L).comentario("comentario2");
    }

    public static FeedBackContadorParaUsuario getFeedBackContadorParaUsuarioRandomSampleGenerator() {
        return new FeedBackContadorParaUsuario().id(longCount.incrementAndGet()).comentario(UUID.randomUUID().toString());
    }
}
