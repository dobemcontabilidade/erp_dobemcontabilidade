package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.DocsPessoaTestSamples.*;
import static com.dobemcontabilidade.domain.PessoaFisicaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DocsPessoaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocsPessoa.class);
        DocsPessoa docsPessoa1 = getDocsPessoaSample1();
        DocsPessoa docsPessoa2 = new DocsPessoa();
        assertThat(docsPessoa1).isNotEqualTo(docsPessoa2);

        docsPessoa2.setId(docsPessoa1.getId());
        assertThat(docsPessoa1).isEqualTo(docsPessoa2);

        docsPessoa2 = getDocsPessoaSample2();
        assertThat(docsPessoa1).isNotEqualTo(docsPessoa2);
    }

    @Test
    void pessoaTest() {
        DocsPessoa docsPessoa = getDocsPessoaRandomSampleGenerator();
        PessoaFisica pessoaFisicaBack = getPessoaFisicaRandomSampleGenerator();

        docsPessoa.setPessoa(pessoaFisicaBack);
        assertThat(docsPessoa.getPessoa()).isEqualTo(pessoaFisicaBack);

        docsPessoa.pessoa(null);
        assertThat(docsPessoa.getPessoa()).isNull();
    }
}
