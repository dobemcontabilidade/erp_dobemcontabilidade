package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.GrupoAcessoEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.GrupoAcessoUsuarioEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.UsuarioEmpresaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GrupoAcessoUsuarioEmpresaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrupoAcessoUsuarioEmpresa.class);
        GrupoAcessoUsuarioEmpresa grupoAcessoUsuarioEmpresa1 = getGrupoAcessoUsuarioEmpresaSample1();
        GrupoAcessoUsuarioEmpresa grupoAcessoUsuarioEmpresa2 = new GrupoAcessoUsuarioEmpresa();
        assertThat(grupoAcessoUsuarioEmpresa1).isNotEqualTo(grupoAcessoUsuarioEmpresa2);

        grupoAcessoUsuarioEmpresa2.setId(grupoAcessoUsuarioEmpresa1.getId());
        assertThat(grupoAcessoUsuarioEmpresa1).isEqualTo(grupoAcessoUsuarioEmpresa2);

        grupoAcessoUsuarioEmpresa2 = getGrupoAcessoUsuarioEmpresaSample2();
        assertThat(grupoAcessoUsuarioEmpresa1).isNotEqualTo(grupoAcessoUsuarioEmpresa2);
    }

    @Test
    void grupoAcessoEmpresaTest() {
        GrupoAcessoUsuarioEmpresa grupoAcessoUsuarioEmpresa = getGrupoAcessoUsuarioEmpresaRandomSampleGenerator();
        GrupoAcessoEmpresa grupoAcessoEmpresaBack = getGrupoAcessoEmpresaRandomSampleGenerator();

        grupoAcessoUsuarioEmpresa.setGrupoAcessoEmpresa(grupoAcessoEmpresaBack);
        assertThat(grupoAcessoUsuarioEmpresa.getGrupoAcessoEmpresa()).isEqualTo(grupoAcessoEmpresaBack);

        grupoAcessoUsuarioEmpresa.grupoAcessoEmpresa(null);
        assertThat(grupoAcessoUsuarioEmpresa.getGrupoAcessoEmpresa()).isNull();
    }

    @Test
    void usuarioEmpresaTest() {
        GrupoAcessoUsuarioEmpresa grupoAcessoUsuarioEmpresa = getGrupoAcessoUsuarioEmpresaRandomSampleGenerator();
        UsuarioEmpresa usuarioEmpresaBack = getUsuarioEmpresaRandomSampleGenerator();

        grupoAcessoUsuarioEmpresa.setUsuarioEmpresa(usuarioEmpresaBack);
        assertThat(grupoAcessoUsuarioEmpresa.getUsuarioEmpresa()).isEqualTo(usuarioEmpresaBack);

        grupoAcessoUsuarioEmpresa.usuarioEmpresa(null);
        assertThat(grupoAcessoUsuarioEmpresa.getUsuarioEmpresa()).isNull();
    }
}
