package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.ContratoFuncionarioTestSamples.*;
import static com.dobemcontabilidade.domain.DemissaoFuncionarioTestSamples.*;
import static com.dobemcontabilidade.domain.DepartamentoFuncionarioTestSamples.*;
import static com.dobemcontabilidade.domain.DependentesFuncionarioTestSamples.*;
import static com.dobemcontabilidade.domain.EmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.EmpresaVinculadaTestSamples.*;
import static com.dobemcontabilidade.domain.EstrangeiroTestSamples.*;
import static com.dobemcontabilidade.domain.FuncionarioTestSamples.*;
import static com.dobemcontabilidade.domain.PessoaTestSamples.*;
import static com.dobemcontabilidade.domain.ProfissaoTestSamples.*;
import static com.dobemcontabilidade.domain.UsuarioEmpresaTestSamples.*;
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
    void usuarioEmpresaTest() {
        Funcionario funcionario = getFuncionarioRandomSampleGenerator();
        UsuarioEmpresa usuarioEmpresaBack = getUsuarioEmpresaRandomSampleGenerator();

        funcionario.setUsuarioEmpresa(usuarioEmpresaBack);
        assertThat(funcionario.getUsuarioEmpresa()).isEqualTo(usuarioEmpresaBack);

        funcionario.usuarioEmpresa(null);
        assertThat(funcionario.getUsuarioEmpresa()).isNull();
    }

    @Test
    void estrangeiroTest() {
        Funcionario funcionario = getFuncionarioRandomSampleGenerator();
        Estrangeiro estrangeiroBack = getEstrangeiroRandomSampleGenerator();

        funcionario.addEstrangeiro(estrangeiroBack);
        assertThat(funcionario.getEstrangeiros()).containsOnly(estrangeiroBack);
        assertThat(estrangeiroBack.getFuncionario()).isEqualTo(funcionario);

        funcionario.removeEstrangeiro(estrangeiroBack);
        assertThat(funcionario.getEstrangeiros()).doesNotContain(estrangeiroBack);
        assertThat(estrangeiroBack.getFuncionario()).isNull();

        funcionario.estrangeiros(new HashSet<>(Set.of(estrangeiroBack)));
        assertThat(funcionario.getEstrangeiros()).containsOnly(estrangeiroBack);
        assertThat(estrangeiroBack.getFuncionario()).isEqualTo(funcionario);

        funcionario.setEstrangeiros(new HashSet<>());
        assertThat(funcionario.getEstrangeiros()).doesNotContain(estrangeiroBack);
        assertThat(estrangeiroBack.getFuncionario()).isNull();
    }

    @Test
    void contratoFuncionarioTest() {
        Funcionario funcionario = getFuncionarioRandomSampleGenerator();
        ContratoFuncionario contratoFuncionarioBack = getContratoFuncionarioRandomSampleGenerator();

        funcionario.addContratoFuncionario(contratoFuncionarioBack);
        assertThat(funcionario.getContratoFuncionarios()).containsOnly(contratoFuncionarioBack);
        assertThat(contratoFuncionarioBack.getFuncionario()).isEqualTo(funcionario);

        funcionario.removeContratoFuncionario(contratoFuncionarioBack);
        assertThat(funcionario.getContratoFuncionarios()).doesNotContain(contratoFuncionarioBack);
        assertThat(contratoFuncionarioBack.getFuncionario()).isNull();

        funcionario.contratoFuncionarios(new HashSet<>(Set.of(contratoFuncionarioBack)));
        assertThat(funcionario.getContratoFuncionarios()).containsOnly(contratoFuncionarioBack);
        assertThat(contratoFuncionarioBack.getFuncionario()).isEqualTo(funcionario);

        funcionario.setContratoFuncionarios(new HashSet<>());
        assertThat(funcionario.getContratoFuncionarios()).doesNotContain(contratoFuncionarioBack);
        assertThat(contratoFuncionarioBack.getFuncionario()).isNull();
    }

    @Test
    void demissaoFuncionarioTest() {
        Funcionario funcionario = getFuncionarioRandomSampleGenerator();
        DemissaoFuncionario demissaoFuncionarioBack = getDemissaoFuncionarioRandomSampleGenerator();

        funcionario.addDemissaoFuncionario(demissaoFuncionarioBack);
        assertThat(funcionario.getDemissaoFuncionarios()).containsOnly(demissaoFuncionarioBack);
        assertThat(demissaoFuncionarioBack.getFuncionario()).isEqualTo(funcionario);

        funcionario.removeDemissaoFuncionario(demissaoFuncionarioBack);
        assertThat(funcionario.getDemissaoFuncionarios()).doesNotContain(demissaoFuncionarioBack);
        assertThat(demissaoFuncionarioBack.getFuncionario()).isNull();

        funcionario.demissaoFuncionarios(new HashSet<>(Set.of(demissaoFuncionarioBack)));
        assertThat(funcionario.getDemissaoFuncionarios()).containsOnly(demissaoFuncionarioBack);
        assertThat(demissaoFuncionarioBack.getFuncionario()).isEqualTo(funcionario);

        funcionario.setDemissaoFuncionarios(new HashSet<>());
        assertThat(funcionario.getDemissaoFuncionarios()).doesNotContain(demissaoFuncionarioBack);
        assertThat(demissaoFuncionarioBack.getFuncionario()).isNull();
    }

    @Test
    void dependentesFuncionarioTest() {
        Funcionario funcionario = getFuncionarioRandomSampleGenerator();
        DependentesFuncionario dependentesFuncionarioBack = getDependentesFuncionarioRandomSampleGenerator();

        funcionario.addDependentesFuncionario(dependentesFuncionarioBack);
        assertThat(funcionario.getDependentesFuncionarios()).containsOnly(dependentesFuncionarioBack);
        assertThat(dependentesFuncionarioBack.getFuncionario()).isEqualTo(funcionario);

        funcionario.removeDependentesFuncionario(dependentesFuncionarioBack);
        assertThat(funcionario.getDependentesFuncionarios()).doesNotContain(dependentesFuncionarioBack);
        assertThat(dependentesFuncionarioBack.getFuncionario()).isNull();

        funcionario.dependentesFuncionarios(new HashSet<>(Set.of(dependentesFuncionarioBack)));
        assertThat(funcionario.getDependentesFuncionarios()).containsOnly(dependentesFuncionarioBack);
        assertThat(dependentesFuncionarioBack.getFuncionario()).isEqualTo(funcionario);

        funcionario.setDependentesFuncionarios(new HashSet<>());
        assertThat(funcionario.getDependentesFuncionarios()).doesNotContain(dependentesFuncionarioBack);
        assertThat(dependentesFuncionarioBack.getFuncionario()).isNull();
    }

    @Test
    void empresaVinculadaTest() {
        Funcionario funcionario = getFuncionarioRandomSampleGenerator();
        EmpresaVinculada empresaVinculadaBack = getEmpresaVinculadaRandomSampleGenerator();

        funcionario.addEmpresaVinculada(empresaVinculadaBack);
        assertThat(funcionario.getEmpresaVinculadas()).containsOnly(empresaVinculadaBack);
        assertThat(empresaVinculadaBack.getFuncionario()).isEqualTo(funcionario);

        funcionario.removeEmpresaVinculada(empresaVinculadaBack);
        assertThat(funcionario.getEmpresaVinculadas()).doesNotContain(empresaVinculadaBack);
        assertThat(empresaVinculadaBack.getFuncionario()).isNull();

        funcionario.empresaVinculadas(new HashSet<>(Set.of(empresaVinculadaBack)));
        assertThat(funcionario.getEmpresaVinculadas()).containsOnly(empresaVinculadaBack);
        assertThat(empresaVinculadaBack.getFuncionario()).isEqualTo(funcionario);

        funcionario.setEmpresaVinculadas(new HashSet<>());
        assertThat(funcionario.getEmpresaVinculadas()).doesNotContain(empresaVinculadaBack);
        assertThat(empresaVinculadaBack.getFuncionario()).isNull();
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
    void pessoaTest() {
        Funcionario funcionario = getFuncionarioRandomSampleGenerator();
        Pessoa pessoaBack = getPessoaRandomSampleGenerator();

        funcionario.setPessoa(pessoaBack);
        assertThat(funcionario.getPessoa()).isEqualTo(pessoaBack);

        funcionario.pessoa(null);
        assertThat(funcionario.getPessoa()).isNull();
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

    @Test
    void profissaoTest() {
        Funcionario funcionario = getFuncionarioRandomSampleGenerator();
        Profissao profissaoBack = getProfissaoRandomSampleGenerator();

        funcionario.setProfissao(profissaoBack);
        assertThat(funcionario.getProfissao()).isEqualTo(profissaoBack);

        funcionario.profissao(null);
        assertThat(funcionario.getProfissao()).isNull();
    }
}
