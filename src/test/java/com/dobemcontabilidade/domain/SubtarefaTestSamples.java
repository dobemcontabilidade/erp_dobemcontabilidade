package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class SubtarefaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Subtarefa getSubtarefaSample1() {
        return new Subtarefa().id(1L).ordem(1).item("item1");
    }

    public static Subtarefa getSubtarefaSample2() {
        return new Subtarefa().id(2L).ordem(2).item("item2");
    }

    public static Subtarefa getSubtarefaRandomSampleGenerator() {
        return new Subtarefa().id(longCount.incrementAndGet()).ordem(intCount.incrementAndGet()).item(UUID.randomUUID().toString());
    }
}
