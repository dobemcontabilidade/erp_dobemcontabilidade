package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ContadorTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Contador getContadorSample1() {
        return new Contador().id(1L).nome("nome1").crc("crc1").limiteEmpresas(1).limiteAreaContabils(1).limiteDepartamentos(1);
    }

    public static Contador getContadorSample2() {
        return new Contador().id(2L).nome("nome2").crc("crc2").limiteEmpresas(2).limiteAreaContabils(2).limiteDepartamentos(2);
    }

    public static Contador getContadorRandomSampleGenerator() {
        return new Contador()
            .id(longCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .crc(UUID.randomUUID().toString())
            .limiteEmpresas(intCount.incrementAndGet())
            .limiteAreaContabils(intCount.incrementAndGet())
            .limiteDepartamentos(intCount.incrementAndGet());
    }
}
