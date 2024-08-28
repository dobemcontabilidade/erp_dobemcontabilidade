package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class RedeSocialTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static RedeSocial getRedeSocialSample1() {
        return new RedeSocial().id(1L).nome("nome1").logo("logo1");
    }

    public static RedeSocial getRedeSocialSample2() {
        return new RedeSocial().id(2L).nome("nome2").logo("logo2");
    }

    public static RedeSocial getRedeSocialRandomSampleGenerator() {
        return new RedeSocial().id(longCount.incrementAndGet()).nome(UUID.randomUUID().toString()).logo(UUID.randomUUID().toString());
    }
}
