package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TelefoneTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Telefone getTelefoneSample1() {
        return new Telefone().id(1L).codigoArea("codigoArea1").telefone("telefone1");
    }

    public static Telefone getTelefoneSample2() {
        return new Telefone().id(2L).codigoArea("codigoArea2").telefone("telefone2");
    }

    public static Telefone getTelefoneRandomSampleGenerator() {
        return new Telefone()
            .id(longCount.incrementAndGet())
            .codigoArea(UUID.randomUUID().toString())
            .telefone(UUID.randomUUID().toString());
    }
}
