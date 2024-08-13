package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class DemissaoFuncionarioTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static DemissaoFuncionario getDemissaoFuncionarioSample1() {
        return new DemissaoFuncionario()
            .id(1L)
            .numeroCertidaoObito("numeroCertidaoObito1")
            .cnpjEmpresaSucessora("cnpjEmpresaSucessora1")
            .saldoFGTS("saldoFGTS1")
            .valorPensao("valorPensao1")
            .valorPensaoFgts("valorPensaoFgts1")
            .percentualPensao("percentualPensao1")
            .percentualFgts("percentualFgts1")
            .diasAvisoPrevio(1)
            .dataAvisoPrevio("dataAvisoPrevio1")
            .dataPagamento("dataPagamento1")
            .dataAfastamento("dataAfastamento1")
            .urlDemissional("urlDemissional1")
            .cumprimentoAvisoPrevio(1);
    }

    public static DemissaoFuncionario getDemissaoFuncionarioSample2() {
        return new DemissaoFuncionario()
            .id(2L)
            .numeroCertidaoObito("numeroCertidaoObito2")
            .cnpjEmpresaSucessora("cnpjEmpresaSucessora2")
            .saldoFGTS("saldoFGTS2")
            .valorPensao("valorPensao2")
            .valorPensaoFgts("valorPensaoFgts2")
            .percentualPensao("percentualPensao2")
            .percentualFgts("percentualFgts2")
            .diasAvisoPrevio(2)
            .dataAvisoPrevio("dataAvisoPrevio2")
            .dataPagamento("dataPagamento2")
            .dataAfastamento("dataAfastamento2")
            .urlDemissional("urlDemissional2")
            .cumprimentoAvisoPrevio(2);
    }

    public static DemissaoFuncionario getDemissaoFuncionarioRandomSampleGenerator() {
        return new DemissaoFuncionario()
            .id(longCount.incrementAndGet())
            .numeroCertidaoObito(UUID.randomUUID().toString())
            .cnpjEmpresaSucessora(UUID.randomUUID().toString())
            .saldoFGTS(UUID.randomUUID().toString())
            .valorPensao(UUID.randomUUID().toString())
            .valorPensaoFgts(UUID.randomUUID().toString())
            .percentualPensao(UUID.randomUUID().toString())
            .percentualFgts(UUID.randomUUID().toString())
            .diasAvisoPrevio(intCount.incrementAndGet())
            .dataAvisoPrevio(UUID.randomUUID().toString())
            .dataPagamento(UUID.randomUUID().toString())
            .dataAfastamento(UUID.randomUUID().toString())
            .urlDemissional(UUID.randomUUID().toString())
            .cumprimentoAvisoPrevio(intCount.incrementAndGet());
    }
}
