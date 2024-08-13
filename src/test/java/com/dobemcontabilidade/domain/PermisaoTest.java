package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.FuncionalidadeGrupoAcessoEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.FuncionalidadeGrupoAcessoPadraoTestSamples.*;
import static com.dobemcontabilidade.domain.GrupoAcessoEmpresaUsuarioContadorTestSamples.*;
import static com.dobemcontabilidade.domain.PermisaoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PermisaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Permisao.class);
        Permisao permisao1 = getPermisaoSample1();
        Permisao permisao2 = new Permisao();
        assertThat(permisao1).isNotEqualTo(permisao2);

        permisao2.setId(permisao1.getId());
        assertThat(permisao1).isEqualTo(permisao2);

        permisao2 = getPermisaoSample2();
        assertThat(permisao1).isNotEqualTo(permisao2);
    }

    @Test
    void funcionalidadeGrupoAcessoPadraoTest() {
        Permisao permisao = getPermisaoRandomSampleGenerator();
        FuncionalidadeGrupoAcessoPadrao funcionalidadeGrupoAcessoPadraoBack = getFuncionalidadeGrupoAcessoPadraoRandomSampleGenerator();

        permisao.addFuncionalidadeGrupoAcessoPadrao(funcionalidadeGrupoAcessoPadraoBack);
        assertThat(permisao.getFuncionalidadeGrupoAcessoPadraos()).containsOnly(funcionalidadeGrupoAcessoPadraoBack);
        assertThat(funcionalidadeGrupoAcessoPadraoBack.getPermisao()).isEqualTo(permisao);

        permisao.removeFuncionalidadeGrupoAcessoPadrao(funcionalidadeGrupoAcessoPadraoBack);
        assertThat(permisao.getFuncionalidadeGrupoAcessoPadraos()).doesNotContain(funcionalidadeGrupoAcessoPadraoBack);
        assertThat(funcionalidadeGrupoAcessoPadraoBack.getPermisao()).isNull();

        permisao.funcionalidadeGrupoAcessoPadraos(new HashSet<>(Set.of(funcionalidadeGrupoAcessoPadraoBack)));
        assertThat(permisao.getFuncionalidadeGrupoAcessoPadraos()).containsOnly(funcionalidadeGrupoAcessoPadraoBack);
        assertThat(funcionalidadeGrupoAcessoPadraoBack.getPermisao()).isEqualTo(permisao);

        permisao.setFuncionalidadeGrupoAcessoPadraos(new HashSet<>());
        assertThat(permisao.getFuncionalidadeGrupoAcessoPadraos()).doesNotContain(funcionalidadeGrupoAcessoPadraoBack);
        assertThat(funcionalidadeGrupoAcessoPadraoBack.getPermisao()).isNull();
    }

    @Test
    void funcionalidadeGrupoAcessoEmpresaTest() {
        Permisao permisao = getPermisaoRandomSampleGenerator();
        FuncionalidadeGrupoAcessoEmpresa funcionalidadeGrupoAcessoEmpresaBack = getFuncionalidadeGrupoAcessoEmpresaRandomSampleGenerator();

        permisao.addFuncionalidadeGrupoAcessoEmpresa(funcionalidadeGrupoAcessoEmpresaBack);
        assertThat(permisao.getFuncionalidadeGrupoAcessoEmpresas()).containsOnly(funcionalidadeGrupoAcessoEmpresaBack);
        assertThat(funcionalidadeGrupoAcessoEmpresaBack.getPermisao()).isEqualTo(permisao);

        permisao.removeFuncionalidadeGrupoAcessoEmpresa(funcionalidadeGrupoAcessoEmpresaBack);
        assertThat(permisao.getFuncionalidadeGrupoAcessoEmpresas()).doesNotContain(funcionalidadeGrupoAcessoEmpresaBack);
        assertThat(funcionalidadeGrupoAcessoEmpresaBack.getPermisao()).isNull();

        permisao.funcionalidadeGrupoAcessoEmpresas(new HashSet<>(Set.of(funcionalidadeGrupoAcessoEmpresaBack)));
        assertThat(permisao.getFuncionalidadeGrupoAcessoEmpresas()).containsOnly(funcionalidadeGrupoAcessoEmpresaBack);
        assertThat(funcionalidadeGrupoAcessoEmpresaBack.getPermisao()).isEqualTo(permisao);

        permisao.setFuncionalidadeGrupoAcessoEmpresas(new HashSet<>());
        assertThat(permisao.getFuncionalidadeGrupoAcessoEmpresas()).doesNotContain(funcionalidadeGrupoAcessoEmpresaBack);
        assertThat(funcionalidadeGrupoAcessoEmpresaBack.getPermisao()).isNull();
    }

    @Test
    void grupoAcessoEmpresaUsuarioContadorTest() {
        Permisao permisao = getPermisaoRandomSampleGenerator();
        GrupoAcessoEmpresaUsuarioContador grupoAcessoEmpresaUsuarioContadorBack =
            getGrupoAcessoEmpresaUsuarioContadorRandomSampleGenerator();

        permisao.addGrupoAcessoEmpresaUsuarioContador(grupoAcessoEmpresaUsuarioContadorBack);
        assertThat(permisao.getGrupoAcessoEmpresaUsuarioContadors()).containsOnly(grupoAcessoEmpresaUsuarioContadorBack);
        assertThat(grupoAcessoEmpresaUsuarioContadorBack.getPermisao()).isEqualTo(permisao);

        permisao.removeGrupoAcessoEmpresaUsuarioContador(grupoAcessoEmpresaUsuarioContadorBack);
        assertThat(permisao.getGrupoAcessoEmpresaUsuarioContadors()).doesNotContain(grupoAcessoEmpresaUsuarioContadorBack);
        assertThat(grupoAcessoEmpresaUsuarioContadorBack.getPermisao()).isNull();

        permisao.grupoAcessoEmpresaUsuarioContadors(new HashSet<>(Set.of(grupoAcessoEmpresaUsuarioContadorBack)));
        assertThat(permisao.getGrupoAcessoEmpresaUsuarioContadors()).containsOnly(grupoAcessoEmpresaUsuarioContadorBack);
        assertThat(grupoAcessoEmpresaUsuarioContadorBack.getPermisao()).isEqualTo(permisao);

        permisao.setGrupoAcessoEmpresaUsuarioContadors(new HashSet<>());
        assertThat(permisao.getGrupoAcessoEmpresaUsuarioContadors()).doesNotContain(grupoAcessoEmpresaUsuarioContadorBack);
        assertThat(grupoAcessoEmpresaUsuarioContadorBack.getPermisao()).isNull();
    }
}
