package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AnexoPessoaTestSamples.*;
import static com.dobemcontabilidade.domain.AnexoRequeridoPessoaTestSamples.*;
import static com.dobemcontabilidade.domain.AnexoRequeridoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnexoRequeridoPessoaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnexoRequeridoPessoa.class);
        AnexoRequeridoPessoa anexoRequeridoPessoa1 = getAnexoRequeridoPessoaSample1();
        AnexoRequeridoPessoa anexoRequeridoPessoa2 = new AnexoRequeridoPessoa();
        assertThat(anexoRequeridoPessoa1).isNotEqualTo(anexoRequeridoPessoa2);

        anexoRequeridoPessoa2.setId(anexoRequeridoPessoa1.getId());
        assertThat(anexoRequeridoPessoa1).isEqualTo(anexoRequeridoPessoa2);

        anexoRequeridoPessoa2 = getAnexoRequeridoPessoaSample2();
        assertThat(anexoRequeridoPessoa1).isNotEqualTo(anexoRequeridoPessoa2);
    }

    @Test
    void anexoPessoaTest() {
        AnexoRequeridoPessoa anexoRequeridoPessoa = getAnexoRequeridoPessoaRandomSampleGenerator();
        AnexoPessoa anexoPessoaBack = getAnexoPessoaRandomSampleGenerator();

        anexoRequeridoPessoa.setAnexoPessoa(anexoPessoaBack);
        assertThat(anexoRequeridoPessoa.getAnexoPessoa()).isEqualTo(anexoPessoaBack);

        anexoRequeridoPessoa.anexoPessoa(null);
        assertThat(anexoRequeridoPessoa.getAnexoPessoa()).isNull();
    }

    @Test
    void anexoRequeridoTest() {
        AnexoRequeridoPessoa anexoRequeridoPessoa = getAnexoRequeridoPessoaRandomSampleGenerator();
        AnexoRequerido anexoRequeridoBack = getAnexoRequeridoRandomSampleGenerator();

        anexoRequeridoPessoa.setAnexoRequerido(anexoRequeridoBack);
        assertThat(anexoRequeridoPessoa.getAnexoRequerido()).isEqualTo(anexoRequeridoBack);

        anexoRequeridoPessoa.anexoRequerido(null);
        assertThat(anexoRequeridoPessoa.getAnexoRequerido()).isNull();
    }
}
