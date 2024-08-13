package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.ContadorTestSamples.*;
import static com.dobemcontabilidade.domain.PerfilContadorDepartamentoTestSamples.*;
import static com.dobemcontabilidade.domain.PerfilContadorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PerfilContadorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PerfilContador.class);
        PerfilContador perfilContador1 = getPerfilContadorSample1();
        PerfilContador perfilContador2 = new PerfilContador();
        assertThat(perfilContador1).isNotEqualTo(perfilContador2);

        perfilContador2.setId(perfilContador1.getId());
        assertThat(perfilContador1).isEqualTo(perfilContador2);

        perfilContador2 = getPerfilContadorSample2();
        assertThat(perfilContador1).isNotEqualTo(perfilContador2);
    }

    @Test
    void contadorTest() {
        PerfilContador perfilContador = getPerfilContadorRandomSampleGenerator();
        Contador contadorBack = getContadorRandomSampleGenerator();

        perfilContador.addContador(contadorBack);
        assertThat(perfilContador.getContadors()).containsOnly(contadorBack);
        assertThat(contadorBack.getPerfilContador()).isEqualTo(perfilContador);

        perfilContador.removeContador(contadorBack);
        assertThat(perfilContador.getContadors()).doesNotContain(contadorBack);
        assertThat(contadorBack.getPerfilContador()).isNull();

        perfilContador.contadors(new HashSet<>(Set.of(contadorBack)));
        assertThat(perfilContador.getContadors()).containsOnly(contadorBack);
        assertThat(contadorBack.getPerfilContador()).isEqualTo(perfilContador);

        perfilContador.setContadors(new HashSet<>());
        assertThat(perfilContador.getContadors()).doesNotContain(contadorBack);
        assertThat(contadorBack.getPerfilContador()).isNull();
    }

    @Test
    void perfilContadorDepartamentoTest() {
        PerfilContador perfilContador = getPerfilContadorRandomSampleGenerator();
        PerfilContadorDepartamento perfilContadorDepartamentoBack = getPerfilContadorDepartamentoRandomSampleGenerator();

        perfilContador.addPerfilContadorDepartamento(perfilContadorDepartamentoBack);
        assertThat(perfilContador.getPerfilContadorDepartamentos()).containsOnly(perfilContadorDepartamentoBack);
        assertThat(perfilContadorDepartamentoBack.getPerfilContador()).isEqualTo(perfilContador);

        perfilContador.removePerfilContadorDepartamento(perfilContadorDepartamentoBack);
        assertThat(perfilContador.getPerfilContadorDepartamentos()).doesNotContain(perfilContadorDepartamentoBack);
        assertThat(perfilContadorDepartamentoBack.getPerfilContador()).isNull();

        perfilContador.perfilContadorDepartamentos(new HashSet<>(Set.of(perfilContadorDepartamentoBack)));
        assertThat(perfilContador.getPerfilContadorDepartamentos()).containsOnly(perfilContadorDepartamentoBack);
        assertThat(perfilContadorDepartamentoBack.getPerfilContador()).isEqualTo(perfilContador);

        perfilContador.setPerfilContadorDepartamentos(new HashSet<>());
        assertThat(perfilContador.getPerfilContadorDepartamentos()).doesNotContain(perfilContadorDepartamentoBack);
        assertThat(perfilContadorDepartamentoBack.getPerfilContador()).isNull();
    }
}
