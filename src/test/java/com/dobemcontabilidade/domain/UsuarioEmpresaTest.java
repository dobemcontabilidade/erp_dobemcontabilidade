package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AssinaturaEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.FeedBackContadorParaUsuarioTestSamples.*;
import static com.dobemcontabilidade.domain.FeedBackUsuarioParaContadorTestSamples.*;
import static com.dobemcontabilidade.domain.FuncionarioTestSamples.*;
import static com.dobemcontabilidade.domain.GrupoAcessoUsuarioEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.SocioTestSamples.*;
import static com.dobemcontabilidade.domain.UsuarioEmpresaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class UsuarioEmpresaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UsuarioEmpresa.class);
        UsuarioEmpresa usuarioEmpresa1 = getUsuarioEmpresaSample1();
        UsuarioEmpresa usuarioEmpresa2 = new UsuarioEmpresa();
        assertThat(usuarioEmpresa1).isNotEqualTo(usuarioEmpresa2);

        usuarioEmpresa2.setId(usuarioEmpresa1.getId());
        assertThat(usuarioEmpresa1).isEqualTo(usuarioEmpresa2);

        usuarioEmpresa2 = getUsuarioEmpresaSample2();
        assertThat(usuarioEmpresa1).isNotEqualTo(usuarioEmpresa2);
    }

    @Test
    void grupoAcessoUsuarioEmpresaTest() {
        UsuarioEmpresa usuarioEmpresa = getUsuarioEmpresaRandomSampleGenerator();
        GrupoAcessoUsuarioEmpresa grupoAcessoUsuarioEmpresaBack = getGrupoAcessoUsuarioEmpresaRandomSampleGenerator();

        usuarioEmpresa.addGrupoAcessoUsuarioEmpresa(grupoAcessoUsuarioEmpresaBack);
        assertThat(usuarioEmpresa.getGrupoAcessoUsuarioEmpresas()).containsOnly(grupoAcessoUsuarioEmpresaBack);
        assertThat(grupoAcessoUsuarioEmpresaBack.getUsuarioEmpresa()).isEqualTo(usuarioEmpresa);

        usuarioEmpresa.removeGrupoAcessoUsuarioEmpresa(grupoAcessoUsuarioEmpresaBack);
        assertThat(usuarioEmpresa.getGrupoAcessoUsuarioEmpresas()).doesNotContain(grupoAcessoUsuarioEmpresaBack);
        assertThat(grupoAcessoUsuarioEmpresaBack.getUsuarioEmpresa()).isNull();

        usuarioEmpresa.grupoAcessoUsuarioEmpresas(new HashSet<>(Set.of(grupoAcessoUsuarioEmpresaBack)));
        assertThat(usuarioEmpresa.getGrupoAcessoUsuarioEmpresas()).containsOnly(grupoAcessoUsuarioEmpresaBack);
        assertThat(grupoAcessoUsuarioEmpresaBack.getUsuarioEmpresa()).isEqualTo(usuarioEmpresa);

        usuarioEmpresa.setGrupoAcessoUsuarioEmpresas(new HashSet<>());
        assertThat(usuarioEmpresa.getGrupoAcessoUsuarioEmpresas()).doesNotContain(grupoAcessoUsuarioEmpresaBack);
        assertThat(grupoAcessoUsuarioEmpresaBack.getUsuarioEmpresa()).isNull();
    }

    @Test
    void feedBackUsuarioParaContadorTest() {
        UsuarioEmpresa usuarioEmpresa = getUsuarioEmpresaRandomSampleGenerator();
        FeedBackUsuarioParaContador feedBackUsuarioParaContadorBack = getFeedBackUsuarioParaContadorRandomSampleGenerator();

        usuarioEmpresa.addFeedBackUsuarioParaContador(feedBackUsuarioParaContadorBack);
        assertThat(usuarioEmpresa.getFeedBackUsuarioParaContadors()).containsOnly(feedBackUsuarioParaContadorBack);
        assertThat(feedBackUsuarioParaContadorBack.getUsuarioEmpresa()).isEqualTo(usuarioEmpresa);

        usuarioEmpresa.removeFeedBackUsuarioParaContador(feedBackUsuarioParaContadorBack);
        assertThat(usuarioEmpresa.getFeedBackUsuarioParaContadors()).doesNotContain(feedBackUsuarioParaContadorBack);
        assertThat(feedBackUsuarioParaContadorBack.getUsuarioEmpresa()).isNull();

        usuarioEmpresa.feedBackUsuarioParaContadors(new HashSet<>(Set.of(feedBackUsuarioParaContadorBack)));
        assertThat(usuarioEmpresa.getFeedBackUsuarioParaContadors()).containsOnly(feedBackUsuarioParaContadorBack);
        assertThat(feedBackUsuarioParaContadorBack.getUsuarioEmpresa()).isEqualTo(usuarioEmpresa);

        usuarioEmpresa.setFeedBackUsuarioParaContadors(new HashSet<>());
        assertThat(usuarioEmpresa.getFeedBackUsuarioParaContadors()).doesNotContain(feedBackUsuarioParaContadorBack);
        assertThat(feedBackUsuarioParaContadorBack.getUsuarioEmpresa()).isNull();
    }

    @Test
    void feedBackContadorParaUsuarioTest() {
        UsuarioEmpresa usuarioEmpresa = getUsuarioEmpresaRandomSampleGenerator();
        FeedBackContadorParaUsuario feedBackContadorParaUsuarioBack = getFeedBackContadorParaUsuarioRandomSampleGenerator();

        usuarioEmpresa.addFeedBackContadorParaUsuario(feedBackContadorParaUsuarioBack);
        assertThat(usuarioEmpresa.getFeedBackContadorParaUsuarios()).containsOnly(feedBackContadorParaUsuarioBack);
        assertThat(feedBackContadorParaUsuarioBack.getUsuarioEmpresa()).isEqualTo(usuarioEmpresa);

        usuarioEmpresa.removeFeedBackContadorParaUsuario(feedBackContadorParaUsuarioBack);
        assertThat(usuarioEmpresa.getFeedBackContadorParaUsuarios()).doesNotContain(feedBackContadorParaUsuarioBack);
        assertThat(feedBackContadorParaUsuarioBack.getUsuarioEmpresa()).isNull();

        usuarioEmpresa.feedBackContadorParaUsuarios(new HashSet<>(Set.of(feedBackContadorParaUsuarioBack)));
        assertThat(usuarioEmpresa.getFeedBackContadorParaUsuarios()).containsOnly(feedBackContadorParaUsuarioBack);
        assertThat(feedBackContadorParaUsuarioBack.getUsuarioEmpresa()).isEqualTo(usuarioEmpresa);

        usuarioEmpresa.setFeedBackContadorParaUsuarios(new HashSet<>());
        assertThat(usuarioEmpresa.getFeedBackContadorParaUsuarios()).doesNotContain(feedBackContadorParaUsuarioBack);
        assertThat(feedBackContadorParaUsuarioBack.getUsuarioEmpresa()).isNull();
    }

    @Test
    void assinaturaEmpresaTest() {
        UsuarioEmpresa usuarioEmpresa = getUsuarioEmpresaRandomSampleGenerator();
        AssinaturaEmpresa assinaturaEmpresaBack = getAssinaturaEmpresaRandomSampleGenerator();

        usuarioEmpresa.setAssinaturaEmpresa(assinaturaEmpresaBack);
        assertThat(usuarioEmpresa.getAssinaturaEmpresa()).isEqualTo(assinaturaEmpresaBack);

        usuarioEmpresa.assinaturaEmpresa(null);
        assertThat(usuarioEmpresa.getAssinaturaEmpresa()).isNull();
    }

    @Test
    void funcionarioTest() {
        UsuarioEmpresa usuarioEmpresa = getUsuarioEmpresaRandomSampleGenerator();
        Funcionario funcionarioBack = getFuncionarioRandomSampleGenerator();

        usuarioEmpresa.setFuncionario(funcionarioBack);
        assertThat(usuarioEmpresa.getFuncionario()).isEqualTo(funcionarioBack);
        assertThat(funcionarioBack.getUsuarioEmpresa()).isEqualTo(usuarioEmpresa);

        usuarioEmpresa.funcionario(null);
        assertThat(usuarioEmpresa.getFuncionario()).isNull();
        assertThat(funcionarioBack.getUsuarioEmpresa()).isNull();
    }

    @Test
    void socioTest() {
        UsuarioEmpresa usuarioEmpresa = getUsuarioEmpresaRandomSampleGenerator();
        Socio socioBack = getSocioRandomSampleGenerator();

        usuarioEmpresa.setSocio(socioBack);
        assertThat(usuarioEmpresa.getSocio()).isEqualTo(socioBack);
        assertThat(socioBack.getUsuarioEmpresa()).isEqualTo(usuarioEmpresa);

        usuarioEmpresa.socio(null);
        assertThat(usuarioEmpresa.getSocio()).isNull();
        assertThat(socioBack.getUsuarioEmpresa()).isNull();
    }
}
