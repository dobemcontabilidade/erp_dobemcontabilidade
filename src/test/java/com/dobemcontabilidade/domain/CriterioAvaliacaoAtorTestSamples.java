package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CriterioAvaliacaoAtorTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CriterioAvaliacaoAtor getCriterioAvaliacaoAtorSample1() {
        return new CriterioAvaliacaoAtor().id(1L).descricao("descricao1");
    }

    public static CriterioAvaliacaoAtor getCriterioAvaliacaoAtorSample2() {
        return new CriterioAvaliacaoAtor().id(2L).descricao("descricao2");
    }

    public static CriterioAvaliacaoAtor getCriterioAvaliacaoAtorRandomSampleGenerator() {
        return new CriterioAvaliacaoAtor().id(longCount.incrementAndGet()).descricao(UUID.randomUUID().toString());
    }
}
