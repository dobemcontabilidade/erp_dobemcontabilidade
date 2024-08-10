package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PerfilRedeSocialTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static PerfilRedeSocial getPerfilRedeSocialSample1() {
        return new PerfilRedeSocial().id(1L).rede("rede1").urlPerfil("urlPerfil1");
    }

    public static PerfilRedeSocial getPerfilRedeSocialSample2() {
        return new PerfilRedeSocial().id(2L).rede("rede2").urlPerfil("urlPerfil2");
    }

    public static PerfilRedeSocial getPerfilRedeSocialRandomSampleGenerator() {
        return new PerfilRedeSocial()
            .id(longCount.incrementAndGet())
            .rede(UUID.randomUUID().toString())
            .urlPerfil(UUID.randomUUID().toString());
    }
}
