package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.ContadorTestSamples.*;
import static com.dobemcontabilidade.domain.DepartamentoEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.DepartamentoTestSamples.*;
import static com.dobemcontabilidade.domain.EmpresaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DepartamentoEmpresaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DepartamentoEmpresa.class);
        DepartamentoEmpresa departamentoEmpresa1 = getDepartamentoEmpresaSample1();
        DepartamentoEmpresa departamentoEmpresa2 = new DepartamentoEmpresa();
        assertThat(departamentoEmpresa1).isNotEqualTo(departamentoEmpresa2);

        departamentoEmpresa2.setId(departamentoEmpresa1.getId());
        assertThat(departamentoEmpresa1).isEqualTo(departamentoEmpresa2);

        departamentoEmpresa2 = getDepartamentoEmpresaSample2();
        assertThat(departamentoEmpresa1).isNotEqualTo(departamentoEmpresa2);
    }

    @Test
    void departamentoTest() {
        DepartamentoEmpresa departamentoEmpresa = getDepartamentoEmpresaRandomSampleGenerator();
        Departamento departamentoBack = getDepartamentoRandomSampleGenerator();

        departamentoEmpresa.setDepartamento(departamentoBack);
        assertThat(departamentoEmpresa.getDepartamento()).isEqualTo(departamentoBack);

        departamentoEmpresa.departamento(null);
        assertThat(departamentoEmpresa.getDepartamento()).isNull();
    }

    @Test
    void empresaTest() {
        DepartamentoEmpresa departamentoEmpresa = getDepartamentoEmpresaRandomSampleGenerator();
        Empresa empresaBack = getEmpresaRandomSampleGenerator();

        departamentoEmpresa.setEmpresa(empresaBack);
        assertThat(departamentoEmpresa.getEmpresa()).isEqualTo(empresaBack);

        departamentoEmpresa.empresa(null);
        assertThat(departamentoEmpresa.getEmpresa()).isNull();
    }

    @Test
    void contadorTest() {
        DepartamentoEmpresa departamentoEmpresa = getDepartamentoEmpresaRandomSampleGenerator();
        Contador contadorBack = getContadorRandomSampleGenerator();

        departamentoEmpresa.setContador(contadorBack);
        assertThat(departamentoEmpresa.getContador()).isEqualTo(contadorBack);

        departamentoEmpresa.contador(null);
        assertThat(departamentoEmpresa.getContador()).isNull();
    }
}
