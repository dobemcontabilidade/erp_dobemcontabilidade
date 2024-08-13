package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.ContadorTestSamples.*;
import static com.dobemcontabilidade.domain.FeedBackUsuarioParaContadorTestSamples.*;
import static com.dobemcontabilidade.domain.GrupoAcessoEmpresaUsuarioContadorTestSamples.*;
import static com.dobemcontabilidade.domain.GrupoAcessoUsuarioContadorTestSamples.*;
import static com.dobemcontabilidade.domain.UsuarioContadorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
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
    void grupoAcessoEmpresaUsuarioContadorTest() {
        UsuarioContador usuarioContador = getUsuarioContadorRandomSampleGenerator();
        GrupoAcessoEmpresaUsuarioContador grupoAcessoEmpresaUsuarioContadorBack =
            getGrupoAcessoEmpresaUsuarioContadorRandomSampleGenerator();

        usuarioContador.addGrupoAcessoEmpresaUsuarioContador(grupoAcessoEmpresaUsuarioContadorBack);
        assertThat(usuarioContador.getGrupoAcessoEmpresaUsuarioContadors()).containsOnly(grupoAcessoEmpresaUsuarioContadorBack);
        assertThat(grupoAcessoEmpresaUsuarioContadorBack.getUsuarioContador()).isEqualTo(usuarioContador);

        usuarioContador.removeGrupoAcessoEmpresaUsuarioContador(grupoAcessoEmpresaUsuarioContadorBack);
        assertThat(usuarioContador.getGrupoAcessoEmpresaUsuarioContadors()).doesNotContain(grupoAcessoEmpresaUsuarioContadorBack);
        assertThat(grupoAcessoEmpresaUsuarioContadorBack.getUsuarioContador()).isNull();

        usuarioContador.grupoAcessoEmpresaUsuarioContadors(new HashSet<>(Set.of(grupoAcessoEmpresaUsuarioContadorBack)));
        assertThat(usuarioContador.getGrupoAcessoEmpresaUsuarioContadors()).containsOnly(grupoAcessoEmpresaUsuarioContadorBack);
        assertThat(grupoAcessoEmpresaUsuarioContadorBack.getUsuarioContador()).isEqualTo(usuarioContador);

        usuarioContador.setGrupoAcessoEmpresaUsuarioContadors(new HashSet<>());
        assertThat(usuarioContador.getGrupoAcessoEmpresaUsuarioContadors()).doesNotContain(grupoAcessoEmpresaUsuarioContadorBack);
        assertThat(grupoAcessoEmpresaUsuarioContadorBack.getUsuarioContador()).isNull();
    }

    @Test
    void grupoAcessoUsuarioContadorTest() {
        UsuarioContador usuarioContador = getUsuarioContadorRandomSampleGenerator();
        GrupoAcessoUsuarioContador grupoAcessoUsuarioContadorBack = getGrupoAcessoUsuarioContadorRandomSampleGenerator();

        usuarioContador.addGrupoAcessoUsuarioContador(grupoAcessoUsuarioContadorBack);
        assertThat(usuarioContador.getGrupoAcessoUsuarioContadors()).containsOnly(grupoAcessoUsuarioContadorBack);
        assertThat(grupoAcessoUsuarioContadorBack.getUsuarioContador()).isEqualTo(usuarioContador);

        usuarioContador.removeGrupoAcessoUsuarioContador(grupoAcessoUsuarioContadorBack);
        assertThat(usuarioContador.getGrupoAcessoUsuarioContadors()).doesNotContain(grupoAcessoUsuarioContadorBack);
        assertThat(grupoAcessoUsuarioContadorBack.getUsuarioContador()).isNull();

        usuarioContador.grupoAcessoUsuarioContadors(new HashSet<>(Set.of(grupoAcessoUsuarioContadorBack)));
        assertThat(usuarioContador.getGrupoAcessoUsuarioContadors()).containsOnly(grupoAcessoUsuarioContadorBack);
        assertThat(grupoAcessoUsuarioContadorBack.getUsuarioContador()).isEqualTo(usuarioContador);

        usuarioContador.setGrupoAcessoUsuarioContadors(new HashSet<>());
        assertThat(usuarioContador.getGrupoAcessoUsuarioContadors()).doesNotContain(grupoAcessoUsuarioContadorBack);
        assertThat(grupoAcessoUsuarioContadorBack.getUsuarioContador()).isNull();
    }

    @Test
    void feedBackUsuarioParaContadorTest() {
        UsuarioContador usuarioContador = getUsuarioContadorRandomSampleGenerator();
        FeedBackUsuarioParaContador feedBackUsuarioParaContadorBack = getFeedBackUsuarioParaContadorRandomSampleGenerator();

        usuarioContador.addFeedBackUsuarioParaContador(feedBackUsuarioParaContadorBack);
        assertThat(usuarioContador.getFeedBackUsuarioParaContadors()).containsOnly(feedBackUsuarioParaContadorBack);
        assertThat(feedBackUsuarioParaContadorBack.getUsuarioContador()).isEqualTo(usuarioContador);

        usuarioContador.removeFeedBackUsuarioParaContador(feedBackUsuarioParaContadorBack);
        assertThat(usuarioContador.getFeedBackUsuarioParaContadors()).doesNotContain(feedBackUsuarioParaContadorBack);
        assertThat(feedBackUsuarioParaContadorBack.getUsuarioContador()).isNull();

        usuarioContador.feedBackUsuarioParaContadors(new HashSet<>(Set.of(feedBackUsuarioParaContadorBack)));
        assertThat(usuarioContador.getFeedBackUsuarioParaContadors()).containsOnly(feedBackUsuarioParaContadorBack);
        assertThat(feedBackUsuarioParaContadorBack.getUsuarioContador()).isEqualTo(usuarioContador);

        usuarioContador.setFeedBackUsuarioParaContadors(new HashSet<>());
        assertThat(usuarioContador.getFeedBackUsuarioParaContadors()).doesNotContain(feedBackUsuarioParaContadorBack);
        assertThat(feedBackUsuarioParaContadorBack.getUsuarioContador()).isNull();
    }

    @Test
    void contadorTest() {
        UsuarioContador usuarioContador = getUsuarioContadorRandomSampleGenerator();
        Contador contadorBack = getContadorRandomSampleGenerator();

        usuarioContador.setContador(contadorBack);
        assertThat(usuarioContador.getContador()).isEqualTo(contadorBack);
        assertThat(contadorBack.getUsuarioContador()).isEqualTo(usuarioContador);

        usuarioContador.contador(null);
        assertThat(usuarioContador.getContador()).isNull();
        assertThat(contadorBack.getUsuarioContador()).isNull();
    }
}
