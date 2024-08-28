package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EnderecoPessoaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static EnderecoPessoa getEnderecoPessoaSample1() {
        return new EnderecoPessoa()
            .id(1L)
            .logradouro("logradouro1")
            .numero("numero1")
            .complemento("complemento1")
            .bairro("bairro1")
            .cep("cep1");
    }

    public static EnderecoPessoa getEnderecoPessoaSample2() {
        return new EnderecoPessoa()
            .id(2L)
            .logradouro("logradouro2")
            .numero("numero2")
            .complemento("complemento2")
            .bairro("bairro2")
            .cep("cep2");
    }

    public static EnderecoPessoa getEnderecoPessoaRandomSampleGenerator() {
        return new EnderecoPessoa()
            .id(longCount.incrementAndGet())
            .logradouro(UUID.randomUUID().toString())
            .numero(UUID.randomUUID().toString())
            .complemento(UUID.randomUUID().toString())
            .bairro(UUID.randomUUID().toString())
            .cep(UUID.randomUUID().toString());
    }
}
