package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.DepartamentoTestSamples.*;
import static com.dobemcontabilidade.domain.PerfilContadorDepartamentoTestSamples.*;
import static com.dobemcontabilidade.domain.PerfilContadorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PerfilContadorDepartamentoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PerfilContadorDepartamento.class);
        PerfilContadorDepartamento perfilContadorDepartamento1 = getPerfilContadorDepartamentoSample1();
        PerfilContadorDepartamento perfilContadorDepartamento2 = new PerfilContadorDepartamento();
        assertThat(perfilContadorDepartamento1).isNotEqualTo(perfilContadorDepartamento2);

        perfilContadorDepartamento2.setId(perfilContadorDepartamento1.getId());
        assertThat(perfilContadorDepartamento1).isEqualTo(perfilContadorDepartamento2);

        perfilContadorDepartamento2 = getPerfilContadorDepartamentoSample2();
        assertThat(perfilContadorDepartamento1).isNotEqualTo(perfilContadorDepartamento2);
    }

    @Test
    void departamentoTest() {
        PerfilContadorDepartamento perfilContadorDepartamento = getPerfilContadorDepartamentoRandomSampleGenerator();
        Departamento departamentoBack = getDepartamentoRandomSampleGenerator();

        perfilContadorDepartamento.setDepartamento(departamentoBack);
        assertThat(perfilContadorDepartamento.getDepartamento()).isEqualTo(departamentoBack);

        perfilContadorDepartamento.departamento(null);
        assertThat(perfilContadorDepartamento.getDepartamento()).isNull();
    }

    @Test
    void perfilContadorTest() {
        PerfilContadorDepartamento perfilContadorDepartamento = getPerfilContadorDepartamentoRandomSampleGenerator();
        PerfilContador perfilContadorBack = getPerfilContadorRandomSampleGenerator();

        perfilContadorDepartamento.setPerfilContador(perfilContadorBack);
        assertThat(perfilContadorDepartamento.getPerfilContador()).isEqualTo(perfilContadorBack);

        perfilContadorDepartamento.perfilContador(null);
        assertThat(perfilContadorDepartamento.getPerfilContador()).isNull();
    }
}
