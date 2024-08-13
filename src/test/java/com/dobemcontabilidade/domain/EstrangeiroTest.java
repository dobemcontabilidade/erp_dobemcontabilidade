package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.EstrangeiroTestSamples.*;
import static com.dobemcontabilidade.domain.FuncionarioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EstrangeiroTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Estrangeiro.class);
        Estrangeiro estrangeiro1 = getEstrangeiroSample1();
        Estrangeiro estrangeiro2 = new Estrangeiro();
        assertThat(estrangeiro1).isNotEqualTo(estrangeiro2);

        estrangeiro2.setId(estrangeiro1.getId());
        assertThat(estrangeiro1).isEqualTo(estrangeiro2);

        estrangeiro2 = getEstrangeiroSample2();
        assertThat(estrangeiro1).isNotEqualTo(estrangeiro2);
    }

    @Test
    void funcionarioTest() {
        Estrangeiro estrangeiro = getEstrangeiroRandomSampleGenerator();
        Funcionario funcionarioBack = getFuncionarioRandomSampleGenerator();

        estrangeiro.setFuncionario(funcionarioBack);
        assertThat(estrangeiro.getFuncionario()).isEqualTo(funcionarioBack);

        estrangeiro.funcionario(null);
        assertThat(estrangeiro.getFuncionario()).isNull();
    }
}
