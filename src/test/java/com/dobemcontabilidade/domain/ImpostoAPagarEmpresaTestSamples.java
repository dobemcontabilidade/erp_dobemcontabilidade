package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ImpostoAPagarEmpresaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static ImpostoAPagarEmpresa getImpostoAPagarEmpresaSample1() {
        return new ImpostoAPagarEmpresa()
            .id(1L)
            .valorMulta(1)
            .urlArquivoPagamento("urlArquivoPagamento1")
            .urlArquivoComprovante("urlArquivoComprovante1");
    }

    public static ImpostoAPagarEmpresa getImpostoAPagarEmpresaSample2() {
        return new ImpostoAPagarEmpresa()
            .id(2L)
            .valorMulta(2)
            .urlArquivoPagamento("urlArquivoPagamento2")
            .urlArquivoComprovante("urlArquivoComprovante2");
    }

    public static ImpostoAPagarEmpresa getImpostoAPagarEmpresaRandomSampleGenerator() {
        return new ImpostoAPagarEmpresa()
            .id(longCount.incrementAndGet())
            .valorMulta(intCount.incrementAndGet())
            .urlArquivoPagamento(UUID.randomUUID().toString())
            .urlArquivoComprovante(UUID.randomUUID().toString());
    }
}
