package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.EmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.OpcaoNomeFantasiaEmpresaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OpcaoNomeFantasiaEmpresaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OpcaoNomeFantasiaEmpresa.class);
        OpcaoNomeFantasiaEmpresa opcaoNomeFantasiaEmpresa1 = getOpcaoNomeFantasiaEmpresaSample1();
        OpcaoNomeFantasiaEmpresa opcaoNomeFantasiaEmpresa2 = new OpcaoNomeFantasiaEmpresa();
        assertThat(opcaoNomeFantasiaEmpresa1).isNotEqualTo(opcaoNomeFantasiaEmpresa2);

        opcaoNomeFantasiaEmpresa2.setId(opcaoNomeFantasiaEmpresa1.getId());
        assertThat(opcaoNomeFantasiaEmpresa1).isEqualTo(opcaoNomeFantasiaEmpresa2);

        opcaoNomeFantasiaEmpresa2 = getOpcaoNomeFantasiaEmpresaSample2();
        assertThat(opcaoNomeFantasiaEmpresa1).isNotEqualTo(opcaoNomeFantasiaEmpresa2);
    }

    @Test
    void empresaTest() {
        OpcaoNomeFantasiaEmpresa opcaoNomeFantasiaEmpresa = getOpcaoNomeFantasiaEmpresaRandomSampleGenerator();
        Empresa empresaBack = getEmpresaRandomSampleGenerator();

        opcaoNomeFantasiaEmpresa.setEmpresa(empresaBack);
        assertThat(opcaoNomeFantasiaEmpresa.getEmpresa()).isEqualTo(empresaBack);

        opcaoNomeFantasiaEmpresa.empresa(null);
        assertThat(opcaoNomeFantasiaEmpresa.getEmpresa()).isNull();
    }
}
