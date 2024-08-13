package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.DepartamentoContadorTestSamples.*;
import static com.dobemcontabilidade.domain.DepartamentoEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.DepartamentoFuncionarioTestSamples.*;
import static com.dobemcontabilidade.domain.DepartamentoTestSamples.*;
import static com.dobemcontabilidade.domain.PerfilContadorDepartamentoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DepartamentoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Departamento.class);
        Departamento departamento1 = getDepartamentoSample1();
        Departamento departamento2 = new Departamento();
        assertThat(departamento1).isNotEqualTo(departamento2);

        departamento2.setId(departamento1.getId());
        assertThat(departamento1).isEqualTo(departamento2);

        departamento2 = getDepartamentoSample2();
        assertThat(departamento1).isNotEqualTo(departamento2);
    }

    @Test
    void departamentoEmpresaTest() {
        Departamento departamento = getDepartamentoRandomSampleGenerator();
        DepartamentoEmpresa departamentoEmpresaBack = getDepartamentoEmpresaRandomSampleGenerator();

        departamento.addDepartamentoEmpresa(departamentoEmpresaBack);
        assertThat(departamento.getDepartamentoEmpresas()).containsOnly(departamentoEmpresaBack);
        assertThat(departamentoEmpresaBack.getDepartamento()).isEqualTo(departamento);

        departamento.removeDepartamentoEmpresa(departamentoEmpresaBack);
        assertThat(departamento.getDepartamentoEmpresas()).doesNotContain(departamentoEmpresaBack);
        assertThat(departamentoEmpresaBack.getDepartamento()).isNull();

        departamento.departamentoEmpresas(new HashSet<>(Set.of(departamentoEmpresaBack)));
        assertThat(departamento.getDepartamentoEmpresas()).containsOnly(departamentoEmpresaBack);
        assertThat(departamentoEmpresaBack.getDepartamento()).isEqualTo(departamento);

        departamento.setDepartamentoEmpresas(new HashSet<>());
        assertThat(departamento.getDepartamentoEmpresas()).doesNotContain(departamentoEmpresaBack);
        assertThat(departamentoEmpresaBack.getDepartamento()).isNull();
    }

    @Test
    void perfilContadorDepartamentoTest() {
        Departamento departamento = getDepartamentoRandomSampleGenerator();
        PerfilContadorDepartamento perfilContadorDepartamentoBack = getPerfilContadorDepartamentoRandomSampleGenerator();

        departamento.addPerfilContadorDepartamento(perfilContadorDepartamentoBack);
        assertThat(departamento.getPerfilContadorDepartamentos()).containsOnly(perfilContadorDepartamentoBack);
        assertThat(perfilContadorDepartamentoBack.getDepartamento()).isEqualTo(departamento);

        departamento.removePerfilContadorDepartamento(perfilContadorDepartamentoBack);
        assertThat(departamento.getPerfilContadorDepartamentos()).doesNotContain(perfilContadorDepartamentoBack);
        assertThat(perfilContadorDepartamentoBack.getDepartamento()).isNull();

        departamento.perfilContadorDepartamentos(new HashSet<>(Set.of(perfilContadorDepartamentoBack)));
        assertThat(departamento.getPerfilContadorDepartamentos()).containsOnly(perfilContadorDepartamentoBack);
        assertThat(perfilContadorDepartamentoBack.getDepartamento()).isEqualTo(departamento);

        departamento.setPerfilContadorDepartamentos(new HashSet<>());
        assertThat(departamento.getPerfilContadorDepartamentos()).doesNotContain(perfilContadorDepartamentoBack);
        assertThat(perfilContadorDepartamentoBack.getDepartamento()).isNull();
    }

    @Test
    void departamentoContadorTest() {
        Departamento departamento = getDepartamentoRandomSampleGenerator();
        DepartamentoContador departamentoContadorBack = getDepartamentoContadorRandomSampleGenerator();

        departamento.addDepartamentoContador(departamentoContadorBack);
        assertThat(departamento.getDepartamentoContadors()).containsOnly(departamentoContadorBack);
        assertThat(departamentoContadorBack.getDepartamento()).isEqualTo(departamento);

        departamento.removeDepartamentoContador(departamentoContadorBack);
        assertThat(departamento.getDepartamentoContadors()).doesNotContain(departamentoContadorBack);
        assertThat(departamentoContadorBack.getDepartamento()).isNull();

        departamento.departamentoContadors(new HashSet<>(Set.of(departamentoContadorBack)));
        assertThat(departamento.getDepartamentoContadors()).containsOnly(departamentoContadorBack);
        assertThat(departamentoContadorBack.getDepartamento()).isEqualTo(departamento);

        departamento.setDepartamentoContadors(new HashSet<>());
        assertThat(departamento.getDepartamentoContadors()).doesNotContain(departamentoContadorBack);
        assertThat(departamentoContadorBack.getDepartamento()).isNull();
    }

    @Test
    void departamentoFuncionarioTest() {
        Departamento departamento = getDepartamentoRandomSampleGenerator();
        DepartamentoFuncionario departamentoFuncionarioBack = getDepartamentoFuncionarioRandomSampleGenerator();

        departamento.addDepartamentoFuncionario(departamentoFuncionarioBack);
        assertThat(departamento.getDepartamentoFuncionarios()).containsOnly(departamentoFuncionarioBack);
        assertThat(departamentoFuncionarioBack.getDepartamento()).isEqualTo(departamento);

        departamento.removeDepartamentoFuncionario(departamentoFuncionarioBack);
        assertThat(departamento.getDepartamentoFuncionarios()).doesNotContain(departamentoFuncionarioBack);
        assertThat(departamentoFuncionarioBack.getDepartamento()).isNull();

        departamento.departamentoFuncionarios(new HashSet<>(Set.of(departamentoFuncionarioBack)));
        assertThat(departamento.getDepartamentoFuncionarios()).containsOnly(departamentoFuncionarioBack);
        assertThat(departamentoFuncionarioBack.getDepartamento()).isEqualTo(departamento);

        departamento.setDepartamentoFuncionarios(new HashSet<>());
        assertThat(departamento.getDepartamentoFuncionarios()).doesNotContain(departamentoFuncionarioBack);
        assertThat(departamentoFuncionarioBack.getDepartamento()).isNull();
    }
}
