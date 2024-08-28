package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.CertificadoDigitalEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.CertificadoDigitalTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CertificadoDigitalTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CertificadoDigital.class);
        CertificadoDigital certificadoDigital1 = getCertificadoDigitalSample1();
        CertificadoDigital certificadoDigital2 = new CertificadoDigital();
        assertThat(certificadoDigital1).isNotEqualTo(certificadoDigital2);

        certificadoDigital2.setId(certificadoDigital1.getId());
        assertThat(certificadoDigital1).isEqualTo(certificadoDigital2);

        certificadoDigital2 = getCertificadoDigitalSample2();
        assertThat(certificadoDigital1).isNotEqualTo(certificadoDigital2);
    }

    @Test
    void certificadoDigitalEmpresaTest() {
        CertificadoDigital certificadoDigital = getCertificadoDigitalRandomSampleGenerator();
        CertificadoDigitalEmpresa certificadoDigitalEmpresaBack = getCertificadoDigitalEmpresaRandomSampleGenerator();

        certificadoDigital.addCertificadoDigitalEmpresa(certificadoDigitalEmpresaBack);
        assertThat(certificadoDigital.getCertificadoDigitalEmpresas()).containsOnly(certificadoDigitalEmpresaBack);
        assertThat(certificadoDigitalEmpresaBack.getCertificadoDigital()).isEqualTo(certificadoDigital);

        certificadoDigital.removeCertificadoDigitalEmpresa(certificadoDigitalEmpresaBack);
        assertThat(certificadoDigital.getCertificadoDigitalEmpresas()).doesNotContain(certificadoDigitalEmpresaBack);
        assertThat(certificadoDigitalEmpresaBack.getCertificadoDigital()).isNull();

        certificadoDigital.certificadoDigitalEmpresas(new HashSet<>(Set.of(certificadoDigitalEmpresaBack)));
        assertThat(certificadoDigital.getCertificadoDigitalEmpresas()).containsOnly(certificadoDigitalEmpresaBack);
        assertThat(certificadoDigitalEmpresaBack.getCertificadoDigital()).isEqualTo(certificadoDigital);

        certificadoDigital.setCertificadoDigitalEmpresas(new HashSet<>());
        assertThat(certificadoDigital.getCertificadoDigitalEmpresas()).doesNotContain(certificadoDigitalEmpresaBack);
        assertThat(certificadoDigitalEmpresaBack.getCertificadoDigital()).isNull();
    }
}
