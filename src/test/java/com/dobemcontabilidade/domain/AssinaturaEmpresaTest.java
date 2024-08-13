package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AreaContabilAssinaturaEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.AssinaturaEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.CalculoPlanoAssinaturaTestSamples.*;
import static com.dobemcontabilidade.domain.CobrancaEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.EmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.FormaDePagamentoTestSamples.*;
import static com.dobemcontabilidade.domain.GatewayAssinaturaEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.GrupoAcessoEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.PagamentoTestSamples.*;
import static com.dobemcontabilidade.domain.PeriodoPagamentoTestSamples.*;
import static com.dobemcontabilidade.domain.PlanoContaAzulTestSamples.*;
import static com.dobemcontabilidade.domain.PlanoContabilTestSamples.*;
import static com.dobemcontabilidade.domain.ServicoContabilAssinaturaEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.UsuarioEmpresaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AssinaturaEmpresaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssinaturaEmpresa.class);
        AssinaturaEmpresa assinaturaEmpresa1 = getAssinaturaEmpresaSample1();
        AssinaturaEmpresa assinaturaEmpresa2 = new AssinaturaEmpresa();
        assertThat(assinaturaEmpresa1).isNotEqualTo(assinaturaEmpresa2);

        assinaturaEmpresa2.setId(assinaturaEmpresa1.getId());
        assertThat(assinaturaEmpresa1).isEqualTo(assinaturaEmpresa2);

        assinaturaEmpresa2 = getAssinaturaEmpresaSample2();
        assertThat(assinaturaEmpresa1).isNotEqualTo(assinaturaEmpresa2);
    }

    @Test
    void grupoAcessoEmpresaTest() {
        AssinaturaEmpresa assinaturaEmpresa = getAssinaturaEmpresaRandomSampleGenerator();
        GrupoAcessoEmpresa grupoAcessoEmpresaBack = getGrupoAcessoEmpresaRandomSampleGenerator();

        assinaturaEmpresa.addGrupoAcessoEmpresa(grupoAcessoEmpresaBack);
        assertThat(assinaturaEmpresa.getGrupoAcessoEmpresas()).containsOnly(grupoAcessoEmpresaBack);
        assertThat(grupoAcessoEmpresaBack.getAssinaturaEmpresa()).isEqualTo(assinaturaEmpresa);

        assinaturaEmpresa.removeGrupoAcessoEmpresa(grupoAcessoEmpresaBack);
        assertThat(assinaturaEmpresa.getGrupoAcessoEmpresas()).doesNotContain(grupoAcessoEmpresaBack);
        assertThat(grupoAcessoEmpresaBack.getAssinaturaEmpresa()).isNull();

        assinaturaEmpresa.grupoAcessoEmpresas(new HashSet<>(Set.of(grupoAcessoEmpresaBack)));
        assertThat(assinaturaEmpresa.getGrupoAcessoEmpresas()).containsOnly(grupoAcessoEmpresaBack);
        assertThat(grupoAcessoEmpresaBack.getAssinaturaEmpresa()).isEqualTo(assinaturaEmpresa);

        assinaturaEmpresa.setGrupoAcessoEmpresas(new HashSet<>());
        assertThat(assinaturaEmpresa.getGrupoAcessoEmpresas()).doesNotContain(grupoAcessoEmpresaBack);
        assertThat(grupoAcessoEmpresaBack.getAssinaturaEmpresa()).isNull();
    }

    @Test
    void areaContabilAssinaturaEmpresaTest() {
        AssinaturaEmpresa assinaturaEmpresa = getAssinaturaEmpresaRandomSampleGenerator();
        AreaContabilAssinaturaEmpresa areaContabilAssinaturaEmpresaBack = getAreaContabilAssinaturaEmpresaRandomSampleGenerator();

        assinaturaEmpresa.addAreaContabilAssinaturaEmpresa(areaContabilAssinaturaEmpresaBack);
        assertThat(assinaturaEmpresa.getAreaContabilAssinaturaEmpresas()).containsOnly(areaContabilAssinaturaEmpresaBack);
        assertThat(areaContabilAssinaturaEmpresaBack.getAssinaturaEmpresa()).isEqualTo(assinaturaEmpresa);

        assinaturaEmpresa.removeAreaContabilAssinaturaEmpresa(areaContabilAssinaturaEmpresaBack);
        assertThat(assinaturaEmpresa.getAreaContabilAssinaturaEmpresas()).doesNotContain(areaContabilAssinaturaEmpresaBack);
        assertThat(areaContabilAssinaturaEmpresaBack.getAssinaturaEmpresa()).isNull();

        assinaturaEmpresa.areaContabilAssinaturaEmpresas(new HashSet<>(Set.of(areaContabilAssinaturaEmpresaBack)));
        assertThat(assinaturaEmpresa.getAreaContabilAssinaturaEmpresas()).containsOnly(areaContabilAssinaturaEmpresaBack);
        assertThat(areaContabilAssinaturaEmpresaBack.getAssinaturaEmpresa()).isEqualTo(assinaturaEmpresa);

        assinaturaEmpresa.setAreaContabilAssinaturaEmpresas(new HashSet<>());
        assertThat(assinaturaEmpresa.getAreaContabilAssinaturaEmpresas()).doesNotContain(areaContabilAssinaturaEmpresaBack);
        assertThat(areaContabilAssinaturaEmpresaBack.getAssinaturaEmpresa()).isNull();
    }

    @Test
    void servicoContabilAssinaturaEmpresaTest() {
        AssinaturaEmpresa assinaturaEmpresa = getAssinaturaEmpresaRandomSampleGenerator();
        ServicoContabilAssinaturaEmpresa servicoContabilAssinaturaEmpresaBack = getServicoContabilAssinaturaEmpresaRandomSampleGenerator();

        assinaturaEmpresa.addServicoContabilAssinaturaEmpresa(servicoContabilAssinaturaEmpresaBack);
        assertThat(assinaturaEmpresa.getServicoContabilAssinaturaEmpresas()).containsOnly(servicoContabilAssinaturaEmpresaBack);
        assertThat(servicoContabilAssinaturaEmpresaBack.getAssinaturaEmpresa()).isEqualTo(assinaturaEmpresa);

        assinaturaEmpresa.removeServicoContabilAssinaturaEmpresa(servicoContabilAssinaturaEmpresaBack);
        assertThat(assinaturaEmpresa.getServicoContabilAssinaturaEmpresas()).doesNotContain(servicoContabilAssinaturaEmpresaBack);
        assertThat(servicoContabilAssinaturaEmpresaBack.getAssinaturaEmpresa()).isNull();

        assinaturaEmpresa.servicoContabilAssinaturaEmpresas(new HashSet<>(Set.of(servicoContabilAssinaturaEmpresaBack)));
        assertThat(assinaturaEmpresa.getServicoContabilAssinaturaEmpresas()).containsOnly(servicoContabilAssinaturaEmpresaBack);
        assertThat(servicoContabilAssinaturaEmpresaBack.getAssinaturaEmpresa()).isEqualTo(assinaturaEmpresa);

        assinaturaEmpresa.setServicoContabilAssinaturaEmpresas(new HashSet<>());
        assertThat(assinaturaEmpresa.getServicoContabilAssinaturaEmpresas()).doesNotContain(servicoContabilAssinaturaEmpresaBack);
        assertThat(servicoContabilAssinaturaEmpresaBack.getAssinaturaEmpresa()).isNull();
    }

    @Test
    void gatewayAssinaturaEmpresaTest() {
        AssinaturaEmpresa assinaturaEmpresa = getAssinaturaEmpresaRandomSampleGenerator();
        GatewayAssinaturaEmpresa gatewayAssinaturaEmpresaBack = getGatewayAssinaturaEmpresaRandomSampleGenerator();

        assinaturaEmpresa.addGatewayAssinaturaEmpresa(gatewayAssinaturaEmpresaBack);
        assertThat(assinaturaEmpresa.getGatewayAssinaturaEmpresas()).containsOnly(gatewayAssinaturaEmpresaBack);
        assertThat(gatewayAssinaturaEmpresaBack.getAssinaturaEmpresa()).isEqualTo(assinaturaEmpresa);

        assinaturaEmpresa.removeGatewayAssinaturaEmpresa(gatewayAssinaturaEmpresaBack);
        assertThat(assinaturaEmpresa.getGatewayAssinaturaEmpresas()).doesNotContain(gatewayAssinaturaEmpresaBack);
        assertThat(gatewayAssinaturaEmpresaBack.getAssinaturaEmpresa()).isNull();

        assinaturaEmpresa.gatewayAssinaturaEmpresas(new HashSet<>(Set.of(gatewayAssinaturaEmpresaBack)));
        assertThat(assinaturaEmpresa.getGatewayAssinaturaEmpresas()).containsOnly(gatewayAssinaturaEmpresaBack);
        assertThat(gatewayAssinaturaEmpresaBack.getAssinaturaEmpresa()).isEqualTo(assinaturaEmpresa);

        assinaturaEmpresa.setGatewayAssinaturaEmpresas(new HashSet<>());
        assertThat(assinaturaEmpresa.getGatewayAssinaturaEmpresas()).doesNotContain(gatewayAssinaturaEmpresaBack);
        assertThat(gatewayAssinaturaEmpresaBack.getAssinaturaEmpresa()).isNull();
    }

    @Test
    void calculoPlanoAssinaturaTest() {
        AssinaturaEmpresa assinaturaEmpresa = getAssinaturaEmpresaRandomSampleGenerator();
        CalculoPlanoAssinatura calculoPlanoAssinaturaBack = getCalculoPlanoAssinaturaRandomSampleGenerator();

        assinaturaEmpresa.addCalculoPlanoAssinatura(calculoPlanoAssinaturaBack);
        assertThat(assinaturaEmpresa.getCalculoPlanoAssinaturas()).containsOnly(calculoPlanoAssinaturaBack);
        assertThat(calculoPlanoAssinaturaBack.getAssinaturaEmpresa()).isEqualTo(assinaturaEmpresa);

        assinaturaEmpresa.removeCalculoPlanoAssinatura(calculoPlanoAssinaturaBack);
        assertThat(assinaturaEmpresa.getCalculoPlanoAssinaturas()).doesNotContain(calculoPlanoAssinaturaBack);
        assertThat(calculoPlanoAssinaturaBack.getAssinaturaEmpresa()).isNull();

        assinaturaEmpresa.calculoPlanoAssinaturas(new HashSet<>(Set.of(calculoPlanoAssinaturaBack)));
        assertThat(assinaturaEmpresa.getCalculoPlanoAssinaturas()).containsOnly(calculoPlanoAssinaturaBack);
        assertThat(calculoPlanoAssinaturaBack.getAssinaturaEmpresa()).isEqualTo(assinaturaEmpresa);

        assinaturaEmpresa.setCalculoPlanoAssinaturas(new HashSet<>());
        assertThat(assinaturaEmpresa.getCalculoPlanoAssinaturas()).doesNotContain(calculoPlanoAssinaturaBack);
        assertThat(calculoPlanoAssinaturaBack.getAssinaturaEmpresa()).isNull();
    }

    @Test
    void pagamentoTest() {
        AssinaturaEmpresa assinaturaEmpresa = getAssinaturaEmpresaRandomSampleGenerator();
        Pagamento pagamentoBack = getPagamentoRandomSampleGenerator();

        assinaturaEmpresa.addPagamento(pagamentoBack);
        assertThat(assinaturaEmpresa.getPagamentos()).containsOnly(pagamentoBack);
        assertThat(pagamentoBack.getAssinaturaEmpresa()).isEqualTo(assinaturaEmpresa);

        assinaturaEmpresa.removePagamento(pagamentoBack);
        assertThat(assinaturaEmpresa.getPagamentos()).doesNotContain(pagamentoBack);
        assertThat(pagamentoBack.getAssinaturaEmpresa()).isNull();

        assinaturaEmpresa.pagamentos(new HashSet<>(Set.of(pagamentoBack)));
        assertThat(assinaturaEmpresa.getPagamentos()).containsOnly(pagamentoBack);
        assertThat(pagamentoBack.getAssinaturaEmpresa()).isEqualTo(assinaturaEmpresa);

        assinaturaEmpresa.setPagamentos(new HashSet<>());
        assertThat(assinaturaEmpresa.getPagamentos()).doesNotContain(pagamentoBack);
        assertThat(pagamentoBack.getAssinaturaEmpresa()).isNull();
    }

    @Test
    void cobrancaEmpresaTest() {
        AssinaturaEmpresa assinaturaEmpresa = getAssinaturaEmpresaRandomSampleGenerator();
        CobrancaEmpresa cobrancaEmpresaBack = getCobrancaEmpresaRandomSampleGenerator();

        assinaturaEmpresa.addCobrancaEmpresa(cobrancaEmpresaBack);
        assertThat(assinaturaEmpresa.getCobrancaEmpresas()).containsOnly(cobrancaEmpresaBack);
        assertThat(cobrancaEmpresaBack.getAssinaturaEmpresa()).isEqualTo(assinaturaEmpresa);

        assinaturaEmpresa.removeCobrancaEmpresa(cobrancaEmpresaBack);
        assertThat(assinaturaEmpresa.getCobrancaEmpresas()).doesNotContain(cobrancaEmpresaBack);
        assertThat(cobrancaEmpresaBack.getAssinaturaEmpresa()).isNull();

        assinaturaEmpresa.cobrancaEmpresas(new HashSet<>(Set.of(cobrancaEmpresaBack)));
        assertThat(assinaturaEmpresa.getCobrancaEmpresas()).containsOnly(cobrancaEmpresaBack);
        assertThat(cobrancaEmpresaBack.getAssinaturaEmpresa()).isEqualTo(assinaturaEmpresa);

        assinaturaEmpresa.setCobrancaEmpresas(new HashSet<>());
        assertThat(assinaturaEmpresa.getCobrancaEmpresas()).doesNotContain(cobrancaEmpresaBack);
        assertThat(cobrancaEmpresaBack.getAssinaturaEmpresa()).isNull();
    }

    @Test
    void usuarioEmpresaTest() {
        AssinaturaEmpresa assinaturaEmpresa = getAssinaturaEmpresaRandomSampleGenerator();
        UsuarioEmpresa usuarioEmpresaBack = getUsuarioEmpresaRandomSampleGenerator();

        assinaturaEmpresa.addUsuarioEmpresa(usuarioEmpresaBack);
        assertThat(assinaturaEmpresa.getUsuarioEmpresas()).containsOnly(usuarioEmpresaBack);
        assertThat(usuarioEmpresaBack.getAssinaturaEmpresa()).isEqualTo(assinaturaEmpresa);

        assinaturaEmpresa.removeUsuarioEmpresa(usuarioEmpresaBack);
        assertThat(assinaturaEmpresa.getUsuarioEmpresas()).doesNotContain(usuarioEmpresaBack);
        assertThat(usuarioEmpresaBack.getAssinaturaEmpresa()).isNull();

        assinaturaEmpresa.usuarioEmpresas(new HashSet<>(Set.of(usuarioEmpresaBack)));
        assertThat(assinaturaEmpresa.getUsuarioEmpresas()).containsOnly(usuarioEmpresaBack);
        assertThat(usuarioEmpresaBack.getAssinaturaEmpresa()).isEqualTo(assinaturaEmpresa);

        assinaturaEmpresa.setUsuarioEmpresas(new HashSet<>());
        assertThat(assinaturaEmpresa.getUsuarioEmpresas()).doesNotContain(usuarioEmpresaBack);
        assertThat(usuarioEmpresaBack.getAssinaturaEmpresa()).isNull();
    }

    @Test
    void periodoPagamentoTest() {
        AssinaturaEmpresa assinaturaEmpresa = getAssinaturaEmpresaRandomSampleGenerator();
        PeriodoPagamento periodoPagamentoBack = getPeriodoPagamentoRandomSampleGenerator();

        assinaturaEmpresa.setPeriodoPagamento(periodoPagamentoBack);
        assertThat(assinaturaEmpresa.getPeriodoPagamento()).isEqualTo(periodoPagamentoBack);

        assinaturaEmpresa.periodoPagamento(null);
        assertThat(assinaturaEmpresa.getPeriodoPagamento()).isNull();
    }

    @Test
    void formaDePagamentoTest() {
        AssinaturaEmpresa assinaturaEmpresa = getAssinaturaEmpresaRandomSampleGenerator();
        FormaDePagamento formaDePagamentoBack = getFormaDePagamentoRandomSampleGenerator();

        assinaturaEmpresa.setFormaDePagamento(formaDePagamentoBack);
        assertThat(assinaturaEmpresa.getFormaDePagamento()).isEqualTo(formaDePagamentoBack);

        assinaturaEmpresa.formaDePagamento(null);
        assertThat(assinaturaEmpresa.getFormaDePagamento()).isNull();
    }

    @Test
    void planoContaAzulTest() {
        AssinaturaEmpresa assinaturaEmpresa = getAssinaturaEmpresaRandomSampleGenerator();
        PlanoContaAzul planoContaAzulBack = getPlanoContaAzulRandomSampleGenerator();

        assinaturaEmpresa.setPlanoContaAzul(planoContaAzulBack);
        assertThat(assinaturaEmpresa.getPlanoContaAzul()).isEqualTo(planoContaAzulBack);

        assinaturaEmpresa.planoContaAzul(null);
        assertThat(assinaturaEmpresa.getPlanoContaAzul()).isNull();
    }

    @Test
    void planoContabilTest() {
        AssinaturaEmpresa assinaturaEmpresa = getAssinaturaEmpresaRandomSampleGenerator();
        PlanoContabil planoContabilBack = getPlanoContabilRandomSampleGenerator();

        assinaturaEmpresa.setPlanoContabil(planoContabilBack);
        assertThat(assinaturaEmpresa.getPlanoContabil()).isEqualTo(planoContabilBack);

        assinaturaEmpresa.planoContabil(null);
        assertThat(assinaturaEmpresa.getPlanoContabil()).isNull();
    }

    @Test
    void empresaTest() {
        AssinaturaEmpresa assinaturaEmpresa = getAssinaturaEmpresaRandomSampleGenerator();
        Empresa empresaBack = getEmpresaRandomSampleGenerator();

        assinaturaEmpresa.setEmpresa(empresaBack);
        assertThat(assinaturaEmpresa.getEmpresa()).isEqualTo(empresaBack);

        assinaturaEmpresa.empresa(null);
        assertThat(assinaturaEmpresa.getEmpresa()).isNull();
    }
}
