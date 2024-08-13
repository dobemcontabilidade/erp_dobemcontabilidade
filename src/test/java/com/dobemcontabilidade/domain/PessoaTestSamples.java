package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class PessoaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Pessoa getPessoaSample1() {
        return new Pessoa()
            .id(1L)
            .nome("nome1")
            .cpf("cpf1")
            .dataNascimento("dataNascimento1")
            .tituloEleitor(1)
            .rg("rg1")
            .rgOrgaoExpeditor("rgOrgaoExpeditor1")
            .rgUfExpedicao("rgUfExpedicao1")
            .nomeMae("nomeMae1")
            .nomePai("nomePai1")
            .localNascimento("localNascimento1")
            .urlFotoPerfil("urlFotoPerfil1");
    }

    public static Pessoa getPessoaSample2() {
        return new Pessoa()
            .id(2L)
            .nome("nome2")
            .cpf("cpf2")
            .dataNascimento("dataNascimento2")
            .tituloEleitor(2)
            .rg("rg2")
            .rgOrgaoExpeditor("rgOrgaoExpeditor2")
            .rgUfExpedicao("rgUfExpedicao2")
            .nomeMae("nomeMae2")
            .nomePai("nomePai2")
            .localNascimento("localNascimento2")
            .urlFotoPerfil("urlFotoPerfil2");
    }

    public static Pessoa getPessoaRandomSampleGenerator() {
        return new Pessoa()
            .id(longCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .cpf(UUID.randomUUID().toString())
            .dataNascimento(UUID.randomUUID().toString())
            .tituloEleitor(intCount.incrementAndGet())
            .rg(UUID.randomUUID().toString())
            .rgOrgaoExpeditor(UUID.randomUUID().toString())
            .rgUfExpedicao(UUID.randomUUID().toString())
            .nomeMae(UUID.randomUUID().toString())
            .nomePai(UUID.randomUUID().toString())
            .localNascimento(UUID.randomUUID().toString())
            .urlFotoPerfil(UUID.randomUUID().toString());
    }
}
