package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ServicoContabilAssinaturaEmpresaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ServicoContabilAssinaturaEmpresa getServicoContabilAssinaturaEmpresaSample1() {
        return new ServicoContabilAssinaturaEmpresa().id(1L);
    }

    public static ServicoContabilAssinaturaEmpresa getServicoContabilAssinaturaEmpresaSample2() {
        return new ServicoContabilAssinaturaEmpresa().id(2L);
    }

    public static ServicoContabilAssinaturaEmpresa getServicoContabilAssinaturaEmpresaRandomSampleGenerator() {
        return new ServicoContabilAssinaturaEmpresa().id(longCount.incrementAndGet());
    }
}
