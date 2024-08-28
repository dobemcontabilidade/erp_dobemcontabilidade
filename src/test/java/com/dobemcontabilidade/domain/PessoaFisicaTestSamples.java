package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PessoaFisicaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static PessoaFisica getPessoaFisicaSample1() {
        return new PessoaFisica()
            .id(1L)
            .nome("nome1")
            .cpf("cpf1")
            .tituloEleitor("tituloEleitor1")
            .rg("rg1")
            .rgOrgaoExpditor("rgOrgaoExpditor1")
            .rgUfExpedicao("rgUfExpedicao1")
            .urlFotoPerfil("urlFotoPerfil1");
    }

    public static PessoaFisica getPessoaFisicaSample2() {
        return new PessoaFisica()
            .id(2L)
            .nome("nome2")
            .cpf("cpf2")
            .tituloEleitor("tituloEleitor2")
            .rg("rg2")
            .rgOrgaoExpditor("rgOrgaoExpditor2")
            .rgUfExpedicao("rgUfExpedicao2")
            .urlFotoPerfil("urlFotoPerfil2");
    }

    public static PessoaFisica getPessoaFisicaRandomSampleGenerator() {
        return new PessoaFisica()
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
