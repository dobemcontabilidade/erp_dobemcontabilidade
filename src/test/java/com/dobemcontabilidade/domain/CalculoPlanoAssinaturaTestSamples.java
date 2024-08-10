package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CalculoPlanoAssinaturaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CalculoPlanoAssinatura getCalculoPlanoAssinaturaSample1() {
        return new CalculoPlanoAssinatura().id(1L).codigoAtendimento("codigoAtendimento1");
    }

    public static CalculoPlanoAssinatura getCalculoPlanoAssinaturaSample2() {
        return new CalculoPlanoAssinatura().id(2L).codigoAtendimento("codigoAtendimento2");
    }

    public static CalculoPlanoAssinatura getCalculoPlanoAssinaturaRandomSampleGenerator() {
        return new CalculoPlanoAssinatura().id(longCount.incrementAndGet()).codigoAtendimento(UUID.randomUUID().toString());
    }
}
