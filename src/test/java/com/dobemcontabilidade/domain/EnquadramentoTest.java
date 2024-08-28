package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AdicionalEnquadramentoTestSamples.*;
import static com.dobemcontabilidade.domain.EmpresaTestSamples.*;
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
    void empresaTest() {
        Enquadramento enquadramento = getEnquadramentoRandomSampleGenerator();
        Empresa empresaBack = getEmpresaRandomSampleGenerator();

        enquadramento.addEmpresa(empresaBack);
        assertThat(enquadramento.getEmpresas()).containsOnly(empresaBack);
        assertThat(empresaBack.getEnquadramento()).isEqualTo(enquadramento);

        enquadramento.removeEmpresa(empresaBack);
        assertThat(enquadramento.getEmpresas()).doesNotContain(empresaBack);
        assertThat(empresaBack.getEnquadramento()).isNull();

        enquadramento.empresas(new HashSet<>(Set.of(empresaBack)));
        assertThat(enquadramento.getEmpresas()).containsOnly(empresaBack);
        assertThat(empresaBack.getEnquadramento()).isEqualTo(enquadramento);

        enquadramento.setEmpresas(new HashSet<>());
        assertThat(enquadramento.getEmpresas()).doesNotContain(empresaBack);
        assertThat(empresaBack.getEnquadramento()).isNull();
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
