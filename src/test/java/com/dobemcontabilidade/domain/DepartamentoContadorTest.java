package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.ContadorTestSamples.*;
import static com.dobemcontabilidade.domain.DepartamentoContadorTestSamples.*;
import static com.dobemcontabilidade.domain.DepartamentoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DepartamentoContadorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DepartamentoContador.class);
        DepartamentoContador departamentoContador1 = getDepartamentoContadorSample1();
        DepartamentoContador departamentoContador2 = new DepartamentoContador();
        assertThat(departamentoContador1).isNotEqualTo(departamentoContador2);

        departamentoContador2.setId(departamentoContador1.getId());
        assertThat(departamentoContador1).isEqualTo(departamentoContador2);

        departamentoContador2 = getDepartamentoContadorSample2();
        assertThat(departamentoContador1).isNotEqualTo(departamentoContador2);
    }

    @Test
    void departamentoTest() {
        DepartamentoContador departamentoContador = getDepartamentoContadorRandomSampleGenerator();
        Departamento departamentoBack = getDepartamentoRandomSampleGenerator();

        departamentoContador.setDepartamento(departamentoBack);
        assertThat(departamentoContador.getDepartamento()).isEqualTo(departamentoBack);

        departamentoContador.departamento(null);
        assertThat(departamentoContador.getDepartamento()).isNull();
    }

    @Test
    void contadorTest() {
        DepartamentoContador departamentoContador = getDepartamentoContadorRandomSampleGenerator();
        Contador contadorBack = getContadorRandomSampleGenerator();

        departamentoContador.setContador(contadorBack);
        assertThat(departamentoContador.getContador()).isEqualTo(contadorBack);

        departamentoContador.contador(null);
        assertThat(departamentoContador.getContador()).isNull();
    }
}
