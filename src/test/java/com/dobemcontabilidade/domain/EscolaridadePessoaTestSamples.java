package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class EscolaridadePessoaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static EscolaridadePessoa getEscolaridadePessoaSample1() {
        return new EscolaridadePessoa()
            .id(1L)
            .nomeInstituicao("nomeInstituicao1")
            .anoConclusao(1)
            .urlComprovanteEscolaridade("urlComprovanteEscolaridade1");
    }

    public static EscolaridadePessoa getEscolaridadePessoaSample2() {
        return new EscolaridadePessoa()
            .id(2L)
            .nomeInstituicao("nomeInstituicao2")
            .anoConclusao(2)
            .urlComprovanteEscolaridade("urlComprovanteEscolaridade2");
    }

    public static EscolaridadePessoa getEscolaridadePessoaRandomSampleGenerator() {
        return new EscolaridadePessoa()
            .id(longCount.incrementAndGet())
            .nomeInstituicao(UUID.randomUUID().toString())
            .anoConclusao(intCount.incrementAndGet())
            .urlComprovanteEscolaridade(UUID.randomUUID().toString());
    }
}
