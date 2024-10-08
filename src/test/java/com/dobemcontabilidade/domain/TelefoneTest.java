package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.PessoaTestSamples.*;
import static com.dobemcontabilidade.domain.TelefoneTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TelefoneTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Telefone.class);
        Telefone telefone1 = getTelefoneSample1();
        Telefone telefone2 = new Telefone();
        assertThat(telefone1).isNotEqualTo(telefone2);

        telefone2.setId(telefone1.getId());
        assertThat(telefone1).isEqualTo(telefone2);

        telefone2 = getTelefoneSample2();
        assertThat(telefone1).isNotEqualTo(telefone2);
    }

    @Test
    void pessoaTest() {
        Telefone telefone = getTelefoneRandomSampleGenerator();
        Pessoa pessoaBack = getPessoaRandomSampleGenerator();

        telefone.setPessoa(pessoaBack);
        assertThat(telefone.getPessoa()).isEqualTo(pessoaBack);

        telefone.pessoa(null);
        assertThat(telefone.getPessoa()).isNull();
    }
}
