package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.CertificadoDigitalEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.DocsEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.EmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.EnderecoEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.PessoajuridicaTestSamples.*;
import static com.dobemcontabilidade.domain.RedeSocialEmpresaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PessoajuridicaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pessoajuridica.class);
        Pessoajuridica pessoajuridica1 = getPessoajuridicaSample1();
        Pessoajuridica pessoajuridica2 = new Pessoajuridica();
        assertThat(pessoajuridica1).isNotEqualTo(pessoajuridica2);

        pessoajuridica2.setId(pessoajuridica1.getId());
        assertThat(pessoajuridica1).isEqualTo(pessoajuridica2);

        pessoajuridica2 = getPessoajuridicaSample2();
        assertThat(pessoajuridica1).isNotEqualTo(pessoajuridica2);
    }

    @Test
    void redeSocialEmpresaTest() {
        Pessoajuridica pessoajuridica = getPessoajuridicaRandomSampleGenerator();
        RedeSocialEmpresa redeSocialEmpresaBack = getRedeSocialEmpresaRandomSampleGenerator();

        pessoajuridica.addRedeSocialEmpresa(redeSocialEmpresaBack);
        assertThat(pessoajuridica.getRedeSocialEmpresas()).containsOnly(redeSocialEmpresaBack);
        assertThat(redeSocialEmpresaBack.getPessoajuridica()).isEqualTo(pessoajuridica);

        pessoajuridica.removeRedeSocialEmpresa(redeSocialEmpresaBack);
        assertThat(pessoajuridica.getRedeSocialEmpresas()).doesNotContain(redeSocialEmpresaBack);
        assertThat(redeSocialEmpresaBack.getPessoajuridica()).isNull();

        pessoajuridica.redeSocialEmpresas(new HashSet<>(Set.of(redeSocialEmpresaBack)));
        assertThat(pessoajuridica.getRedeSocialEmpresas()).containsOnly(redeSocialEmpresaBack);
        assertThat(redeSocialEmpresaBack.getPessoajuridica()).isEqualTo(pessoajuridica);

        pessoajuridica.setRedeSocialEmpresas(new HashSet<>());
        assertThat(pessoajuridica.getRedeSocialEmpresas()).doesNotContain(redeSocialEmpresaBack);
        assertThat(redeSocialEmpresaBack.getPessoajuridica()).isNull();
    }

    @Test
    void certificadoDigitalEmpresaTest() {
        Pessoajuridica pessoajuridica = getPessoajuridicaRandomSampleGenerator();
        CertificadoDigitalEmpresa certificadoDigitalEmpresaBack = getCertificadoDigitalEmpresaRandomSampleGenerator();

        pessoajuridica.addCertificadoDigitalEmpresa(certificadoDigitalEmpresaBack);
        assertThat(pessoajuridica.getCertificadoDigitalEmpresas()).containsOnly(certificadoDigitalEmpresaBack);
        assertThat(certificadoDigitalEmpresaBack.getPessoaJuridica()).isEqualTo(pessoajuridica);

        pessoajuridica.removeCertificadoDigitalEmpresa(certificadoDigitalEmpresaBack);
        assertThat(pessoajuridica.getCertificadoDigitalEmpresas()).doesNotContain(certificadoDigitalEmpresaBack);
        assertThat(certificadoDigitalEmpresaBack.getPessoaJuridica()).isNull();

        pessoajuridica.certificadoDigitalEmpresas(new HashSet<>(Set.of(certificadoDigitalEmpresaBack)));
        assertThat(pessoajuridica.getCertificadoDigitalEmpresas()).containsOnly(certificadoDigitalEmpresaBack);
        assertThat(certificadoDigitalEmpresaBack.getPessoaJuridica()).isEqualTo(pessoajuridica);

        pessoajuridica.setCertificadoDigitalEmpresas(new HashSet<>());
        assertThat(pessoajuridica.getCertificadoDigitalEmpresas()).doesNotContain(certificadoDigitalEmpresaBack);
        assertThat(certificadoDigitalEmpresaBack.getPessoaJuridica()).isNull();
    }

    @Test
    void docsEmpresaTest() {
        Pessoajuridica pessoajuridica = getPessoajuridicaRandomSampleGenerator();
        DocsEmpresa docsEmpresaBack = getDocsEmpresaRandomSampleGenerator();

        pessoajuridica.addDocsEmpresa(docsEmpresaBack);
        assertThat(pessoajuridica.getDocsEmpresas()).containsOnly(docsEmpresaBack);
        assertThat(docsEmpresaBack.getPessoaJuridica()).isEqualTo(pessoajuridica);

        pessoajuridica.removeDocsEmpresa(docsEmpresaBack);
        assertThat(pessoajuridica.getDocsEmpresas()).doesNotContain(docsEmpresaBack);
        assertThat(docsEmpresaBack.getPessoaJuridica()).isNull();

        pessoajuridica.docsEmpresas(new HashSet<>(Set.of(docsEmpresaBack)));
        assertThat(pessoajuridica.getDocsEmpresas()).containsOnly(docsEmpresaBack);
        assertThat(docsEmpresaBack.getPessoaJuridica()).isEqualTo(pessoajuridica);

        pessoajuridica.setDocsEmpresas(new HashSet<>());
        assertThat(pessoajuridica.getDocsEmpresas()).doesNotContain(docsEmpresaBack);
        assertThat(docsEmpresaBack.getPessoaJuridica()).isNull();
    }

    @Test
    void enderecoEmpresaTest() {
        Pessoajuridica pessoajuridica = getPessoajuridicaRandomSampleGenerator();
        EnderecoEmpresa enderecoEmpresaBack = getEnderecoEmpresaRandomSampleGenerator();

        pessoajuridica.addEnderecoEmpresa(enderecoEmpresaBack);
        assertThat(pessoajuridica.getEnderecoEmpresas()).containsOnly(enderecoEmpresaBack);
        assertThat(enderecoEmpresaBack.getPessoaJuridica()).isEqualTo(pessoajuridica);

        pessoajuridica.removeEnderecoEmpresa(enderecoEmpresaBack);
        assertThat(pessoajuridica.getEnderecoEmpresas()).doesNotContain(enderecoEmpresaBack);
        assertThat(enderecoEmpresaBack.getPessoaJuridica()).isNull();

        pessoajuridica.enderecoEmpresas(new HashSet<>(Set.of(enderecoEmpresaBack)));
        assertThat(pessoajuridica.getEnderecoEmpresas()).containsOnly(enderecoEmpresaBack);
        assertThat(enderecoEmpresaBack.getPessoaJuridica()).isEqualTo(pessoajuridica);

        pessoajuridica.setEnderecoEmpresas(new HashSet<>());
        assertThat(pessoajuridica.getEnderecoEmpresas()).doesNotContain(enderecoEmpresaBack);
        assertThat(enderecoEmpresaBack.getPessoaJuridica()).isNull();
    }

    @Test
    void empresaTest() {
        Pessoajuridica pessoajuridica = getPessoajuridicaRandomSampleGenerator();
        Empresa empresaBack = getEmpresaRandomSampleGenerator();

        pessoajuridica.setEmpresa(empresaBack);
        assertThat(pessoajuridica.getEmpresa()).isEqualTo(empresaBack);
        assertThat(empresaBack.getPessoaJuridica()).isEqualTo(pessoajuridica);

        pessoajuridica.empresa(null);
        assertThat(pessoajuridica.getEmpresa()).isNull();
        assertThat(empresaBack.getPessoaJuridica()).isNull();
    }
}
