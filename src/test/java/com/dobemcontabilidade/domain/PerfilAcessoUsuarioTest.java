package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.PerfilAcessoUsuarioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PerfilAcessoUsuarioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PerfilAcessoUsuario.class);
        PerfilAcessoUsuario perfilAcessoUsuario1 = getPerfilAcessoUsuarioSample1();
        PerfilAcessoUsuario perfilAcessoUsuario2 = new PerfilAcessoUsuario();
        assertThat(perfilAcessoUsuario1).isNotEqualTo(perfilAcessoUsuario2);

        perfilAcessoUsuario2.setId(perfilAcessoUsuario1.getId());
        assertThat(perfilAcessoUsuario1).isEqualTo(perfilAcessoUsuario2);

        perfilAcessoUsuario2 = getPerfilAcessoUsuarioSample2();
        assertThat(perfilAcessoUsuario1).isNotEqualTo(perfilAcessoUsuario2);
    }
}
