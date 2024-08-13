package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ContratoFuncionarioTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static ContratoFuncionario getContratoFuncionarioSample1() {
        return new ContratoFuncionario()
            .id(1L)
            .ctps("ctps1")
            .serieCtps(1)
            .orgaoEmissorDocumento("orgaoEmissorDocumento1")
            .dataValidadeDocumento("dataValidadeDocumento1")
            .dataAdmissao("dataAdmissao1")
            .cargo("cargo1")
            .valorSalarioFixo("valorSalarioFixo1")
            .valorSalarioVariavel("valorSalarioVariavel1")
            .dataTerminoContrato("dataTerminoContrato1")
            .datainicioContrato("datainicioContrato1")
            .horasATrabalhadar(1)
            .codigoCargo("codigoCargo1")
            .numeroPisNisPasep(1);
    }

    public static ContratoFuncionario getContratoFuncionarioSample2() {
        return new ContratoFuncionario()
            .id(2L)
            .ctps("ctps2")
            .serieCtps(2)
            .orgaoEmissorDocumento("orgaoEmissorDocumento2")
            .dataValidadeDocumento("dataValidadeDocumento2")
            .dataAdmissao("dataAdmissao2")
            .cargo("cargo2")
            .valorSalarioFixo("valorSalarioFixo2")
            .valorSalarioVariavel("valorSalarioVariavel2")
            .dataTerminoContrato("dataTerminoContrato2")
            .datainicioContrato("datainicioContrato2")
            .horasATrabalhadar(2)
            .codigoCargo("codigoCargo2")
            .numeroPisNisPasep(2);
    }

    public static ContratoFuncionario getContratoFuncionarioRandomSampleGenerator() {
        return new ContratoFuncionario()
            .id(longCount.incrementAndGet())
            .ctps(UUID.randomUUID().toString())
            .serieCtps(intCount.incrementAndGet())
            .orgaoEmissorDocumento(UUID.randomUUID().toString())
            .dataValidadeDocumento(UUID.randomUUID().toString())
            .dataAdmissao(UUID.randomUUID().toString())
            .cargo(UUID.randomUUID().toString())
            .valorSalarioFixo(UUID.randomUUID().toString())
            .valorSalarioVariavel(UUID.randomUUID().toString())
            .dataTerminoContrato(UUID.randomUUID().toString())
            .datainicioContrato(UUID.randomUUID().toString())
            .horasATrabalhadar(intCount.incrementAndGet())
            .codigoCargo(UUID.randomUUID().toString())
            .numeroPisNisPasep(intCount.incrementAndGet());
    }
}
