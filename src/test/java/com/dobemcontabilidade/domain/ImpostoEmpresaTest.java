package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.EmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.ImpostoAPagarEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.ImpostoEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.ImpostoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ImpostoEmpresaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImpostoEmpresa.class);
        ImpostoEmpresa impostoEmpresa1 = getImpostoEmpresaSample1();
        ImpostoEmpresa impostoEmpresa2 = new ImpostoEmpresa();
        assertThat(impostoEmpresa1).isNotEqualTo(impostoEmpresa2);

        impostoEmpresa2.setId(impostoEmpresa1.getId());
        assertThat(impostoEmpresa1).isEqualTo(impostoEmpresa2);

        impostoEmpresa2 = getImpostoEmpresaSample2();
        assertThat(impostoEmpresa1).isNotEqualTo(impostoEmpresa2);
    }

    @Test
    void impostoAPagarEmpresaTest() {
        ImpostoEmpresa impostoEmpresa = getImpostoEmpresaRandomSampleGenerator();
        ImpostoAPagarEmpresa impostoAPagarEmpresaBack = getImpostoAPagarEmpresaRandomSampleGenerator();

        impostoEmpresa.addImpostoAPagarEmpresa(impostoAPagarEmpresaBack);
        assertThat(impostoEmpresa.getImpostoAPagarEmpresas()).containsOnly(impostoAPagarEmpresaBack);
        assertThat(impostoAPagarEmpresaBack.getImposto()).isEqualTo(impostoEmpresa);

        impostoEmpresa.removeImpostoAPagarEmpresa(impostoAPagarEmpresaBack);
        assertThat(impostoEmpresa.getImpostoAPagarEmpresas()).doesNotContain(impostoAPagarEmpresaBack);
        assertThat(impostoAPagarEmpresaBack.getImposto()).isNull();

        impostoEmpresa.impostoAPagarEmpresas(new HashSet<>(Set.of(impostoAPagarEmpresaBack)));
        assertThat(impostoEmpresa.getImpostoAPagarEmpresas()).containsOnly(impostoAPagarEmpresaBack);
        assertThat(impostoAPagarEmpresaBack.getImposto()).isEqualTo(impostoEmpresa);

        impostoEmpresa.setImpostoAPagarEmpresas(new HashSet<>());
        assertThat(impostoEmpresa.getImpostoAPagarEmpresas()).doesNotContain(impostoAPagarEmpresaBack);
        assertThat(impostoAPagarEmpresaBack.getImposto()).isNull();
    }

    @Test
    void empresaTest() {
        ImpostoEmpresa impostoEmpresa = getImpostoEmpresaRandomSampleGenerator();
        Empresa empresaBack = getEmpresaRandomSampleGenerator();

        impostoEmpresa.setEmpresa(empresaBack);
        assertThat(impostoEmpresa.getEmpresa()).isEqualTo(empresaBack);

        impostoEmpresa.empresa(null);
        assertThat(impostoEmpresa.getEmpresa()).isNull();
    }

    @Test
    void impostoTest() {
        ImpostoEmpresa impostoEmpresa = getImpostoEmpresaRandomSampleGenerator();
        Imposto impostoBack = getImpostoRandomSampleGenerator();

        impostoEmpresa.setImposto(impostoBack);
        assertThat(impostoEmpresa.getImposto()).isEqualTo(impostoBack);

        impostoEmpresa.imposto(null);
        assertThat(impostoEmpresa.getImposto()).isNull();
    }
}
