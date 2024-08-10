package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.EmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.OpcaoRazaoSocialEmpresaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OpcaoRazaoSocialEmpresaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OpcaoRazaoSocialEmpresa.class);
        OpcaoRazaoSocialEmpresa opcaoRazaoSocialEmpresa1 = getOpcaoRazaoSocialEmpresaSample1();
        OpcaoRazaoSocialEmpresa opcaoRazaoSocialEmpresa2 = new OpcaoRazaoSocialEmpresa();
        assertThat(opcaoRazaoSocialEmpresa1).isNotEqualTo(opcaoRazaoSocialEmpresa2);

        opcaoRazaoSocialEmpresa2.setId(opcaoRazaoSocialEmpresa1.getId());
        assertThat(opcaoRazaoSocialEmpresa1).isEqualTo(opcaoRazaoSocialEmpresa2);

        opcaoRazaoSocialEmpresa2 = getOpcaoRazaoSocialEmpresaSample2();
        assertThat(opcaoRazaoSocialEmpresa1).isNotEqualTo(opcaoRazaoSocialEmpresa2);
    }

    @Test
    void empresaTest() {
        OpcaoRazaoSocialEmpresa opcaoRazaoSocialEmpresa = getOpcaoRazaoSocialEmpresaRandomSampleGenerator();
        Empresa empresaBack = getEmpresaRandomSampleGenerator();

        opcaoRazaoSocialEmpresa.setEmpresa(empresaBack);
        assertThat(opcaoRazaoSocialEmpresa.getEmpresa()).isEqualTo(empresaBack);

        opcaoRazaoSocialEmpresa.empresa(null);
        assertThat(opcaoRazaoSocialEmpresa.getEmpresa()).isNull();
    }
}
