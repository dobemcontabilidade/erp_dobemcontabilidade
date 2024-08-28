package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PessoajuridicaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Pessoajuridica getPessoajuridicaSample1() {
        return new Pessoajuridica().id(1L).razaoSocial("razaoSocial1").nomeFantasia("nomeFantasia1").cnpj("cnpj1");
    }

    public static Pessoajuridica getPessoajuridicaSample2() {
        return new Pessoajuridica().id(2L).razaoSocial("razaoSocial2").nomeFantasia("nomeFantasia2").cnpj("cnpj2");
    }

    public static Pessoajuridica getPessoajuridicaRandomSampleGenerator() {
        return new Pessoajuridica()
            .id(longCount.incrementAndGet())
            .razaoSocial(UUID.randomUUID().toString())
            .nomeFantasia(UUID.randomUUID().toString())
            .cnpj(UUID.randomUUID().toString());
    }
}
