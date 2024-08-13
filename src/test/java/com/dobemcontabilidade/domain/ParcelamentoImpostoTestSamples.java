package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ParcelamentoImpostoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static ParcelamentoImposto getParcelamentoImpostoSample1() {
        return new ParcelamentoImposto()
            .id(1L)
            .diaVencimento(1)
            .numeroParcelas(1)
            .urlArquivoNegociacao("urlArquivoNegociacao1")
            .numeroParcelasPagas(1)
            .numeroParcelasRegatantes(1);
    }

    public static ParcelamentoImposto getParcelamentoImpostoSample2() {
        return new ParcelamentoImposto()
            .id(2L)
            .diaVencimento(2)
            .numeroParcelas(2)
            .urlArquivoNegociacao("urlArquivoNegociacao2")
            .numeroParcelasPagas(2)
            .numeroParcelasRegatantes(2);
    }

    public static ParcelamentoImposto getParcelamentoImpostoRandomSampleGenerator() {
        return new ParcelamentoImposto()
            .id(longCount.incrementAndGet())
            .diaVencimento(intCount.incrementAndGet())
            .numeroParcelas(intCount.incrementAndGet())
            .urlArquivoNegociacao(UUID.randomUUID().toString())
            .numeroParcelasPagas(intCount.incrementAndGet())
            .numeroParcelasRegatantes(intCount.incrementAndGet());
    }
}
