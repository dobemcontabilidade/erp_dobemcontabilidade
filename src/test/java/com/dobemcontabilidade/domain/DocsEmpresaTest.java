package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.DocsEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.PessoajuridicaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DocsEmpresaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocsEmpresa.class);
        DocsEmpresa docsEmpresa1 = getDocsEmpresaSample1();
        DocsEmpresa docsEmpresa2 = new DocsEmpresa();
        assertThat(docsEmpresa1).isNotEqualTo(docsEmpresa2);

        docsEmpresa2.setId(docsEmpresa1.getId());
        assertThat(docsEmpresa1).isEqualTo(docsEmpresa2);

        docsEmpresa2 = getDocsEmpresaSample2();
        assertThat(docsEmpresa1).isNotEqualTo(docsEmpresa2);
    }

    @Test
    void pessoaJuridicaTest() {
        DocsEmpresa docsEmpresa = getDocsEmpresaRandomSampleGenerator();
        Pessoajuridica pessoajuridicaBack = getPessoajuridicaRandomSampleGenerator();

        docsEmpresa.setPessoaJuridica(pessoajuridicaBack);
        assertThat(docsEmpresa.getPessoaJuridica()).isEqualTo(pessoajuridicaBack);

        docsEmpresa.pessoaJuridica(null);
        assertThat(docsEmpresa.getPessoaJuridica()).isNull();
    }
}
