package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.CertificadoDigitalTestSamples.*;
import static com.dobemcontabilidade.domain.EmpresaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
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
    void empresaTest() {
        CertificadoDigital certificadoDigital = getCertificadoDigitalRandomSampleGenerator();
        Empresa empresaBack = getEmpresaRandomSampleGenerator();

        certificadoDigital.setEmpresa(empresaBack);
        assertThat(certificadoDigital.getEmpresa()).isEqualTo(empresaBack);

        certificadoDigital.empresa(null);
        assertThat(certificadoDigital.getEmpresa()).isNull();
    }
}
