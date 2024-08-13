package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ContadorResponsavelOrdemServicoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ContadorResponsavelOrdemServico getContadorResponsavelOrdemServicoSample1() {
        return new ContadorResponsavelOrdemServico().id(1L);
    }

    public static ContadorResponsavelOrdemServico getContadorResponsavelOrdemServicoSample2() {
        return new ContadorResponsavelOrdemServico().id(2L);
    }

    public static ContadorResponsavelOrdemServico getContadorResponsavelOrdemServicoRandomSampleGenerator() {
        return new ContadorResponsavelOrdemServico().id(longCount.incrementAndGet());
    }
}
