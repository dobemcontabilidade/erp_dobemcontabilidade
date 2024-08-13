package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ImpostoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Imposto getImpostoSample1() {
        return new Imposto().id(1L).nome("nome1").sigla("sigla1").descricao("descricao1");
    }

    public static Imposto getImpostoSample2() {
        return new Imposto().id(2L).nome("nome2").sigla("sigla2").descricao("descricao2");
    }

    public static Imposto getImpostoRandomSampleGenerator() {
        return new Imposto()
            .id(longCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .sigla(UUID.randomUUID().toString())
            .descricao(UUID.randomUUID().toString());
    }
}
