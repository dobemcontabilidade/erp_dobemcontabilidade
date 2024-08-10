package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AtividadeEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.EmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.SubclasseCnaeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AtividadeEmpresaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AtividadeEmpresa.class);
        AtividadeEmpresa atividadeEmpresa1 = getAtividadeEmpresaSample1();
        AtividadeEmpresa atividadeEmpresa2 = new AtividadeEmpresa();
        assertThat(atividadeEmpresa1).isNotEqualTo(atividadeEmpresa2);

        atividadeEmpresa2.setId(atividadeEmpresa1.getId());
        assertThat(atividadeEmpresa1).isEqualTo(atividadeEmpresa2);

        atividadeEmpresa2 = getAtividadeEmpresaSample2();
        assertThat(atividadeEmpresa1).isNotEqualTo(atividadeEmpresa2);
    }

    @Test
    void cnaeTest() {
        AtividadeEmpresa atividadeEmpresa = getAtividadeEmpresaRandomSampleGenerator();
        SubclasseCnae subclasseCnaeBack = getSubclasseCnaeRandomSampleGenerator();

        atividadeEmpresa.setCnae(subclasseCnaeBack);
        assertThat(atividadeEmpresa.getCnae()).isEqualTo(subclasseCnaeBack);

        atividadeEmpresa.cnae(null);
        assertThat(atividadeEmpresa.getCnae()).isNull();
    }

    @Test
    void empresaTest() {
        AtividadeEmpresa atividadeEmpresa = getAtividadeEmpresaRandomSampleGenerator();
        Empresa empresaBack = getEmpresaRandomSampleGenerator();

        atividadeEmpresa.setEmpresa(empresaBack);
        assertThat(atividadeEmpresa.getEmpresa()).isEqualTo(empresaBack);

        atividadeEmpresa.empresa(null);
        assertThat(atividadeEmpresa.getEmpresa()).isNull();
    }
}
