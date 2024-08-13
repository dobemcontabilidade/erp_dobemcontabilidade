package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AdicionalEnquadramentoTestSamples.*;
import static com.dobemcontabilidade.domain.AdicionalRamoTestSamples.*;
import static com.dobemcontabilidade.domain.AdicionalTributacaoTestSamples.*;
import static com.dobemcontabilidade.domain.AssinaturaEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.CalculoPlanoAssinaturaTestSamples.*;
import static com.dobemcontabilidade.domain.DescontoPlanoContabilTestSamples.*;
import static com.dobemcontabilidade.domain.PlanoContabilTestSamples.*;
import static com.dobemcontabilidade.domain.TermoAdesaoEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.TermoContratoContabilTestSamples.*;
import static com.dobemcontabilidade.domain.ValorBaseRamoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PlanoContabilTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanoContabil.class);
        PlanoContabil planoContabil1 = getPlanoContabilSample1();
        PlanoContabil planoContabil2 = new PlanoContabil();
        assertThat(planoContabil1).isNotEqualTo(planoContabil2);

        planoContabil2.setId(planoContabil1.getId());
        assertThat(planoContabil1).isEqualTo(planoContabil2);

        planoContabil2 = getPlanoContabilSample2();
        assertThat(planoContabil1).isNotEqualTo(planoContabil2);
    }

    @Test
    void calculoPlanoAssinaturaTest() {
        PlanoContabil planoContabil = getPlanoContabilRandomSampleGenerator();
        CalculoPlanoAssinatura calculoPlanoAssinaturaBack = getCalculoPlanoAssinaturaRandomSampleGenerator();

        planoContabil.addCalculoPlanoAssinatura(calculoPlanoAssinaturaBack);
        assertThat(planoContabil.getCalculoPlanoAssinaturas()).containsOnly(calculoPlanoAssinaturaBack);
        assertThat(calculoPlanoAssinaturaBack.getPlanoContabil()).isEqualTo(planoContabil);

        planoContabil.removeCalculoPlanoAssinatura(calculoPlanoAssinaturaBack);
        assertThat(planoContabil.getCalculoPlanoAssinaturas()).doesNotContain(calculoPlanoAssinaturaBack);
        assertThat(calculoPlanoAssinaturaBack.getPlanoContabil()).isNull();

        planoContabil.calculoPlanoAssinaturas(new HashSet<>(Set.of(calculoPlanoAssinaturaBack)));
        assertThat(planoContabil.getCalculoPlanoAssinaturas()).containsOnly(calculoPlanoAssinaturaBack);
        assertThat(calculoPlanoAssinaturaBack.getPlanoContabil()).isEqualTo(planoContabil);

        planoContabil.setCalculoPlanoAssinaturas(new HashSet<>());
        assertThat(planoContabil.getCalculoPlanoAssinaturas()).doesNotContain(calculoPlanoAssinaturaBack);
        assertThat(calculoPlanoAssinaturaBack.getPlanoContabil()).isNull();
    }

    @Test
    void assinaturaEmpresaTest() {
        PlanoContabil planoContabil = getPlanoContabilRandomSampleGenerator();
        AssinaturaEmpresa assinaturaEmpresaBack = getAssinaturaEmpresaRandomSampleGenerator();

        planoContabil.addAssinaturaEmpresa(assinaturaEmpresaBack);
        assertThat(planoContabil.getAssinaturaEmpresas()).containsOnly(assinaturaEmpresaBack);
        assertThat(assinaturaEmpresaBack.getPlanoContabil()).isEqualTo(planoContabil);

        planoContabil.removeAssinaturaEmpresa(assinaturaEmpresaBack);
        assertThat(planoContabil.getAssinaturaEmpresas()).doesNotContain(assinaturaEmpresaBack);
        assertThat(assinaturaEmpresaBack.getPlanoContabil()).isNull();

        planoContabil.assinaturaEmpresas(new HashSet<>(Set.of(assinaturaEmpresaBack)));
        assertThat(planoContabil.getAssinaturaEmpresas()).containsOnly(assinaturaEmpresaBack);
        assertThat(assinaturaEmpresaBack.getPlanoContabil()).isEqualTo(planoContabil);

        planoContabil.setAssinaturaEmpresas(new HashSet<>());
        assertThat(planoContabil.getAssinaturaEmpresas()).doesNotContain(assinaturaEmpresaBack);
        assertThat(assinaturaEmpresaBack.getPlanoContabil()).isNull();
    }

    @Test
    void descontoPlanoContabilTest() {
        PlanoContabil planoContabil = getPlanoContabilRandomSampleGenerator();
        DescontoPlanoContabil descontoPlanoContabilBack = getDescontoPlanoContabilRandomSampleGenerator();

        planoContabil.addDescontoPlanoContabil(descontoPlanoContabilBack);
        assertThat(planoContabil.getDescontoPlanoContabils()).containsOnly(descontoPlanoContabilBack);
        assertThat(descontoPlanoContabilBack.getPlanoContabil()).isEqualTo(planoContabil);

        planoContabil.removeDescontoPlanoContabil(descontoPlanoContabilBack);
        assertThat(planoContabil.getDescontoPlanoContabils()).doesNotContain(descontoPlanoContabilBack);
        assertThat(descontoPlanoContabilBack.getPlanoContabil()).isNull();

        planoContabil.descontoPlanoContabils(new HashSet<>(Set.of(descontoPlanoContabilBack)));
        assertThat(planoContabil.getDescontoPlanoContabils()).containsOnly(descontoPlanoContabilBack);
        assertThat(descontoPlanoContabilBack.getPlanoContabil()).isEqualTo(planoContabil);

        planoContabil.setDescontoPlanoContabils(new HashSet<>());
        assertThat(planoContabil.getDescontoPlanoContabils()).doesNotContain(descontoPlanoContabilBack);
        assertThat(descontoPlanoContabilBack.getPlanoContabil()).isNull();
    }

    @Test
    void adicionalRamoTest() {
        PlanoContabil planoContabil = getPlanoContabilRandomSampleGenerator();
        AdicionalRamo adicionalRamoBack = getAdicionalRamoRandomSampleGenerator();

        planoContabil.addAdicionalRamo(adicionalRamoBack);
        assertThat(planoContabil.getAdicionalRamos()).containsOnly(adicionalRamoBack);
        assertThat(adicionalRamoBack.getPlanoContabil()).isEqualTo(planoContabil);

        planoContabil.removeAdicionalRamo(adicionalRamoBack);
        assertThat(planoContabil.getAdicionalRamos()).doesNotContain(adicionalRamoBack);
        assertThat(adicionalRamoBack.getPlanoContabil()).isNull();

        planoContabil.adicionalRamos(new HashSet<>(Set.of(adicionalRamoBack)));
        assertThat(planoContabil.getAdicionalRamos()).containsOnly(adicionalRamoBack);
        assertThat(adicionalRamoBack.getPlanoContabil()).isEqualTo(planoContabil);

        planoContabil.setAdicionalRamos(new HashSet<>());
        assertThat(planoContabil.getAdicionalRamos()).doesNotContain(adicionalRamoBack);
        assertThat(adicionalRamoBack.getPlanoContabil()).isNull();
    }

    @Test
    void adicionalTributacaoTest() {
        PlanoContabil planoContabil = getPlanoContabilRandomSampleGenerator();
        AdicionalTributacao adicionalTributacaoBack = getAdicionalTributacaoRandomSampleGenerator();

        planoContabil.addAdicionalTributacao(adicionalTributacaoBack);
        assertThat(planoContabil.getAdicionalTributacaos()).containsOnly(adicionalTributacaoBack);
        assertThat(adicionalTributacaoBack.getPlanoContabil()).isEqualTo(planoContabil);

        planoContabil.removeAdicionalTributacao(adicionalTributacaoBack);
        assertThat(planoContabil.getAdicionalTributacaos()).doesNotContain(adicionalTributacaoBack);
        assertThat(adicionalTributacaoBack.getPlanoContabil()).isNull();

        planoContabil.adicionalTributacaos(new HashSet<>(Set.of(adicionalTributacaoBack)));
        assertThat(planoContabil.getAdicionalTributacaos()).containsOnly(adicionalTributacaoBack);
        assertThat(adicionalTributacaoBack.getPlanoContabil()).isEqualTo(planoContabil);

        planoContabil.setAdicionalTributacaos(new HashSet<>());
        assertThat(planoContabil.getAdicionalTributacaos()).doesNotContain(adicionalTributacaoBack);
        assertThat(adicionalTributacaoBack.getPlanoContabil()).isNull();
    }

    @Test
    void termoContratoContabilTest() {
        PlanoContabil planoContabil = getPlanoContabilRandomSampleGenerator();
        TermoContratoContabil termoContratoContabilBack = getTermoContratoContabilRandomSampleGenerator();

        planoContabil.addTermoContratoContabil(termoContratoContabilBack);
        assertThat(planoContabil.getTermoContratoContabils()).containsOnly(termoContratoContabilBack);
        assertThat(termoContratoContabilBack.getPlanoContabil()).isEqualTo(planoContabil);

        planoContabil.removeTermoContratoContabil(termoContratoContabilBack);
        assertThat(planoContabil.getTermoContratoContabils()).doesNotContain(termoContratoContabilBack);
        assertThat(termoContratoContabilBack.getPlanoContabil()).isNull();

        planoContabil.termoContratoContabils(new HashSet<>(Set.of(termoContratoContabilBack)));
        assertThat(planoContabil.getTermoContratoContabils()).containsOnly(termoContratoContabilBack);
        assertThat(termoContratoContabilBack.getPlanoContabil()).isEqualTo(planoContabil);

        planoContabil.setTermoContratoContabils(new HashSet<>());
        assertThat(planoContabil.getTermoContratoContabils()).doesNotContain(termoContratoContabilBack);
        assertThat(termoContratoContabilBack.getPlanoContabil()).isNull();
    }

    @Test
    void adicionalEnquadramentoTest() {
        PlanoContabil planoContabil = getPlanoContabilRandomSampleGenerator();
        AdicionalEnquadramento adicionalEnquadramentoBack = getAdicionalEnquadramentoRandomSampleGenerator();

        planoContabil.addAdicionalEnquadramento(adicionalEnquadramentoBack);
        assertThat(planoContabil.getAdicionalEnquadramentos()).containsOnly(adicionalEnquadramentoBack);
        assertThat(adicionalEnquadramentoBack.getPlanoContabil()).isEqualTo(planoContabil);

        planoContabil.removeAdicionalEnquadramento(adicionalEnquadramentoBack);
        assertThat(planoContabil.getAdicionalEnquadramentos()).doesNotContain(adicionalEnquadramentoBack);
        assertThat(adicionalEnquadramentoBack.getPlanoContabil()).isNull();

        planoContabil.adicionalEnquadramentos(new HashSet<>(Set.of(adicionalEnquadramentoBack)));
        assertThat(planoContabil.getAdicionalEnquadramentos()).containsOnly(adicionalEnquadramentoBack);
        assertThat(adicionalEnquadramentoBack.getPlanoContabil()).isEqualTo(planoContabil);

        planoContabil.setAdicionalEnquadramentos(new HashSet<>());
        assertThat(planoContabil.getAdicionalEnquadramentos()).doesNotContain(adicionalEnquadramentoBack);
        assertThat(adicionalEnquadramentoBack.getPlanoContabil()).isNull();
    }

    @Test
    void valorBaseRamoTest() {
        PlanoContabil planoContabil = getPlanoContabilRandomSampleGenerator();
        ValorBaseRamo valorBaseRamoBack = getValorBaseRamoRandomSampleGenerator();

        planoContabil.addValorBaseRamo(valorBaseRamoBack);
        assertThat(planoContabil.getValorBaseRamos()).containsOnly(valorBaseRamoBack);
        assertThat(valorBaseRamoBack.getPlanoContabil()).isEqualTo(planoContabil);

        planoContabil.removeValorBaseRamo(valorBaseRamoBack);
        assertThat(planoContabil.getValorBaseRamos()).doesNotContain(valorBaseRamoBack);
        assertThat(valorBaseRamoBack.getPlanoContabil()).isNull();

        planoContabil.valorBaseRamos(new HashSet<>(Set.of(valorBaseRamoBack)));
        assertThat(planoContabil.getValorBaseRamos()).containsOnly(valorBaseRamoBack);
        assertThat(valorBaseRamoBack.getPlanoContabil()).isEqualTo(planoContabil);

        planoContabil.setValorBaseRamos(new HashSet<>());
        assertThat(planoContabil.getValorBaseRamos()).doesNotContain(valorBaseRamoBack);
        assertThat(valorBaseRamoBack.getPlanoContabil()).isNull();
    }

    @Test
    void termoAdesaoEmpresaTest() {
        PlanoContabil planoContabil = getPlanoContabilRandomSampleGenerator();
        TermoAdesaoEmpresa termoAdesaoEmpresaBack = getTermoAdesaoEmpresaRandomSampleGenerator();

        planoContabil.addTermoAdesaoEmpresa(termoAdesaoEmpresaBack);
        assertThat(planoContabil.getTermoAdesaoEmpresas()).containsOnly(termoAdesaoEmpresaBack);
        assertThat(termoAdesaoEmpresaBack.getPlanoContabil()).isEqualTo(planoContabil);

        planoContabil.removeTermoAdesaoEmpresa(termoAdesaoEmpresaBack);
        assertThat(planoContabil.getTermoAdesaoEmpresas()).doesNotContain(termoAdesaoEmpresaBack);
        assertThat(termoAdesaoEmpresaBack.getPlanoContabil()).isNull();

        planoContabil.termoAdesaoEmpresas(new HashSet<>(Set.of(termoAdesaoEmpresaBack)));
        assertThat(planoContabil.getTermoAdesaoEmpresas()).containsOnly(termoAdesaoEmpresaBack);
        assertThat(termoAdesaoEmpresaBack.getPlanoContabil()).isEqualTo(planoContabil);

        planoContabil.setTermoAdesaoEmpresas(new HashSet<>());
        assertThat(planoContabil.getTermoAdesaoEmpresas()).doesNotContain(termoAdesaoEmpresaBack);
        assertThat(termoAdesaoEmpresaBack.getPlanoContabil()).isNull();
    }
}
