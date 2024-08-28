package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AdicionalEnquadramentoTestSamples.*;
import static com.dobemcontabilidade.domain.AdicionalRamoTestSamples.*;
import static com.dobemcontabilidade.domain.AdicionalTributacaoTestSamples.*;
import static com.dobemcontabilidade.domain.DescontoPeriodoPagamentoTestSamples.*;
import static com.dobemcontabilidade.domain.PlanoAssinaturaContabilTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PlanoAssinaturaContabilTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanoAssinaturaContabil.class);
        PlanoAssinaturaContabil planoAssinaturaContabil1 = getPlanoAssinaturaContabilSample1();
        PlanoAssinaturaContabil planoAssinaturaContabil2 = new PlanoAssinaturaContabil();
        assertThat(planoAssinaturaContabil1).isNotEqualTo(planoAssinaturaContabil2);

        planoAssinaturaContabil2.setId(planoAssinaturaContabil1.getId());
        assertThat(planoAssinaturaContabil1).isEqualTo(planoAssinaturaContabil2);

        planoAssinaturaContabil2 = getPlanoAssinaturaContabilSample2();
        assertThat(planoAssinaturaContabil1).isNotEqualTo(planoAssinaturaContabil2);
    }

    @Test
    void descontoPeriodoPagamentoTest() {
        PlanoAssinaturaContabil planoAssinaturaContabil = getPlanoAssinaturaContabilRandomSampleGenerator();
        DescontoPeriodoPagamento descontoPeriodoPagamentoBack = getDescontoPeriodoPagamentoRandomSampleGenerator();

        planoAssinaturaContabil.addDescontoPeriodoPagamento(descontoPeriodoPagamentoBack);
        assertThat(planoAssinaturaContabil.getDescontoPeriodoPagamentos()).containsOnly(descontoPeriodoPagamentoBack);
        assertThat(descontoPeriodoPagamentoBack.getPlanoAssinaturaContabil()).isEqualTo(planoAssinaturaContabil);

        planoAssinaturaContabil.removeDescontoPeriodoPagamento(descontoPeriodoPagamentoBack);
        assertThat(planoAssinaturaContabil.getDescontoPeriodoPagamentos()).doesNotContain(descontoPeriodoPagamentoBack);
        assertThat(descontoPeriodoPagamentoBack.getPlanoAssinaturaContabil()).isNull();

        planoAssinaturaContabil.descontoPeriodoPagamentos(new HashSet<>(Set.of(descontoPeriodoPagamentoBack)));
        assertThat(planoAssinaturaContabil.getDescontoPeriodoPagamentos()).containsOnly(descontoPeriodoPagamentoBack);
        assertThat(descontoPeriodoPagamentoBack.getPlanoAssinaturaContabil()).isEqualTo(planoAssinaturaContabil);

        planoAssinaturaContabil.setDescontoPeriodoPagamentos(new HashSet<>());
        assertThat(planoAssinaturaContabil.getDescontoPeriodoPagamentos()).doesNotContain(descontoPeriodoPagamentoBack);
        assertThat(descontoPeriodoPagamentoBack.getPlanoAssinaturaContabil()).isNull();
    }

    @Test
    void adicionalRamoTest() {
        PlanoAssinaturaContabil planoAssinaturaContabil = getPlanoAssinaturaContabilRandomSampleGenerator();
        AdicionalRamo adicionalRamoBack = getAdicionalRamoRandomSampleGenerator();

        planoAssinaturaContabil.addAdicionalRamo(adicionalRamoBack);
        assertThat(planoAssinaturaContabil.getAdicionalRamos()).containsOnly(adicionalRamoBack);
        assertThat(adicionalRamoBack.getPlanoAssinaturaContabil()).isEqualTo(planoAssinaturaContabil);

        planoAssinaturaContabil.removeAdicionalRamo(adicionalRamoBack);
        assertThat(planoAssinaturaContabil.getAdicionalRamos()).doesNotContain(adicionalRamoBack);
        assertThat(adicionalRamoBack.getPlanoAssinaturaContabil()).isNull();

        planoAssinaturaContabil.adicionalRamos(new HashSet<>(Set.of(adicionalRamoBack)));
        assertThat(planoAssinaturaContabil.getAdicionalRamos()).containsOnly(adicionalRamoBack);
        assertThat(adicionalRamoBack.getPlanoAssinaturaContabil()).isEqualTo(planoAssinaturaContabil);

        planoAssinaturaContabil.setAdicionalRamos(new HashSet<>());
        assertThat(planoAssinaturaContabil.getAdicionalRamos()).doesNotContain(adicionalRamoBack);
        assertThat(adicionalRamoBack.getPlanoAssinaturaContabil()).isNull();
    }

    @Test
    void adicionalTributacaoTest() {
        PlanoAssinaturaContabil planoAssinaturaContabil = getPlanoAssinaturaContabilRandomSampleGenerator();
        AdicionalTributacao adicionalTributacaoBack = getAdicionalTributacaoRandomSampleGenerator();

        planoAssinaturaContabil.addAdicionalTributacao(adicionalTributacaoBack);
        assertThat(planoAssinaturaContabil.getAdicionalTributacaos()).containsOnly(adicionalTributacaoBack);
        assertThat(adicionalTributacaoBack.getPlanoAssinaturaContabil()).isEqualTo(planoAssinaturaContabil);

        planoAssinaturaContabil.removeAdicionalTributacao(adicionalTributacaoBack);
        assertThat(planoAssinaturaContabil.getAdicionalTributacaos()).doesNotContain(adicionalTributacaoBack);
        assertThat(adicionalTributacaoBack.getPlanoAssinaturaContabil()).isNull();

        planoAssinaturaContabil.adicionalTributacaos(new HashSet<>(Set.of(adicionalTributacaoBack)));
        assertThat(planoAssinaturaContabil.getAdicionalTributacaos()).containsOnly(adicionalTributacaoBack);
        assertThat(adicionalTributacaoBack.getPlanoAssinaturaContabil()).isEqualTo(planoAssinaturaContabil);

        planoAssinaturaContabil.setAdicionalTributacaos(new HashSet<>());
        assertThat(planoAssinaturaContabil.getAdicionalTributacaos()).doesNotContain(adicionalTributacaoBack);
        assertThat(adicionalTributacaoBack.getPlanoAssinaturaContabil()).isNull();
    }

    @Test
    void adicionalEnquadramentoTest() {
        PlanoAssinaturaContabil planoAssinaturaContabil = getPlanoAssinaturaContabilRandomSampleGenerator();
        AdicionalEnquadramento adicionalEnquadramentoBack = getAdicionalEnquadramentoRandomSampleGenerator();

        planoAssinaturaContabil.addAdicionalEnquadramento(adicionalEnquadramentoBack);
        assertThat(planoAssinaturaContabil.getAdicionalEnquadramentos()).containsOnly(adicionalEnquadramentoBack);
        assertThat(adicionalEnquadramentoBack.getPlanoAssinaturaContabil()).isEqualTo(planoAssinaturaContabil);

        planoAssinaturaContabil.removeAdicionalEnquadramento(adicionalEnquadramentoBack);
        assertThat(planoAssinaturaContabil.getAdicionalEnquadramentos()).doesNotContain(adicionalEnquadramentoBack);
        assertThat(adicionalEnquadramentoBack.getPlanoAssinaturaContabil()).isNull();

        planoAssinaturaContabil.adicionalEnquadramentos(new HashSet<>(Set.of(adicionalEnquadramentoBack)));
        assertThat(planoAssinaturaContabil.getAdicionalEnquadramentos()).containsOnly(adicionalEnquadramentoBack);
        assertThat(adicionalEnquadramentoBack.getPlanoAssinaturaContabil()).isEqualTo(planoAssinaturaContabil);

        planoAssinaturaContabil.setAdicionalEnquadramentos(new HashSet<>());
        assertThat(planoAssinaturaContabil.getAdicionalEnquadramentos()).doesNotContain(adicionalEnquadramentoBack);
        assertThat(adicionalEnquadramentoBack.getPlanoAssinaturaContabil()).isNull();
    }
}
