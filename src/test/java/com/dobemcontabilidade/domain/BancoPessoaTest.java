package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.BancoPessoaTestSamples.*;
import static com.dobemcontabilidade.domain.BancoTestSamples.*;
import static com.dobemcontabilidade.domain.PessoaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BancoPessoaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BancoPessoa.class);
        BancoPessoa bancoPessoa1 = getBancoPessoaSample1();
        BancoPessoa bancoPessoa2 = new BancoPessoa();
        assertThat(bancoPessoa1).isNotEqualTo(bancoPessoa2);

        bancoPessoa2.setId(bancoPessoa1.getId());
        assertThat(bancoPessoa1).isEqualTo(bancoPessoa2);

        bancoPessoa2 = getBancoPessoaSample2();
        assertThat(bancoPessoa1).isNotEqualTo(bancoPessoa2);
    }

    @Test
    void pessoaTest() {
        BancoPessoa bancoPessoa = getBancoPessoaRandomSampleGenerator();
        Pessoa pessoaBack = getPessoaRandomSampleGenerator();

        bancoPessoa.setPessoa(pessoaBack);
        assertThat(bancoPessoa.getPessoa()).isEqualTo(pessoaBack);

        bancoPessoa.pessoa(null);
        assertThat(bancoPessoa.getPessoa()).isNull();
    }

    @Test
    void bancoTest() {
        BancoPessoa bancoPessoa = getBancoPessoaRandomSampleGenerator();
        Banco bancoBack = getBancoRandomSampleGenerator();

        bancoPessoa.setBanco(bancoBack);
        assertThat(bancoPessoa.getBanco()).isEqualTo(bancoBack);

        bancoPessoa.banco(null);
        assertThat(bancoPessoa.getBanco()).isNull();
    }
}
