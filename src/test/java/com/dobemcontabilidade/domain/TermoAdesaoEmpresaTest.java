package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.EmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.PlanoContabilTestSamples.*;
import static com.dobemcontabilidade.domain.TermoAdesaoEmpresaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TermoAdesaoEmpresaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TermoAdesaoEmpresa.class);
        TermoAdesaoEmpresa termoAdesaoEmpresa1 = getTermoAdesaoEmpresaSample1();
        TermoAdesaoEmpresa termoAdesaoEmpresa2 = new TermoAdesaoEmpresa();
        assertThat(termoAdesaoEmpresa1).isNotEqualTo(termoAdesaoEmpresa2);

        termoAdesaoEmpresa2.setId(termoAdesaoEmpresa1.getId());
        assertThat(termoAdesaoEmpresa1).isEqualTo(termoAdesaoEmpresa2);

        termoAdesaoEmpresa2 = getTermoAdesaoEmpresaSample2();
        assertThat(termoAdesaoEmpresa1).isNotEqualTo(termoAdesaoEmpresa2);
    }

    @Test
    void empresaTest() {
        TermoAdesaoEmpresa termoAdesaoEmpresa = getTermoAdesaoEmpresaRandomSampleGenerator();
        Empresa empresaBack = getEmpresaRandomSampleGenerator();

        termoAdesaoEmpresa.setEmpresa(empresaBack);
        assertThat(termoAdesaoEmpresa.getEmpresa()).isEqualTo(empresaBack);

        termoAdesaoEmpresa.empresa(null);
        assertThat(termoAdesaoEmpresa.getEmpresa()).isNull();
    }

    @Test
    void planoContabilTest() {
        TermoAdesaoEmpresa termoAdesaoEmpresa = getTermoAdesaoEmpresaRandomSampleGenerator();
        PlanoContabil planoContabilBack = getPlanoContabilRandomSampleGenerator();

        termoAdesaoEmpresa.setPlanoContabil(planoContabilBack);
        assertThat(termoAdesaoEmpresa.getPlanoContabil()).isEqualTo(planoContabilBack);

        termoAdesaoEmpresa.planoContabil(null);
        assertThat(termoAdesaoEmpresa.getPlanoContabil()).isNull();
    }
}
