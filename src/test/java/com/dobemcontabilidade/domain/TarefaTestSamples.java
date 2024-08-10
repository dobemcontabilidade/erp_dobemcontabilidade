package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class TarefaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Tarefa getTarefaSample1() {
        return new Tarefa().id(1L).titulo("titulo1").numeroDias(1).pontos(1);
    }

    public static Tarefa getTarefaSample2() {
        return new Tarefa().id(2L).titulo("titulo2").numeroDias(2).pontos(2);
    }

    public static Tarefa getTarefaRandomSampleGenerator() {
        return new Tarefa()
            .id(longCount.incrementAndGet())
            .titulo(UUID.randomUUID().toString())
            .numeroDias(intCount.incrementAndGet())
            .pontos(intCount.incrementAndGet());
    }
}
