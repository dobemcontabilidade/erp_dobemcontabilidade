package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ServicoContabilOrdemServicoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ServicoContabilOrdemServico getServicoContabilOrdemServicoSample1() {
        return new ServicoContabilOrdemServico().id(1L);
    }

    public static ServicoContabilOrdemServico getServicoContabilOrdemServicoSample2() {
        return new ServicoContabilOrdemServico().id(2L);
    }

    public static ServicoContabilOrdemServico getServicoContabilOrdemServicoRandomSampleGenerator() {
        return new ServicoContabilOrdemServico().id(longCount.incrementAndGet());
    }
}
