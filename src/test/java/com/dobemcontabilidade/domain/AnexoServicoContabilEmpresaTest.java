package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AnexoRequeridoTestSamples.*;
import static com.dobemcontabilidade.domain.AnexoServicoContabilEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.ServicoContabilAssinaturaEmpresaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnexoServicoContabilEmpresaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnexoServicoContabilEmpresa.class);
        AnexoServicoContabilEmpresa anexoServicoContabilEmpresa1 = getAnexoServicoContabilEmpresaSample1();
        AnexoServicoContabilEmpresa anexoServicoContabilEmpresa2 = new AnexoServicoContabilEmpresa();
        assertThat(anexoServicoContabilEmpresa1).isNotEqualTo(anexoServicoContabilEmpresa2);

        anexoServicoContabilEmpresa2.setId(anexoServicoContabilEmpresa1.getId());
        assertThat(anexoServicoContabilEmpresa1).isEqualTo(anexoServicoContabilEmpresa2);

        anexoServicoContabilEmpresa2 = getAnexoServicoContabilEmpresaSample2();
        assertThat(anexoServicoContabilEmpresa1).isNotEqualTo(anexoServicoContabilEmpresa2);
    }

    @Test
    void anexoRequeridoTest() {
        AnexoServicoContabilEmpresa anexoServicoContabilEmpresa = getAnexoServicoContabilEmpresaRandomSampleGenerator();
        AnexoRequerido anexoRequeridoBack = getAnexoRequeridoRandomSampleGenerator();

        anexoServicoContabilEmpresa.setAnexoRequerido(anexoRequeridoBack);
        assertThat(anexoServicoContabilEmpresa.getAnexoRequerido()).isEqualTo(anexoRequeridoBack);

        anexoServicoContabilEmpresa.anexoRequerido(null);
        assertThat(anexoServicoContabilEmpresa.getAnexoRequerido()).isNull();
    }

    @Test
    void servicoContabilAssinaturaEmpresaTest() {
        AnexoServicoContabilEmpresa anexoServicoContabilEmpresa = getAnexoServicoContabilEmpresaRandomSampleGenerator();
        ServicoContabilAssinaturaEmpresa servicoContabilAssinaturaEmpresaBack = getServicoContabilAssinaturaEmpresaRandomSampleGenerator();

        anexoServicoContabilEmpresa.setServicoContabilAssinaturaEmpresa(servicoContabilAssinaturaEmpresaBack);
        assertThat(anexoServicoContabilEmpresa.getServicoContabilAssinaturaEmpresa()).isEqualTo(servicoContabilAssinaturaEmpresaBack);

        anexoServicoContabilEmpresa.servicoContabilAssinaturaEmpresa(null);
        assertThat(anexoServicoContabilEmpresa.getServicoContabilAssinaturaEmpresa()).isNull();
    }
}
