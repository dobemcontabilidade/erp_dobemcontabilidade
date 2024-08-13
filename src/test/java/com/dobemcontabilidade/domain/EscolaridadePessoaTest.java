package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.EscolaridadePessoaTestSamples.*;
import static com.dobemcontabilidade.domain.EscolaridadeTestSamples.*;
import static com.dobemcontabilidade.domain.PessoaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EscolaridadePessoaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EscolaridadePessoa.class);
        EscolaridadePessoa escolaridadePessoa1 = getEscolaridadePessoaSample1();
        EscolaridadePessoa escolaridadePessoa2 = new EscolaridadePessoa();
        assertThat(escolaridadePessoa1).isNotEqualTo(escolaridadePessoa2);

        escolaridadePessoa2.setId(escolaridadePessoa1.getId());
        assertThat(escolaridadePessoa1).isEqualTo(escolaridadePessoa2);

        escolaridadePessoa2 = getEscolaridadePessoaSample2();
        assertThat(escolaridadePessoa1).isNotEqualTo(escolaridadePessoa2);
    }

    @Test
    void pessoaTest() {
        EscolaridadePessoa escolaridadePessoa = getEscolaridadePessoaRandomSampleGenerator();
        Pessoa pessoaBack = getPessoaRandomSampleGenerator();

        escolaridadePessoa.setPessoa(pessoaBack);
        assertThat(escolaridadePessoa.getPessoa()).isEqualTo(pessoaBack);

        escolaridadePessoa.pessoa(null);
        assertThat(escolaridadePessoa.getPessoa()).isNull();
    }

    @Test
    void escolaridadeTest() {
        EscolaridadePessoa escolaridadePessoa = getEscolaridadePessoaRandomSampleGenerator();
        Escolaridade escolaridadeBack = getEscolaridadeRandomSampleGenerator();

        escolaridadePessoa.setEscolaridade(escolaridadeBack);
        assertThat(escolaridadePessoa.getEscolaridade()).isEqualTo(escolaridadeBack);

        escolaridadePessoa.escolaridade(null);
        assertThat(escolaridadePessoa.getEscolaridade()).isNull();
    }
}
