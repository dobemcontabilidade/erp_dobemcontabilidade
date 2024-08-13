package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AdicionalEnquadramentoTestSamples.*;
import static com.dobemcontabilidade.domain.AnexoRequeridoEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.EmpresaModeloTestSamples.*;
import static com.dobemcontabilidade.domain.EnquadramentoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EnquadramentoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Enquadramento.class);
        Enquadramento enquadramento1 = getEnquadramentoSample1();
        Enquadramento enquadramento2 = new Enquadramento();
        assertThat(enquadramento1).isNotEqualTo(enquadramento2);

        enquadramento2.setId(enquadramento1.getId());
        assertThat(enquadramento1).isEqualTo(enquadramento2);

        enquadramento2 = getEnquadramentoSample2();
        assertThat(enquadramento1).isNotEqualTo(enquadramento2);
    }

    @Test
    void anexoRequeridoEmpresaTest() {
        Enquadramento enquadramento = getEnquadramentoRandomSampleGenerator();
        AnexoRequeridoEmpresa anexoRequeridoEmpresaBack = getAnexoRequeridoEmpresaRandomSampleGenerator();

        enquadramento.addAnexoRequeridoEmpresa(anexoRequeridoEmpresaBack);
        assertThat(enquadramento.getAnexoRequeridoEmpresas()).containsOnly(anexoRequeridoEmpresaBack);
        assertThat(anexoRequeridoEmpresaBack.getEnquadramento()).isEqualTo(enquadramento);

        enquadramento.removeAnexoRequeridoEmpresa(anexoRequeridoEmpresaBack);
        assertThat(enquadramento.getAnexoRequeridoEmpresas()).doesNotContain(anexoRequeridoEmpresaBack);
        assertThat(anexoRequeridoEmpresaBack.getEnquadramento()).isNull();

        enquadramento.anexoRequeridoEmpresas(new HashSet<>(Set.of(anexoRequeridoEmpresaBack)));
        assertThat(enquadramento.getAnexoRequeridoEmpresas()).containsOnly(anexoRequeridoEmpresaBack);
        assertThat(anexoRequeridoEmpresaBack.getEnquadramento()).isEqualTo(enquadramento);

        enquadramento.setAnexoRequeridoEmpresas(new HashSet<>());
        assertThat(enquadramento.getAnexoRequeridoEmpresas()).doesNotContain(anexoRequeridoEmpresaBack);
        assertThat(anexoRequeridoEmpresaBack.getEnquadramento()).isNull();
    }

    @Test
    void empresaModeloTest() {
        Enquadramento enquadramento = getEnquadramentoRandomSampleGenerator();
        EmpresaModelo empresaModeloBack = getEmpresaModeloRandomSampleGenerator();

        enquadramento.addEmpresaModelo(empresaModeloBack);
        assertThat(enquadramento.getEmpresaModelos()).containsOnly(empresaModeloBack);
        assertThat(empresaModeloBack.getEnquadramento()).isEqualTo(enquadramento);

        enquadramento.removeEmpresaModelo(empresaModeloBack);
        assertThat(enquadramento.getEmpresaModelos()).doesNotContain(empresaModeloBack);
        assertThat(empresaModeloBack.getEnquadramento()).isNull();

        enquadramento.empresaModelos(new HashSet<>(Set.of(empresaModeloBack)));
        assertThat(enquadramento.getEmpresaModelos()).containsOnly(empresaModeloBack);
        assertThat(empresaModeloBack.getEnquadramento()).isEqualTo(enquadramento);

        enquadramento.setEmpresaModelos(new HashSet<>());
        assertThat(enquadramento.getEmpresaModelos()).doesNotContain(empresaModeloBack);
        assertThat(empresaModeloBack.getEnquadramento()).isNull();
    }

    @Test
    void adicionalEnquadramentoTest() {
        Enquadramento enquadramento = getEnquadramentoRandomSampleGenerator();
        AdicionalEnquadramento adicionalEnquadramentoBack = getAdicionalEnquadramentoRandomSampleGenerator();

        enquadramento.addAdicionalEnquadramento(adicionalEnquadramentoBack);
        assertThat(enquadramento.getAdicionalEnquadramentos()).containsOnly(adicionalEnquadramentoBack);
        assertThat(adicionalEnquadramentoBack.getEnquadramento()).isEqualTo(enquadramento);

        enquadramento.removeAdicionalEnquadramento(adicionalEnquadramentoBack);
        assertThat(enquadramento.getAdicionalEnquadramentos()).doesNotContain(adicionalEnquadramentoBack);
        assertThat(adicionalEnquadramentoBack.getEnquadramento()).isNull();

        enquadramento.adicionalEnquadramentos(new HashSet<>(Set.of(adicionalEnquadramentoBack)));
        assertThat(enquadramento.getAdicionalEnquadramentos()).containsOnly(adicionalEnquadramentoBack);
        assertThat(adicionalEnquadramentoBack.getEnquadramento()).isEqualTo(enquadramento);

        enquadramento.setAdicionalEnquadramentos(new HashSet<>());
        assertThat(enquadramento.getAdicionalEnquadramentos()).doesNotContain(adicionalEnquadramentoBack);
        assertThat(adicionalEnquadramentoBack.getEnquadramento()).isNull();
    }
}
