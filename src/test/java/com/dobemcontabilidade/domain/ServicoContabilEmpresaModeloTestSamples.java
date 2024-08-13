package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class ServicoContabilEmpresaModeloTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ServicoContabilEmpresaModelo getServicoContabilEmpresaModeloSample1() {
        return new ServicoContabilEmpresaModelo().id(1L);
    }

    public static ServicoContabilEmpresaModelo getServicoContabilEmpresaModeloSample2() {
        return new ServicoContabilEmpresaModelo().id(2L);
    }

    public static ServicoContabilEmpresaModelo getServicoContabilEmpresaModeloRandomSampleGenerator() {
        return new ServicoContabilEmpresaModelo().id(longCount.incrementAndGet());
    }
}
