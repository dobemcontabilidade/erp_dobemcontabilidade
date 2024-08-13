package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ParcelaImpostoAPagarTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static ParcelaImpostoAPagar getParcelaImpostoAPagarSample1() {
        return new ParcelaImpostoAPagar()
            .id(1L)
            .numeroParcela(1)
            .valorMulta(1)
            .urlArquivoPagamento("urlArquivoPagamento1")
            .urlArquivoComprovante("urlArquivoComprovante1");
    }

    public static ParcelaImpostoAPagar getParcelaImpostoAPagarSample2() {
        return new ParcelaImpostoAPagar()
            .id(2L)
            .numeroParcela(2)
            .valorMulta(2)
            .urlArquivoPagamento("urlArquivoPagamento2")
            .urlArquivoComprovante("urlArquivoComprovante2");
    }

    public static ParcelaImpostoAPagar getParcelaImpostoAPagarRandomSampleGenerator() {
        return new ParcelaImpostoAPagar()
            .id(longCount.incrementAndGet())
            .numeroParcela(intCount.incrementAndGet())
            .valorMulta(intCount.incrementAndGet())
            .urlArquivoPagamento(UUID.randomUUID().toString())
            .urlArquivoComprovante(UUID.randomUUID().toString());
    }
}
