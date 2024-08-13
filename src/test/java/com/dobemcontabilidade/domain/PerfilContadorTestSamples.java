package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PerfilContadorTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static PerfilContador getPerfilContadorSample1() {
        return new PerfilContador().id(1L).perfil("perfil1").descricao("descricao1").limiteEmpresas(1).limiteDepartamentos(1);
    }

    public static PerfilContador getPerfilContadorSample2() {
        return new PerfilContador().id(2L).perfil("perfil2").descricao("descricao2").limiteEmpresas(2).limiteDepartamentos(2);
    }

    public static PerfilContador getPerfilContadorRandomSampleGenerator() {
        return new PerfilContador()
            .id(longCount.incrementAndGet())
            .perfil(UUID.randomUUID().toString())
            .descricao(UUID.randomUUID().toString())
            .limiteEmpresas(intCount.incrementAndGet())
            .limiteDepartamentos(intCount.incrementAndGet());
    }
}
