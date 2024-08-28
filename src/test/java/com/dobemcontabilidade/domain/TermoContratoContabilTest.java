package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.TermoContratoAssinaturaEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.TermoContratoContabilTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TermoContratoContabilTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TermoContratoContabil.class);
        TermoContratoContabil termoContratoContabil1 = getTermoContratoContabilSample1();
        TermoContratoContabil termoContratoContabil2 = new TermoContratoContabil();
        assertThat(termoContratoContabil1).isNotEqualTo(termoContratoContabil2);

        termoContratoContabil2.setId(termoContratoContabil1.getId());
        assertThat(termoContratoContabil1).isEqualTo(termoContratoContabil2);

        termoContratoContabil2 = getTermoContratoContabilSample2();
        assertThat(termoContratoContabil1).isNotEqualTo(termoContratoContabil2);
    }

    @Test
    void termoContratoAssinaturaEmpresaTest() {
        TermoContratoContabil termoContratoContabil = getTermoContratoContabilRandomSampleGenerator();
        TermoContratoAssinaturaEmpresa termoContratoAssinaturaEmpresaBack = getTermoContratoAssinaturaEmpresaRandomSampleGenerator();

        termoContratoContabil.addTermoContratoAssinaturaEmpresa(termoContratoAssinaturaEmpresaBack);
        assertThat(termoContratoContabil.getTermoContratoAssinaturaEmpresas()).containsOnly(termoContratoAssinaturaEmpresaBack);
        assertThat(termoContratoAssinaturaEmpresaBack.getTermoContratoContabil()).isEqualTo(termoContratoContabil);

        termoContratoContabil.removeTermoContratoAssinaturaEmpresa(termoContratoAssinaturaEmpresaBack);
        assertThat(termoContratoContabil.getTermoContratoAssinaturaEmpresas()).doesNotContain(termoContratoAssinaturaEmpresaBack);
        assertThat(termoContratoAssinaturaEmpresaBack.getTermoContratoContabil()).isNull();

        termoContratoContabil.termoContratoAssinaturaEmpresas(new HashSet<>(Set.of(termoContratoAssinaturaEmpresaBack)));
        assertThat(termoContratoContabil.getTermoContratoAssinaturaEmpresas()).containsOnly(termoContratoAssinaturaEmpresaBack);
        assertThat(termoContratoAssinaturaEmpresaBack.getTermoContratoContabil()).isEqualTo(termoContratoContabil);

        termoContratoContabil.setTermoContratoAssinaturaEmpresas(new HashSet<>());
        assertThat(termoContratoContabil.getTermoContratoAssinaturaEmpresas()).doesNotContain(termoContratoAssinaturaEmpresaBack);
        assertThat(termoContratoAssinaturaEmpresaBack.getTermoContratoContabil()).isNull();
    }
}
