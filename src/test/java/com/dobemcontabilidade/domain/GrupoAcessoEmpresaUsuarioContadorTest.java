package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.GrupoAcessoEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.GrupoAcessoEmpresaUsuarioContadorTestSamples.*;
import static com.dobemcontabilidade.domain.PermisaoTestSamples.*;
import static com.dobemcontabilidade.domain.UsuarioContadorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GrupoAcessoEmpresaUsuarioContadorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrupoAcessoEmpresaUsuarioContador.class);
        GrupoAcessoEmpresaUsuarioContador grupoAcessoEmpresaUsuarioContador1 = getGrupoAcessoEmpresaUsuarioContadorSample1();
        GrupoAcessoEmpresaUsuarioContador grupoAcessoEmpresaUsuarioContador2 = new GrupoAcessoEmpresaUsuarioContador();
        assertThat(grupoAcessoEmpresaUsuarioContador1).isNotEqualTo(grupoAcessoEmpresaUsuarioContador2);

        grupoAcessoEmpresaUsuarioContador2.setId(grupoAcessoEmpresaUsuarioContador1.getId());
        assertThat(grupoAcessoEmpresaUsuarioContador1).isEqualTo(grupoAcessoEmpresaUsuarioContador2);

        grupoAcessoEmpresaUsuarioContador2 = getGrupoAcessoEmpresaUsuarioContadorSample2();
        assertThat(grupoAcessoEmpresaUsuarioContador1).isNotEqualTo(grupoAcessoEmpresaUsuarioContador2);
    }

    @Test
    void usuarioContadorTest() {
        GrupoAcessoEmpresaUsuarioContador grupoAcessoEmpresaUsuarioContador = getGrupoAcessoEmpresaUsuarioContadorRandomSampleGenerator();
        UsuarioContador usuarioContadorBack = getUsuarioContadorRandomSampleGenerator();

        grupoAcessoEmpresaUsuarioContador.setUsuarioContador(usuarioContadorBack);
        assertThat(grupoAcessoEmpresaUsuarioContador.getUsuarioContador()).isEqualTo(usuarioContadorBack);

        grupoAcessoEmpresaUsuarioContador.usuarioContador(null);
        assertThat(grupoAcessoEmpresaUsuarioContador.getUsuarioContador()).isNull();
    }

    @Test
    void permisaoTest() {
        GrupoAcessoEmpresaUsuarioContador grupoAcessoEmpresaUsuarioContador = getGrupoAcessoEmpresaUsuarioContadorRandomSampleGenerator();
        Permisao permisaoBack = getPermisaoRandomSampleGenerator();

        grupoAcessoEmpresaUsuarioContador.setPermisao(permisaoBack);
        assertThat(grupoAcessoEmpresaUsuarioContador.getPermisao()).isEqualTo(permisaoBack);

        grupoAcessoEmpresaUsuarioContador.permisao(null);
        assertThat(grupoAcessoEmpresaUsuarioContador.getPermisao()).isNull();
    }

    @Test
    void grupoAcessoEmpresaTest() {
        GrupoAcessoEmpresaUsuarioContador grupoAcessoEmpresaUsuarioContador = getGrupoAcessoEmpresaUsuarioContadorRandomSampleGenerator();
        GrupoAcessoEmpresa grupoAcessoEmpresaBack = getGrupoAcessoEmpresaRandomSampleGenerator();

        grupoAcessoEmpresaUsuarioContador.setGrupoAcessoEmpresa(grupoAcessoEmpresaBack);
        assertThat(grupoAcessoEmpresaUsuarioContador.getGrupoAcessoEmpresa()).isEqualTo(grupoAcessoEmpresaBack);

        grupoAcessoEmpresaUsuarioContador.grupoAcessoEmpresa(null);
        assertThat(grupoAcessoEmpresaUsuarioContador.getGrupoAcessoEmpresa()).isNull();
    }
}
