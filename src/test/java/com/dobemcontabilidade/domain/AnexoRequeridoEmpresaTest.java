package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AnexoEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.AnexoRequeridoEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.AnexoRequeridoTestSamples.*;
import static com.dobemcontabilidade.domain.EmpresaModeloTestSamples.*;
import static com.dobemcontabilidade.domain.EmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.EnquadramentoTestSamples.*;
import static com.dobemcontabilidade.domain.RamoTestSamples.*;
import static com.dobemcontabilidade.domain.TributacaoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AnexoRequeridoEmpresaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnexoRequeridoEmpresa.class);
        AnexoRequeridoEmpresa anexoRequeridoEmpresa1 = getAnexoRequeridoEmpresaSample1();
        AnexoRequeridoEmpresa anexoRequeridoEmpresa2 = new AnexoRequeridoEmpresa();
        assertThat(anexoRequeridoEmpresa1).isNotEqualTo(anexoRequeridoEmpresa2);

        anexoRequeridoEmpresa2.setId(anexoRequeridoEmpresa1.getId());
        assertThat(anexoRequeridoEmpresa1).isEqualTo(anexoRequeridoEmpresa2);

        anexoRequeridoEmpresa2 = getAnexoRequeridoEmpresaSample2();
        assertThat(anexoRequeridoEmpresa1).isNotEqualTo(anexoRequeridoEmpresa2);
    }

    @Test
    void anexoEmpresaTest() {
        AnexoRequeridoEmpresa anexoRequeridoEmpresa = getAnexoRequeridoEmpresaRandomSampleGenerator();
        AnexoEmpresa anexoEmpresaBack = getAnexoEmpresaRandomSampleGenerator();

        anexoRequeridoEmpresa.addAnexoEmpresa(anexoEmpresaBack);
        assertThat(anexoRequeridoEmpresa.getAnexoEmpresas()).containsOnly(anexoEmpresaBack);
        assertThat(anexoEmpresaBack.getAnexoRequeridoEmpresa()).isEqualTo(anexoRequeridoEmpresa);

        anexoRequeridoEmpresa.removeAnexoEmpresa(anexoEmpresaBack);
        assertThat(anexoRequeridoEmpresa.getAnexoEmpresas()).doesNotContain(anexoEmpresaBack);
        assertThat(anexoEmpresaBack.getAnexoRequeridoEmpresa()).isNull();

        anexoRequeridoEmpresa.anexoEmpresas(new HashSet<>(Set.of(anexoEmpresaBack)));
        assertThat(anexoRequeridoEmpresa.getAnexoEmpresas()).containsOnly(anexoEmpresaBack);
        assertThat(anexoEmpresaBack.getAnexoRequeridoEmpresa()).isEqualTo(anexoRequeridoEmpresa);

        anexoRequeridoEmpresa.setAnexoEmpresas(new HashSet<>());
        assertThat(anexoRequeridoEmpresa.getAnexoEmpresas()).doesNotContain(anexoEmpresaBack);
        assertThat(anexoEmpresaBack.getAnexoRequeridoEmpresa()).isNull();
    }

    @Test
    void anexoRequeridoTest() {
        AnexoRequeridoEmpresa anexoRequeridoEmpresa = getAnexoRequeridoEmpresaRandomSampleGenerator();
        AnexoRequerido anexoRequeridoBack = getAnexoRequeridoRandomSampleGenerator();

        anexoRequeridoEmpresa.setAnexoRequerido(anexoRequeridoBack);
        assertThat(anexoRequeridoEmpresa.getAnexoRequerido()).isEqualTo(anexoRequeridoBack);

        anexoRequeridoEmpresa.anexoRequerido(null);
        assertThat(anexoRequeridoEmpresa.getAnexoRequerido()).isNull();
    }

    @Test
    void enquadramentoTest() {
        AnexoRequeridoEmpresa anexoRequeridoEmpresa = getAnexoRequeridoEmpresaRandomSampleGenerator();
        Enquadramento enquadramentoBack = getEnquadramentoRandomSampleGenerator();

        anexoRequeridoEmpresa.setEnquadramento(enquadramentoBack);
        assertThat(anexoRequeridoEmpresa.getEnquadramento()).isEqualTo(enquadramentoBack);

        anexoRequeridoEmpresa.enquadramento(null);
        assertThat(anexoRequeridoEmpresa.getEnquadramento()).isNull();
    }

    @Test
    void tributacaoTest() {
        AnexoRequeridoEmpresa anexoRequeridoEmpresa = getAnexoRequeridoEmpresaRandomSampleGenerator();
        Tributacao tributacaoBack = getTributacaoRandomSampleGenerator();

        anexoRequeridoEmpresa.setTributacao(tributacaoBack);
        assertThat(anexoRequeridoEmpresa.getTributacao()).isEqualTo(tributacaoBack);

        anexoRequeridoEmpresa.tributacao(null);
        assertThat(anexoRequeridoEmpresa.getTributacao()).isNull();
    }

    @Test
    void ramoTest() {
        AnexoRequeridoEmpresa anexoRequeridoEmpresa = getAnexoRequeridoEmpresaRandomSampleGenerator();
        Ramo ramoBack = getRamoRandomSampleGenerator();

        anexoRequeridoEmpresa.setRamo(ramoBack);
        assertThat(anexoRequeridoEmpresa.getRamo()).isEqualTo(ramoBack);

        anexoRequeridoEmpresa.ramo(null);
        assertThat(anexoRequeridoEmpresa.getRamo()).isNull();
    }

    @Test
    void empresaTest() {
        AnexoRequeridoEmpresa anexoRequeridoEmpresa = getAnexoRequeridoEmpresaRandomSampleGenerator();
        Empresa empresaBack = getEmpresaRandomSampleGenerator();

        anexoRequeridoEmpresa.setEmpresa(empresaBack);
        assertThat(anexoRequeridoEmpresa.getEmpresa()).isEqualTo(empresaBack);

        anexoRequeridoEmpresa.empresa(null);
        assertThat(anexoRequeridoEmpresa.getEmpresa()).isNull();
    }

    @Test
    void empresaModeloTest() {
        AnexoRequeridoEmpresa anexoRequeridoEmpresa = getAnexoRequeridoEmpresaRandomSampleGenerator();
        EmpresaModelo empresaModeloBack = getEmpresaModeloRandomSampleGenerator();

        anexoRequeridoEmpresa.setEmpresaModelo(empresaModeloBack);
        assertThat(anexoRequeridoEmpresa.getEmpresaModelo()).isEqualTo(empresaModeloBack);

        anexoRequeridoEmpresa.empresaModelo(null);
        assertThat(anexoRequeridoEmpresa.getEmpresaModelo()).isNull();
    }
}
