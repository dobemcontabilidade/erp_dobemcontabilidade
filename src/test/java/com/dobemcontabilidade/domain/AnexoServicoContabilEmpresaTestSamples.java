package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AnexoServicoContabilEmpresaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AnexoServicoContabilEmpresa getAnexoServicoContabilEmpresaSample1() {
        return new AnexoServicoContabilEmpresa().id(1L).link("link1");
    }

    public static AnexoServicoContabilEmpresa getAnexoServicoContabilEmpresaSample2() {
        return new AnexoServicoContabilEmpresa().id(2L).link("link2");
    }

    public static AnexoServicoContabilEmpresa getAnexoServicoContabilEmpresaRandomSampleGenerator() {
        return new AnexoServicoContabilEmpresa().id(longCount.incrementAndGet()).link(UUID.randomUUID().toString());
    }
}
