package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.EmailTestSamples.*;
import static com.dobemcontabilidade.domain.PessoaFisicaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmailTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Email.class);
        Email email1 = getEmailSample1();
        Email email2 = new Email();
        assertThat(email1).isNotEqualTo(email2);

        email2.setId(email1.getId());
        assertThat(email1).isEqualTo(email2);

        email2 = getEmailSample2();
        assertThat(email1).isNotEqualTo(email2);
    }

    @Test
    void pessoaTest() {
        Email email = getEmailRandomSampleGenerator();
        PessoaFisica pessoaFisicaBack = getPessoaFisicaRandomSampleGenerator();

        email.setPessoa(pessoaFisicaBack);
        assertThat(email.getPessoa()).isEqualTo(pessoaFisicaBack);

        email.pessoa(null);
        assertThat(email.getPessoa()).isNull();
    }
}
