package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class OrdemServicoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static OrdemServico getOrdemServicoSample1() {
        return new OrdemServico().id(1L).descricao("descricao1");
    }

    public static OrdemServico getOrdemServicoSample2() {
        return new OrdemServico().id(2L).descricao("descricao2");
    }

    public static OrdemServico getOrdemServicoRandomSampleGenerator() {
        return new OrdemServico().id(longCount.incrementAndGet()).descricao(UUID.randomUUID().toString());
    }
}
