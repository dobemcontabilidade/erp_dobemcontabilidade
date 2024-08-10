package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class BancoContadorTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static BancoContador getBancoContadorSample1() {
        return new BancoContador().id(1L).agencia("agencia1").conta("conta1").digitoAgencia("digitoAgencia1").digitoConta("digitoConta1");
    }

    public static BancoContador getBancoContadorSample2() {
        return new BancoContador().id(2L).agencia("agencia2").conta("conta2").digitoAgencia("digitoAgencia2").digitoConta("digitoConta2");
    }

    public static BancoContador getBancoContadorRandomSampleGenerator() {
        return new BancoContador()
            .id(longCount.incrementAndGet())
            .agencia(UUID.randomUUID().toString())
            .conta(UUID.randomUUID().toString())
            .digitoAgencia(UUID.randomUUID().toString())
            .digitoConta(UUID.randomUUID().toString());
    }
}
