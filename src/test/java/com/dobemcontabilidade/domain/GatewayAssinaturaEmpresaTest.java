package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AssinaturaEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.GatewayAssinaturaEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.GatewayPagamentoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GatewayAssinaturaEmpresaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GatewayAssinaturaEmpresa.class);
        GatewayAssinaturaEmpresa gatewayAssinaturaEmpresa1 = getGatewayAssinaturaEmpresaSample1();
        GatewayAssinaturaEmpresa gatewayAssinaturaEmpresa2 = new GatewayAssinaturaEmpresa();
        assertThat(gatewayAssinaturaEmpresa1).isNotEqualTo(gatewayAssinaturaEmpresa2);

        gatewayAssinaturaEmpresa2.setId(gatewayAssinaturaEmpresa1.getId());
        assertThat(gatewayAssinaturaEmpresa1).isEqualTo(gatewayAssinaturaEmpresa2);

        gatewayAssinaturaEmpresa2 = getGatewayAssinaturaEmpresaSample2();
        assertThat(gatewayAssinaturaEmpresa1).isNotEqualTo(gatewayAssinaturaEmpresa2);
    }

    @Test
    void assinaturaEmpresaTest() {
        GatewayAssinaturaEmpresa gatewayAssinaturaEmpresa = getGatewayAssinaturaEmpresaRandomSampleGenerator();
        AssinaturaEmpresa assinaturaEmpresaBack = getAssinaturaEmpresaRandomSampleGenerator();

        gatewayAssinaturaEmpresa.setAssinaturaEmpresa(assinaturaEmpresaBack);
        assertThat(gatewayAssinaturaEmpresa.getAssinaturaEmpresa()).isEqualTo(assinaturaEmpresaBack);

        gatewayAssinaturaEmpresa.assinaturaEmpresa(null);
        assertThat(gatewayAssinaturaEmpresa.getAssinaturaEmpresa()).isNull();
    }

    @Test
    void gatewayPagamentoTest() {
        GatewayAssinaturaEmpresa gatewayAssinaturaEmpresa = getGatewayAssinaturaEmpresaRandomSampleGenerator();
        GatewayPagamento gatewayPagamentoBack = getGatewayPagamentoRandomSampleGenerator();

        gatewayAssinaturaEmpresa.setGatewayPagamento(gatewayPagamentoBack);
        assertThat(gatewayAssinaturaEmpresa.getGatewayPagamento()).isEqualTo(gatewayPagamentoBack);

        gatewayAssinaturaEmpresa.gatewayPagamento(null);
        assertThat(gatewayAssinaturaEmpresa.getGatewayPagamento()).isNull();
    }
}
