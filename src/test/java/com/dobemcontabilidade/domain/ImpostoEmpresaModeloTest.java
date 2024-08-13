package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.EmpresaModeloTestSamples.*;
import static com.dobemcontabilidade.domain.ImpostoEmpresaModeloTestSamples.*;
import static com.dobemcontabilidade.domain.ImpostoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ImpostoEmpresaModeloTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImpostoEmpresaModelo.class);
        ImpostoEmpresaModelo impostoEmpresaModelo1 = getImpostoEmpresaModeloSample1();
        ImpostoEmpresaModelo impostoEmpresaModelo2 = new ImpostoEmpresaModelo();
        assertThat(impostoEmpresaModelo1).isNotEqualTo(impostoEmpresaModelo2);

        impostoEmpresaModelo2.setId(impostoEmpresaModelo1.getId());
        assertThat(impostoEmpresaModelo1).isEqualTo(impostoEmpresaModelo2);

        impostoEmpresaModelo2 = getImpostoEmpresaModeloSample2();
        assertThat(impostoEmpresaModelo1).isNotEqualTo(impostoEmpresaModelo2);
    }

    @Test
    void empresaModeloTest() {
        ImpostoEmpresaModelo impostoEmpresaModelo = getImpostoEmpresaModeloRandomSampleGenerator();
        EmpresaModelo empresaModeloBack = getEmpresaModeloRandomSampleGenerator();

        impostoEmpresaModelo.setEmpresaModelo(empresaModeloBack);
        assertThat(impostoEmpresaModelo.getEmpresaModelo()).isEqualTo(empresaModeloBack);

        impostoEmpresaModelo.empresaModelo(null);
        assertThat(impostoEmpresaModelo.getEmpresaModelo()).isNull();
    }

    @Test
    void impostoTest() {
        ImpostoEmpresaModelo impostoEmpresaModelo = getImpostoEmpresaModeloRandomSampleGenerator();
        Imposto impostoBack = getImpostoRandomSampleGenerator();

        impostoEmpresaModelo.setImposto(impostoBack);
        assertThat(impostoEmpresaModelo.getImposto()).isEqualTo(impostoBack);

        impostoEmpresaModelo.imposto(null);
        assertThat(impostoEmpresaModelo.getImposto()).isNull();
    }
}
