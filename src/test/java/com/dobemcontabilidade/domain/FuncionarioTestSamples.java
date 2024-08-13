package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class FuncionarioTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Funcionario getFuncionarioSample1() {
        return new Funcionario().id(1L).numeroPisNisPasep(1).dataOpcaoFgts("dataOpcaoFgts1").cnpjSindicato("cnpjSindicato1");
    }

    public static Funcionario getFuncionarioSample2() {
        return new Funcionario().id(2L).numeroPisNisPasep(2).dataOpcaoFgts("dataOpcaoFgts2").cnpjSindicato("cnpjSindicato2");
    }

    public static Funcionario getFuncionarioRandomSampleGenerator() {
        return new Funcionario()
            .id(longCount.incrementAndGet())
            .numeroPisNisPasep(intCount.incrementAndGet())
            .dataOpcaoFgts(UUID.randomUUID().toString())
            .cnpjSindicato(UUID.randomUUID().toString());
    }
}
