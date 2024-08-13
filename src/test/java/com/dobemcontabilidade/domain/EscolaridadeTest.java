package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.EscolaridadePessoaTestSamples.*;
import static com.dobemcontabilidade.domain.EscolaridadeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EscolaridadeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Escolaridade.class);
        Escolaridade escolaridade1 = getEscolaridadeSample1();
        Escolaridade escolaridade2 = new Escolaridade();
        assertThat(escolaridade1).isNotEqualTo(escolaridade2);

        escolaridade2.setId(escolaridade1.getId());
        assertThat(escolaridade1).isEqualTo(escolaridade2);

        escolaridade2 = getEscolaridadeSample2();
        assertThat(escolaridade1).isNotEqualTo(escolaridade2);
    }

    @Test
    void escolaridadePessoaTest() {
        Escolaridade escolaridade = getEscolaridadeRandomSampleGenerator();
        EscolaridadePessoa escolaridadePessoaBack = getEscolaridadePessoaRandomSampleGenerator();

        escolaridade.addEscolaridadePessoa(escolaridadePessoaBack);
        assertThat(escolaridade.getEscolaridadePessoas()).containsOnly(escolaridadePessoaBack);
        assertThat(escolaridadePessoaBack.getEscolaridade()).isEqualTo(escolaridade);

        escolaridade.removeEscolaridadePessoa(escolaridadePessoaBack);
        assertThat(escolaridade.getEscolaridadePessoas()).doesNotContain(escolaridadePessoaBack);
        assertThat(escolaridadePessoaBack.getEscolaridade()).isNull();

        escolaridade.escolaridadePessoas(new HashSet<>(Set.of(escolaridadePessoaBack)));
        assertThat(escolaridade.getEscolaridadePessoas()).containsOnly(escolaridadePessoaBack);
        assertThat(escolaridadePessoaBack.getEscolaridade()).isEqualTo(escolaridade);

        escolaridade.setEscolaridadePessoas(new HashSet<>());
        assertThat(escolaridade.getEscolaridadePessoas()).doesNotContain(escolaridadePessoaBack);
        assertThat(escolaridadePessoaBack.getEscolaridade()).isNull();
    }
}
