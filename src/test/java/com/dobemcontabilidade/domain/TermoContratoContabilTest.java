package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.PlanoContabilTestSamples.*;
import static com.dobemcontabilidade.domain.TermoContratoContabilTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
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
    void planoContabilTest() {
        TermoContratoContabil termoContratoContabil = getTermoContratoContabilRandomSampleGenerator();
        PlanoContabil planoContabilBack = getPlanoContabilRandomSampleGenerator();

        termoContratoContabil.setPlanoContabil(planoContabilBack);
        assertThat(termoContratoContabil.getPlanoContabil()).isEqualTo(planoContabilBack);

        termoContratoContabil.planoContabil(null);
        assertThat(termoContratoContabil.getPlanoContabil()).isNull();
    }
}
