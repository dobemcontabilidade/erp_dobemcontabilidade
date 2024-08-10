package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PessoaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Pessoa getPessoaSample1() {
        return new Pessoa()
            .id(1L)
            .nome("nome1")
            .cpf("cpf1")
            .tituloEleitor("tituloEleitor1")
            .rg("rg1")
            .rgOrgaoExpditor("rgOrgaoExpditor1")
            .rgUfExpedicao("rgUfExpedicao1")
            .urlFotoPerfil("urlFotoPerfil1");
    }

    public static Pessoa getPessoaSample2() {
        return new Pessoa()
            .id(2L)
            .nome("nome2")
            .cpf("cpf2")
            .tituloEleitor("tituloEleitor2")
            .rg("rg2")
            .rgOrgaoExpditor("rgOrgaoExpditor2")
            .rgUfExpedicao("rgUfExpedicao2")
            .urlFotoPerfil("urlFotoPerfil2");
    }

    public static Pessoa getPessoaRandomSampleGenerator() {
        return new Pessoa()
            .id(longCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .cpf(UUID.randomUUID().toString())
            .tituloEleitor(UUID.randomUUID().toString())
            .rg(UUID.randomUUID().toString())
            .rgOrgaoExpditor(UUID.randomUUID().toString())
            .rgUfExpedicao(UUID.randomUUID().toString())
            .urlFotoPerfil(UUID.randomUUID().toString());
    }
}
