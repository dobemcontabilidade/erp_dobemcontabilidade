package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FeedBackUsuarioParaContadorTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static FeedBackUsuarioParaContador getFeedBackUsuarioParaContadorSample1() {
        return new FeedBackUsuarioParaContador().id(1L).comentario("comentario1");
    }

    public static FeedBackUsuarioParaContador getFeedBackUsuarioParaContadorSample2() {
        return new FeedBackUsuarioParaContador().id(2L).comentario("comentario2");
    }

    public static FeedBackUsuarioParaContador getFeedBackUsuarioParaContadorRandomSampleGenerator() {
        return new FeedBackUsuarioParaContador().id(longCount.incrementAndGet()).comentario(UUID.randomUUID().toString());
    }
}
