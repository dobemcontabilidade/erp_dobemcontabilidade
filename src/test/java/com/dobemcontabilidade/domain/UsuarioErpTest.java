package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AdministradorTestSamples.*;
import static com.dobemcontabilidade.domain.UsuarioErpTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UsuarioErpTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UsuarioErp.class);
        UsuarioErp usuarioErp1 = getUsuarioErpSample1();
        UsuarioErp usuarioErp2 = new UsuarioErp();
        assertThat(usuarioErp1).isNotEqualTo(usuarioErp2);

        usuarioErp2.setId(usuarioErp1.getId());
        assertThat(usuarioErp1).isEqualTo(usuarioErp2);

        usuarioErp2 = getUsuarioErpSample2();
        assertThat(usuarioErp1).isNotEqualTo(usuarioErp2);
    }

    @Test
    void administradorTest() {
        UsuarioErp usuarioErp = getUsuarioErpRandomSampleGenerator();
        Administrador administradorBack = getAdministradorRandomSampleGenerator();

        usuarioErp.setAdministrador(administradorBack);
        assertThat(usuarioErp.getAdministrador()).isEqualTo(administradorBack);

        usuarioErp.administrador(null);
        assertThat(usuarioErp.getAdministrador()).isNull();
    }
}
