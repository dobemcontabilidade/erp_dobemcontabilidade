package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.CidadeTestSamples.*;
import static com.dobemcontabilidade.domain.EnderecoPessoaTestSamples.*;
import static com.dobemcontabilidade.domain.PessoaFisicaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EnderecoPessoaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnderecoPessoa.class);
        EnderecoPessoa enderecoPessoa1 = getEnderecoPessoaSample1();
        EnderecoPessoa enderecoPessoa2 = new EnderecoPessoa();
        assertThat(enderecoPessoa1).isNotEqualTo(enderecoPessoa2);

        enderecoPessoa2.setId(enderecoPessoa1.getId());
        assertThat(enderecoPessoa1).isEqualTo(enderecoPessoa2);

        enderecoPessoa2 = getEnderecoPessoaSample2();
        assertThat(enderecoPessoa1).isNotEqualTo(enderecoPessoa2);
    }

    @Test
    void pessoaTest() {
        EnderecoPessoa enderecoPessoa = getEnderecoPessoaRandomSampleGenerator();
        PessoaFisica pessoaFisicaBack = getPessoaFisicaRandomSampleGenerator();

        enderecoPessoa.setPessoa(pessoaFisicaBack);
        assertThat(enderecoPessoa.getPessoa()).isEqualTo(pessoaFisicaBack);

        enderecoPessoa.pessoa(null);
        assertThat(enderecoPessoa.getPessoa()).isNull();
    }

    @Test
    void cidadeTest() {
        EnderecoPessoa enderecoPessoa = getEnderecoPessoaRandomSampleGenerator();
        Cidade cidadeBack = getCidadeRandomSampleGenerator();

        enderecoPessoa.setCidade(cidadeBack);
        assertThat(enderecoPessoa.getCidade()).isEqualTo(cidadeBack);

        enderecoPessoa.cidade(null);
        assertThat(enderecoPessoa.getCidade()).isNull();
    }
}
