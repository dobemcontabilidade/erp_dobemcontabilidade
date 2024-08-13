package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.GatewayAssinaturaEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.GatewayPagamentoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class GatewayPagamentoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GatewayPagamento.class);
        GatewayPagamento gatewayPagamento1 = getGatewayPagamentoSample1();
        GatewayPagamento gatewayPagamento2 = new GatewayPagamento();
        assertThat(gatewayPagamento1).isNotEqualTo(gatewayPagamento2);

        gatewayPagamento2.setId(gatewayPagamento1.getId());
        assertThat(gatewayPagamento1).isEqualTo(gatewayPagamento2);

        gatewayPagamento2 = getGatewayPagamentoSample2();
        assertThat(gatewayPagamento1).isNotEqualTo(gatewayPagamento2);
    }

    @Test
    void gatewayAssinaturaEmpresaTest() {
        GatewayPagamento gatewayPagamento = getGatewayPagamentoRandomSampleGenerator();
        GatewayAssinaturaEmpresa gatewayAssinaturaEmpresaBack = getGatewayAssinaturaEmpresaRandomSampleGenerator();

        gatewayPagamento.addGatewayAssinaturaEmpresa(gatewayAssinaturaEmpresaBack);
        assertThat(gatewayPagamento.getGatewayAssinaturaEmpresas()).containsOnly(gatewayAssinaturaEmpresaBack);
        assertThat(gatewayAssinaturaEmpresaBack.getGatewayPagamento()).isEqualTo(gatewayPagamento);

        gatewayPagamento.removeGatewayAssinaturaEmpresa(gatewayAssinaturaEmpresaBack);
        assertThat(gatewayPagamento.getGatewayAssinaturaEmpresas()).doesNotContain(gatewayAssinaturaEmpresaBack);
        assertThat(gatewayAssinaturaEmpresaBack.getGatewayPagamento()).isNull();

        gatewayPagamento.gatewayAssinaturaEmpresas(new HashSet<>(Set.of(gatewayAssinaturaEmpresaBack)));
        assertThat(gatewayPagamento.getGatewayAssinaturaEmpresas()).containsOnly(gatewayAssinaturaEmpresaBack);
        assertThat(gatewayAssinaturaEmpresaBack.getGatewayPagamento()).isEqualTo(gatewayPagamento);

        gatewayPagamento.setGatewayAssinaturaEmpresas(new HashSet<>());
        assertThat(gatewayPagamento.getGatewayAssinaturaEmpresas()).doesNotContain(gatewayAssinaturaEmpresaBack);
        assertThat(gatewayAssinaturaEmpresaBack.getGatewayPagamento()).isNull();
    }
}
