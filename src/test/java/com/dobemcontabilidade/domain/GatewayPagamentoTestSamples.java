package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class GatewayPagamentoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static GatewayPagamento getGatewayPagamentoSample1() {
        return new GatewayPagamento().id(1L).nome("nome1").descricao("descricao1");
    }

    public static GatewayPagamento getGatewayPagamentoSample2() {
        return new GatewayPagamento().id(2L).nome("nome2").descricao("descricao2");
    }

    public static GatewayPagamento getGatewayPagamentoRandomSampleGenerator() {
        return new GatewayPagamento()
            .id(longCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .descricao(UUID.randomUUID().toString());
    }
}
