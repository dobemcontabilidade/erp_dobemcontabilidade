package com.dobemcontabilidade.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AgenteIntegracaoEstagioTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static AgenteIntegracaoEstagio getAgenteIntegracaoEstagioSample1() {
        return new AgenteIntegracaoEstagio()
            .id(1L)
            .cnpj("cnpj1")
            .razaoSocial("razaoSocial1")
            .coordenador("coordenador1")
            .cpfCoordenadorEstagio("cpfCoordenadorEstagio1")
            .logradouro("logradouro1")
            .numero("numero1")
            .complemento("complemento1")
            .bairro("bairro1")
            .cep("cep1");
    }

    public static AgenteIntegracaoEstagio getAgenteIntegracaoEstagioSample2() {
        return new AgenteIntegracaoEstagio()
            .id(2L)
            .cnpj("cnpj2")
            .razaoSocial("razaoSocial2")
            .coordenador("coordenador2")
            .cpfCoordenadorEstagio("cpfCoordenadorEstagio2")
            .logradouro("logradouro2")
            .numero("numero2")
            .complemento("complemento2")
            .bairro("bairro2")
            .cep("cep2");
    }

    public static AgenteIntegracaoEstagio getAgenteIntegracaoEstagioRandomSampleGenerator() {
        return new AgenteIntegracaoEstagio()
            .id(longCount.incrementAndGet())
            .cnpj(UUID.randomUUID().toString())
            .razaoSocial(UUID.randomUUID().toString())
            .coordenador(UUID.randomUUID().toString())
            .cpfCoordenadorEstagio(UUID.randomUUID().toString())
            .logradouro(UUID.randomUUID().toString())
            .numero(UUID.randomUUID().toString())
            .complemento(UUID.randomUUID().toString())
            .bairro(UUID.randomUUID().toString())
            .cep(UUID.randomUUID().toString());
    }
}
