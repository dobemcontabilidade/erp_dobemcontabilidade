package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AnexoEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.AnexoRequeridoEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.EmpresaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnexoEmpresaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnexoEmpresa.class);
        AnexoEmpresa anexoEmpresa1 = getAnexoEmpresaSample1();
        AnexoEmpresa anexoEmpresa2 = new AnexoEmpresa();
        assertThat(anexoEmpresa1).isNotEqualTo(anexoEmpresa2);

        anexoEmpresa2.setId(anexoEmpresa1.getId());
        assertThat(anexoEmpresa1).isEqualTo(anexoEmpresa2);

        anexoEmpresa2 = getAnexoEmpresaSample2();
        assertThat(anexoEmpresa1).isNotEqualTo(anexoEmpresa2);
    }

    @Test
    void empresaTest() {
        AnexoEmpresa anexoEmpresa = getAnexoEmpresaRandomSampleGenerator();
        Empresa empresaBack = getEmpresaRandomSampleGenerator();

        anexoEmpresa.setEmpresa(empresaBack);
        assertThat(anexoEmpresa.getEmpresa()).isEqualTo(empresaBack);

        anexoEmpresa.empresa(null);
        assertThat(anexoEmpresa.getEmpresa()).isNull();
    }

    @Test
    void anexoRequeridoEmpresaTest() {
        AnexoEmpresa anexoEmpresa = getAnexoEmpresaRandomSampleGenerator();
        AnexoRequeridoEmpresa anexoRequeridoEmpresaBack = getAnexoRequeridoEmpresaRandomSampleGenerator();

        anexoEmpresa.setAnexoRequeridoEmpresa(anexoRequeridoEmpresaBack);
        assertThat(anexoEmpresa.getAnexoRequeridoEmpresa()).isEqualTo(anexoRequeridoEmpresaBack);

        anexoEmpresa.anexoRequeridoEmpresa(null);
        assertThat(anexoEmpresa.getAnexoRequeridoEmpresa()).isNull();
    }
}
