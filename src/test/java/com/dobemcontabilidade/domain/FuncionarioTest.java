package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.DepartamentoFuncionarioTestSamples.*;
import static com.dobemcontabilidade.domain.EmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.FuncionarioTestSamples.*;
import static com.dobemcontabilidade.domain.PessoaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class FuncionarioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Funcionario.class);
        Funcionario funcionario1 = getFuncionarioSample1();
        Funcionario funcionario2 = new Funcionario();
        assertThat(funcionario1).isNotEqualTo(funcionario2);

        funcionario2.setId(funcionario1.getId());
        assertThat(funcionario1).isEqualTo(funcionario2);

        funcionario2 = getFuncionarioSample2();
        assertThat(funcionario1).isNotEqualTo(funcionario2);
    }

    @Test
    void pessoaTest() {
        Funcionario funcionario = getFuncionarioRandomSampleGenerator();
        Pessoa pessoaBack = getPessoaRandomSampleGenerator();

        funcionario.setPessoa(pessoaBack);
        assertThat(funcionario.getPessoa()).isEqualTo(pessoaBack);

        funcionario.pessoa(null);
        assertThat(funcionario.getPessoa()).isNull();
    }

    @Test
    void departamentoFuncionarioTest() {
        Funcionario funcionario = getFuncionarioRandomSampleGenerator();
        DepartamentoFuncionario departamentoFuncionarioBack = getDepartamentoFuncionarioRandomSampleGenerator();

        funcionario.addDepartamentoFuncionario(departamentoFuncionarioBack);
        assertThat(funcionario.getDepartamentoFuncionarios()).containsOnly(departamentoFuncionarioBack);
        assertThat(departamentoFuncionarioBack.getFuncionario()).isEqualTo(funcionario);

        funcionario.removeDepartamentoFuncionario(departamentoFuncionarioBack);
        assertThat(funcionario.getDepartamentoFuncionarios()).doesNotContain(departamentoFuncionarioBack);
        assertThat(departamentoFuncionarioBack.getFuncionario()).isNull();

        funcionario.departamentoFuncionarios(new HashSet<>(Set.of(departamentoFuncionarioBack)));
        assertThat(funcionario.getDepartamentoFuncionarios()).containsOnly(departamentoFuncionarioBack);
        assertThat(departamentoFuncionarioBack.getFuncionario()).isEqualTo(funcionario);

        funcionario.setDepartamentoFuncionarios(new HashSet<>());
        assertThat(funcionario.getDepartamentoFuncionarios()).doesNotContain(departamentoFuncionarioBack);
        assertThat(departamentoFuncionarioBack.getFuncionario()).isNull();
    }

    @Test
    void empresaTest() {
        Funcionario funcionario = getFuncionarioRandomSampleGenerator();
        Empresa empresaBack = getEmpresaRandomSampleGenerator();

        funcionario.setEmpresa(empresaBack);
        assertThat(funcionario.getEmpresa()).isEqualTo(empresaBack);

        funcionario.empresa(null);
        assertThat(funcionario.getEmpresa()).isNull();
    }
}
