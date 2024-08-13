package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.DependentesFuncionarioTestSamples.*;
import static com.dobemcontabilidade.domain.FuncionarioTestSamples.*;
import static com.dobemcontabilidade.domain.PessoaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DependentesFuncionarioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DependentesFuncionario.class);
        DependentesFuncionario dependentesFuncionario1 = getDependentesFuncionarioSample1();
        DependentesFuncionario dependentesFuncionario2 = new DependentesFuncionario();
        assertThat(dependentesFuncionario1).isNotEqualTo(dependentesFuncionario2);

        dependentesFuncionario2.setId(dependentesFuncionario1.getId());
        assertThat(dependentesFuncionario1).isEqualTo(dependentesFuncionario2);

        dependentesFuncionario2 = getDependentesFuncionarioSample2();
        assertThat(dependentesFuncionario1).isNotEqualTo(dependentesFuncionario2);
    }

    @Test
    void pessoaTest() {
        DependentesFuncionario dependentesFuncionario = getDependentesFuncionarioRandomSampleGenerator();
        Pessoa pessoaBack = getPessoaRandomSampleGenerator();

        dependentesFuncionario.setPessoa(pessoaBack);
        assertThat(dependentesFuncionario.getPessoa()).isEqualTo(pessoaBack);

        dependentesFuncionario.pessoa(null);
        assertThat(dependentesFuncionario.getPessoa()).isNull();
    }

    @Test
    void funcionarioTest() {
        DependentesFuncionario dependentesFuncionario = getDependentesFuncionarioRandomSampleGenerator();
        Funcionario funcionarioBack = getFuncionarioRandomSampleGenerator();

        dependentesFuncionario.setFuncionario(funcionarioBack);
        assertThat(dependentesFuncionario.getFuncionario()).isEqualTo(funcionarioBack);

        dependentesFuncionario.funcionario(null);
        assertThat(dependentesFuncionario.getFuncionario()).isNull();
    }
}
