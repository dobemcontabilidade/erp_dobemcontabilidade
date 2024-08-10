package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AnexoPessoaTestSamples.*;
import static com.dobemcontabilidade.domain.PessoaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnexoPessoaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnexoPessoa.class);
        AnexoPessoa anexoPessoa1 = getAnexoPessoaSample1();
        AnexoPessoa anexoPessoa2 = new AnexoPessoa();
        assertThat(anexoPessoa1).isNotEqualTo(anexoPessoa2);

        anexoPessoa2.setId(anexoPessoa1.getId());
        assertThat(anexoPessoa1).isEqualTo(anexoPessoa2);

        anexoPessoa2 = getAnexoPessoaSample2();
        assertThat(anexoPessoa1).isNotEqualTo(anexoPessoa2);
    }

    @Test
    void pessoaTest() {
        AnexoPessoa anexoPessoa = getAnexoPessoaRandomSampleGenerator();
        Pessoa pessoaBack = getPessoaRandomSampleGenerator();

        anexoPessoa.setPessoa(pessoaBack);
        assertThat(anexoPessoa.getPessoa()).isEqualTo(pessoaBack);

        anexoPessoa.pessoa(null);
        assertThat(anexoPessoa.getPessoa()).isNull();
    }
}
