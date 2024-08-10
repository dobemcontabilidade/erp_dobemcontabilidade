package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class AtividadeEmpresaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static AtividadeEmpresa getAtividadeEmpresaSample1() {
        return new AtividadeEmpresa().id(1L).ordem(1).descricaoAtividade("descricaoAtividade1");
    }

    public static AtividadeEmpresa getAtividadeEmpresaSample2() {
        return new AtividadeEmpresa().id(2L).ordem(2).descricaoAtividade("descricaoAtividade2");
    }

    public static AtividadeEmpresa getAtividadeEmpresaRandomSampleGenerator() {
        return new AtividadeEmpresa()
            .id(longCount.incrementAndGet())
            .ordem(intCount.incrementAndGet())
            .descricaoAtividade(UUID.randomUUID().toString());
    }
}
