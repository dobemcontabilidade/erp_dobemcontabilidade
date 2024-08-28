package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AssinaturaEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.TermoContratoAssinaturaEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.TermoContratoContabilTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TermoContratoAssinaturaEmpresaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TermoContratoAssinaturaEmpresa.class);
        TermoContratoAssinaturaEmpresa termoContratoAssinaturaEmpresa1 = getTermoContratoAssinaturaEmpresaSample1();
        TermoContratoAssinaturaEmpresa termoContratoAssinaturaEmpresa2 = new TermoContratoAssinaturaEmpresa();
        assertThat(termoContratoAssinaturaEmpresa1).isNotEqualTo(termoContratoAssinaturaEmpresa2);

        termoContratoAssinaturaEmpresa2.setId(termoContratoAssinaturaEmpresa1.getId());
        assertThat(termoContratoAssinaturaEmpresa1).isEqualTo(termoContratoAssinaturaEmpresa2);

        termoContratoAssinaturaEmpresa2 = getTermoContratoAssinaturaEmpresaSample2();
        assertThat(termoContratoAssinaturaEmpresa1).isNotEqualTo(termoContratoAssinaturaEmpresa2);
    }

    @Test
    void termoContratoContabilTest() {
        TermoContratoAssinaturaEmpresa termoContratoAssinaturaEmpresa = getTermoContratoAssinaturaEmpresaRandomSampleGenerator();
        TermoContratoContabil termoContratoContabilBack = getTermoContratoContabilRandomSampleGenerator();

        termoContratoAssinaturaEmpresa.setTermoContratoContabil(termoContratoContabilBack);
        assertThat(termoContratoAssinaturaEmpresa.getTermoContratoContabil()).isEqualTo(termoContratoContabilBack);

        termoContratoAssinaturaEmpresa.termoContratoContabil(null);
        assertThat(termoContratoAssinaturaEmpresa.getTermoContratoContabil()).isNull();
    }

    @Test
    void empresaTest() {
        TermoContratoAssinaturaEmpresa termoContratoAssinaturaEmpresa = getTermoContratoAssinaturaEmpresaRandomSampleGenerator();
        AssinaturaEmpresa assinaturaEmpresaBack = getAssinaturaEmpresaRandomSampleGenerator();

        termoContratoAssinaturaEmpresa.setEmpresa(assinaturaEmpresaBack);
        assertThat(termoContratoAssinaturaEmpresa.getEmpresa()).isEqualTo(assinaturaEmpresaBack);

        termoContratoAssinaturaEmpresa.empresa(null);
        assertThat(termoContratoAssinaturaEmpresa.getEmpresa()).isNull();
    }
}
