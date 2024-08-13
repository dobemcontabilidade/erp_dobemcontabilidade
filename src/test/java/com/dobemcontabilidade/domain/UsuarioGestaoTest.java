package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AdministradorTestSamples.*;
import static com.dobemcontabilidade.domain.UsuarioGestaoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UsuarioGestaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UsuarioGestao.class);
        UsuarioGestao usuarioGestao1 = getUsuarioGestaoSample1();
        UsuarioGestao usuarioGestao2 = new UsuarioGestao();
        assertThat(usuarioGestao1).isNotEqualTo(usuarioGestao2);

        usuarioGestao2.setId(usuarioGestao1.getId());
        assertThat(usuarioGestao1).isEqualTo(usuarioGestao2);

        usuarioGestao2 = getUsuarioGestaoSample2();
        assertThat(usuarioGestao1).isNotEqualTo(usuarioGestao2);
    }

    @Test
    void administradorTest() {
        UsuarioGestao usuarioGestao = getUsuarioGestaoRandomSampleGenerator();
        Administrador administradorBack = getAdministradorRandomSampleGenerator();

        usuarioGestao.setAdministrador(administradorBack);
        assertThat(usuarioGestao.getAdministrador()).isEqualTo(administradorBack);

        usuarioGestao.administrador(null);
        assertThat(usuarioGestao.getAdministrador()).isNull();
    }
}
