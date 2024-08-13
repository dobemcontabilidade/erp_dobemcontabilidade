package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.GrupoAcessoPadraoTestSamples.*;
import static com.dobemcontabilidade.domain.GrupoAcessoUsuarioContadorTestSamples.*;
import static com.dobemcontabilidade.domain.UsuarioContadorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GrupoAcessoUsuarioContadorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrupoAcessoUsuarioContador.class);
        GrupoAcessoUsuarioContador grupoAcessoUsuarioContador1 = getGrupoAcessoUsuarioContadorSample1();
        GrupoAcessoUsuarioContador grupoAcessoUsuarioContador2 = new GrupoAcessoUsuarioContador();
        assertThat(grupoAcessoUsuarioContador1).isNotEqualTo(grupoAcessoUsuarioContador2);

        grupoAcessoUsuarioContador2.setId(grupoAcessoUsuarioContador1.getId());
        assertThat(grupoAcessoUsuarioContador1).isEqualTo(grupoAcessoUsuarioContador2);

        grupoAcessoUsuarioContador2 = getGrupoAcessoUsuarioContadorSample2();
        assertThat(grupoAcessoUsuarioContador1).isNotEqualTo(grupoAcessoUsuarioContador2);
    }

    @Test
    void usuarioContadorTest() {
        GrupoAcessoUsuarioContador grupoAcessoUsuarioContador = getGrupoAcessoUsuarioContadorRandomSampleGenerator();
        UsuarioContador usuarioContadorBack = getUsuarioContadorRandomSampleGenerator();

        grupoAcessoUsuarioContador.setUsuarioContador(usuarioContadorBack);
        assertThat(grupoAcessoUsuarioContador.getUsuarioContador()).isEqualTo(usuarioContadorBack);

        grupoAcessoUsuarioContador.usuarioContador(null);
        assertThat(grupoAcessoUsuarioContador.getUsuarioContador()).isNull();
    }

    @Test
    void grupoAcessoPadraoTest() {
        GrupoAcessoUsuarioContador grupoAcessoUsuarioContador = getGrupoAcessoUsuarioContadorRandomSampleGenerator();
        GrupoAcessoPadrao grupoAcessoPadraoBack = getGrupoAcessoPadraoRandomSampleGenerator();

        grupoAcessoUsuarioContador.setGrupoAcessoPadrao(grupoAcessoPadraoBack);
        assertThat(grupoAcessoUsuarioContador.getGrupoAcessoPadrao()).isEqualTo(grupoAcessoPadraoBack);

        grupoAcessoUsuarioContador.grupoAcessoPadrao(null);
        assertThat(grupoAcessoUsuarioContador.getGrupoAcessoPadrao()).isNull();
    }
}
