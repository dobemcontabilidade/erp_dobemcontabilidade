package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.CertificadoDigitalEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.FornecedorCertificadoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class FornecedorCertificadoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FornecedorCertificado.class);
        FornecedorCertificado fornecedorCertificado1 = getFornecedorCertificadoSample1();
        FornecedorCertificado fornecedorCertificado2 = new FornecedorCertificado();
        assertThat(fornecedorCertificado1).isNotEqualTo(fornecedorCertificado2);

        fornecedorCertificado2.setId(fornecedorCertificado1.getId());
        assertThat(fornecedorCertificado1).isEqualTo(fornecedorCertificado2);

        fornecedorCertificado2 = getFornecedorCertificadoSample2();
        assertThat(fornecedorCertificado1).isNotEqualTo(fornecedorCertificado2);
    }

    @Test
    void certificadoDigitalEmpresaTest() {
        FornecedorCertificado fornecedorCertificado = getFornecedorCertificadoRandomSampleGenerator();
        CertificadoDigitalEmpresa certificadoDigitalEmpresaBack = getCertificadoDigitalEmpresaRandomSampleGenerator();

        fornecedorCertificado.addCertificadoDigitalEmpresa(certificadoDigitalEmpresaBack);
        assertThat(fornecedorCertificado.getCertificadoDigitalEmpresas()).containsOnly(certificadoDigitalEmpresaBack);
        assertThat(certificadoDigitalEmpresaBack.getFornecedorCertificado()).isEqualTo(fornecedorCertificado);

        fornecedorCertificado.removeCertificadoDigitalEmpresa(certificadoDigitalEmpresaBack);
        assertThat(fornecedorCertificado.getCertificadoDigitalEmpresas()).doesNotContain(certificadoDigitalEmpresaBack);
        assertThat(certificadoDigitalEmpresaBack.getFornecedorCertificado()).isNull();

        fornecedorCertificado.certificadoDigitalEmpresas(new HashSet<>(Set.of(certificadoDigitalEmpresaBack)));
        assertThat(fornecedorCertificado.getCertificadoDigitalEmpresas()).containsOnly(certificadoDigitalEmpresaBack);
        assertThat(certificadoDigitalEmpresaBack.getFornecedorCertificado()).isEqualTo(fornecedorCertificado);

        fornecedorCertificado.setCertificadoDigitalEmpresas(new HashSet<>());
        assertThat(fornecedorCertificado.getCertificadoDigitalEmpresas()).doesNotContain(certificadoDigitalEmpresaBack);
        assertThat(certificadoDigitalEmpresaBack.getFornecedorCertificado()).isNull();
    }
}
