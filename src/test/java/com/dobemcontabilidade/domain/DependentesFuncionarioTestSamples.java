package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DependentesFuncionarioTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static DependentesFuncionario getDependentesFuncionarioSample1() {
        return new DependentesFuncionario().id(1L).urlCertidaoDependente("urlCertidaoDependente1").urlRgDependente("urlRgDependente1");
    }

    public static DependentesFuncionario getDependentesFuncionarioSample2() {
        return new DependentesFuncionario().id(2L).urlCertidaoDependente("urlCertidaoDependente2").urlRgDependente("urlRgDependente2");
    }

    public static DependentesFuncionario getDependentesFuncionarioRandomSampleGenerator() {
        return new DependentesFuncionario()
            .id(longCount.incrementAndGet())
            .urlCertidaoDependente(UUID.randomUUID().toString())
            .urlRgDependente(UUID.randomUUID().toString());
    }
}
