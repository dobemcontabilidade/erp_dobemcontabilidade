package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class EmpresaVinculadaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static EmpresaVinculada getEmpresaVinculadaSample1() {
        return new EmpresaVinculada()
            .id(1L)
            .nomeEmpresa("nomeEmpresa1")
            .cnpj("cnpj1")
            .remuneracaoEmpresa("remuneracaoEmpresa1")
            .observacoes("observacoes1")
            .valorSalarioFixo("valorSalarioFixo1")
            .dataTerminoContrato("dataTerminoContrato1")
            .numeroInscricao(1)
            .codigoLotacao(1)
            .descricaoComplementar("descricaoComplementar1")
            .descricaoCargo("descricaoCargo1")
            .observacaoJornadaTrabalho("observacaoJornadaTrabalho1")
            .mediaHorasTrabalhadasSemana(1);
    }

    public static EmpresaVinculada getEmpresaVinculadaSample2() {
        return new EmpresaVinculada()
            .id(2L)
            .nomeEmpresa("nomeEmpresa2")
            .cnpj("cnpj2")
            .remuneracaoEmpresa("remuneracaoEmpresa2")
            .observacoes("observacoes2")
            .valorSalarioFixo("valorSalarioFixo2")
            .dataTerminoContrato("dataTerminoContrato2")
            .numeroInscricao(2)
            .codigoLotacao(2)
            .descricaoComplementar("descricaoComplementar2")
            .descricaoCargo("descricaoCargo2")
            .observacaoJornadaTrabalho("observacaoJornadaTrabalho2")
            .mediaHorasTrabalhadasSemana(2);
    }

    public static EmpresaVinculada getEmpresaVinculadaRandomSampleGenerator() {
        return new EmpresaVinculada()
            .id(longCount.incrementAndGet())
            .nomeEmpresa(UUID.randomUUID().toString())
            .cnpj(UUID.randomUUID().toString())
            .remuneracaoEmpresa(UUID.randomUUID().toString())
            .observacoes(UUID.randomUUID().toString())
            .valorSalarioFixo(UUID.randomUUID().toString())
            .dataTerminoContrato(UUID.randomUUID().toString())
            .numeroInscricao(intCount.incrementAndGet())
            .codigoLotacao(intCount.incrementAndGet())
            .descricaoComplementar(UUID.randomUUID().toString())
            .descricaoCargo(UUID.randomUUID().toString())
            .observacaoJornadaTrabalho(UUID.randomUUID().toString())
            .mediaHorasTrabalhadasSemana(intCount.incrementAndGet());
    }
}
