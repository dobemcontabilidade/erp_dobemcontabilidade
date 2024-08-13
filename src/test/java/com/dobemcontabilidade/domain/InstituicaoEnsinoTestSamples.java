package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class InstituicaoEnsinoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static InstituicaoEnsino getInstituicaoEnsinoSample1() {
        return new InstituicaoEnsino()
            .id(1L)
            .nome("nome1")
            .cnpj("cnpj1")
            .logradouro("logradouro1")
            .numero("numero1")
            .complemento("complemento1")
            .bairro("bairro1")
            .cep("cep1");
    }

    public static InstituicaoEnsino getInstituicaoEnsinoSample2() {
        return new InstituicaoEnsino()
            .id(2L)
            .nome("nome2")
            .cnpj("cnpj2")
            .logradouro("logradouro2")
            .numero("numero2")
            .complemento("complemento2")
            .bairro("bairro2")
            .cep("cep2");
    }

    public static InstituicaoEnsino getInstituicaoEnsinoRandomSampleGenerator() {
        return new InstituicaoEnsino()
            .id(longCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .cnpj(UUID.randomUUID().toString())
            .logradouro(UUID.randomUUID().toString())
            .numero(UUID.randomUUID().toString())
            .complemento(UUID.randomUUID().toString())
            .bairro(UUID.randomUUID().toString())
            .cep(UUID.randomUUID().toString());
    }
}
