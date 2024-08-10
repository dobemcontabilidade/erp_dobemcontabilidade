package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AdministradorTestSamples.*;
import static com.dobemcontabilidade.domain.ContadorTestSamples.*;
import static com.dobemcontabilidade.domain.UsuarioContadorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UsuarioContadorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UsuarioContador.class);
        UsuarioContador usuarioContador1 = getUsuarioContadorSample1();
        UsuarioContador usuarioContador2 = new UsuarioContador();
        assertThat(usuarioContador1).isNotEqualTo(usuarioContador2);

        usuarioContador2.setId(usuarioContador1.getId());
        assertThat(usuarioContador1).isEqualTo(usuarioContador2);

        usuarioContador2 = getUsuarioContadorSample2();
        assertThat(usuarioContador1).isNotEqualTo(usuarioContador2);
    }

    @Test
    void contadorTest() {
        UsuarioContador usuarioContador = getUsuarioContadorRandomSampleGenerator();
        Contador contadorBack = getContadorRandomSampleGenerator();

        usuarioContador.setContador(contadorBack);
        assertThat(usuarioContador.getContador()).isEqualTo(contadorBack);

        usuarioContador.contador(null);
        assertThat(usuarioContador.getContador()).isNull();
    }

    @Test
    void administradorTest() {
        UsuarioContador usuarioContador = getUsuarioContadorRandomSampleGenerator();
        Administrador administradorBack = getAdministradorRandomSampleGenerator();

        usuarioContador.setAdministrador(administradorBack);
        assertThat(usuarioContador.getAdministrador()).isEqualTo(administradorBack);

        usuarioContador.administrador(null);
        assertThat(usuarioContador.getAdministrador()).isNull();
    }
}
