package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ImpostoEmpresaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static ImpostoEmpresa getImpostoEmpresaSample1() {
        return new ImpostoEmpresa().id(1L).diaVencimento(1);
    }

    public static ImpostoEmpresa getImpostoEmpresaSample2() {
        return new ImpostoEmpresa().id(2L).diaVencimento(2);
    }

    public static ImpostoEmpresa getImpostoEmpresaRandomSampleGenerator() {
        return new ImpostoEmpresa().id(longCount.incrementAndGet()).diaVencimento(intCount.incrementAndGet());
    }
}
