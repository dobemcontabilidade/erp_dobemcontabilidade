package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.CertificadoDigitalEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.CertificadoDigitalTestSamples.*;
import static com.dobemcontabilidade.domain.FornecedorCertificadoTestSamples.*;
import static com.dobemcontabilidade.domain.PessoajuridicaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CertificadoDigitalEmpresaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CertificadoDigitalEmpresa.class);
        CertificadoDigitalEmpresa certificadoDigitalEmpresa1 = getCertificadoDigitalEmpresaSample1();
        CertificadoDigitalEmpresa certificadoDigitalEmpresa2 = new CertificadoDigitalEmpresa();
        assertThat(certificadoDigitalEmpresa1).isNotEqualTo(certificadoDigitalEmpresa2);

        certificadoDigitalEmpresa2.setId(certificadoDigitalEmpresa1.getId());
        assertThat(certificadoDigitalEmpresa1).isEqualTo(certificadoDigitalEmpresa2);

        certificadoDigitalEmpresa2 = getCertificadoDigitalEmpresaSample2();
        assertThat(certificadoDigitalEmpresa1).isNotEqualTo(certificadoDigitalEmpresa2);
    }

    @Test
    void pessoaJuridicaTest() {
        CertificadoDigitalEmpresa certificadoDigitalEmpresa = getCertificadoDigitalEmpresaRandomSampleGenerator();
        Pessoajuridica pessoajuridicaBack = getPessoajuridicaRandomSampleGenerator();

        certificadoDigitalEmpresa.setPessoaJuridica(pessoajuridicaBack);
        assertThat(certificadoDigitalEmpresa.getPessoaJuridica()).isEqualTo(pessoajuridicaBack);

        certificadoDigitalEmpresa.pessoaJuridica(null);
        assertThat(certificadoDigitalEmpresa.getPessoaJuridica()).isNull();
    }

    @Test
    void certificadoDigitalTest() {
        CertificadoDigitalEmpresa certificadoDigitalEmpresa = getCertificadoDigitalEmpresaRandomSampleGenerator();
        CertificadoDigital certificadoDigitalBack = getCertificadoDigitalRandomSampleGenerator();

        certificadoDigitalEmpresa.setCertificadoDigital(certificadoDigitalBack);
        assertThat(certificadoDigitalEmpresa.getCertificadoDigital()).isEqualTo(certificadoDigitalBack);

        certificadoDigitalEmpresa.certificadoDigital(null);
        assertThat(certificadoDigitalEmpresa.getCertificadoDigital()).isNull();
    }

    @Test
    void fornecedorCertificadoTest() {
        CertificadoDigitalEmpresa certificadoDigitalEmpresa = getCertificadoDigitalEmpresaRandomSampleGenerator();
        FornecedorCertificado fornecedorCertificadoBack = getFornecedorCertificadoRandomSampleGenerator();

        certificadoDigitalEmpresa.setFornecedorCertificado(fornecedorCertificadoBack);
        assertThat(certificadoDigitalEmpresa.getFornecedorCertificado()).isEqualTo(fornecedorCertificadoBack);

        certificadoDigitalEmpresa.fornecedorCertificado(null);
        assertThat(certificadoDigitalEmpresa.getFornecedorCertificado()).isNull();
    }
}
