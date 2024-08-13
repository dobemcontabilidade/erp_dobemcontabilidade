package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.DepartamentoFuncionarioTestSamples.*;
import static com.dobemcontabilidade.domain.DepartamentoTestSamples.*;
import static com.dobemcontabilidade.domain.FuncionarioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DepartamentoFuncionarioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DepartamentoFuncionario.class);
        DepartamentoFuncionario departamentoFuncionario1 = getDepartamentoFuncionarioSample1();
        DepartamentoFuncionario departamentoFuncionario2 = new DepartamentoFuncionario();
        assertThat(departamentoFuncionario1).isNotEqualTo(departamentoFuncionario2);

        departamentoFuncionario2.setId(departamentoFuncionario1.getId());
        assertThat(departamentoFuncionario1).isEqualTo(departamentoFuncionario2);

        departamentoFuncionario2 = getDepartamentoFuncionarioSample2();
        assertThat(departamentoFuncionario1).isNotEqualTo(departamentoFuncionario2);
    }

    @Test
    void funcionarioTest() {
        DepartamentoFuncionario departamentoFuncionario = getDepartamentoFuncionarioRandomSampleGenerator();
        Funcionario funcionarioBack = getFuncionarioRandomSampleGenerator();

        departamentoFuncionario.setFuncionario(funcionarioBack);
        assertThat(departamentoFuncionario.getFuncionario()).isEqualTo(funcionarioBack);

        departamentoFuncionario.funcionario(null);
        assertThat(departamentoFuncionario.getFuncionario()).isNull();
    }

    @Test
    void departamentoTest() {
        DepartamentoFuncionario departamentoFuncionario = getDepartamentoFuncionarioRandomSampleGenerator();
        Departamento departamentoBack = getDepartamentoRandomSampleGenerator();

        departamentoFuncionario.setDepartamento(departamentoBack);
        assertThat(departamentoFuncionario.getDepartamento()).isEqualTo(departamentoBack);

        departamentoFuncionario.departamento(null);
        assertThat(departamentoFuncionario.getDepartamento()).isNull();
    }
}
