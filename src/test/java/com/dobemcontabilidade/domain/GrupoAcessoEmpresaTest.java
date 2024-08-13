package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AssinaturaEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.FuncionalidadeGrupoAcessoEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.GrupoAcessoEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.GrupoAcessoEmpresaUsuarioContadorTestSamples.*;
import static com.dobemcontabilidade.domain.GrupoAcessoUsuarioEmpresaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class GrupoAcessoEmpresaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrupoAcessoEmpresa.class);
        GrupoAcessoEmpresa grupoAcessoEmpresa1 = getGrupoAcessoEmpresaSample1();
        GrupoAcessoEmpresa grupoAcessoEmpresa2 = new GrupoAcessoEmpresa();
        assertThat(grupoAcessoEmpresa1).isNotEqualTo(grupoAcessoEmpresa2);

        grupoAcessoEmpresa2.setId(grupoAcessoEmpresa1.getId());
        assertThat(grupoAcessoEmpresa1).isEqualTo(grupoAcessoEmpresa2);

        grupoAcessoEmpresa2 = getGrupoAcessoEmpresaSample2();
        assertThat(grupoAcessoEmpresa1).isNotEqualTo(grupoAcessoEmpresa2);
    }

    @Test
    void funcionalidadeGrupoAcessoEmpresaTest() {
        GrupoAcessoEmpresa grupoAcessoEmpresa = getGrupoAcessoEmpresaRandomSampleGenerator();
        FuncionalidadeGrupoAcessoEmpresa funcionalidadeGrupoAcessoEmpresaBack = getFuncionalidadeGrupoAcessoEmpresaRandomSampleGenerator();

        grupoAcessoEmpresa.addFuncionalidadeGrupoAcessoEmpresa(funcionalidadeGrupoAcessoEmpresaBack);
        assertThat(grupoAcessoEmpresa.getFuncionalidadeGrupoAcessoEmpresas()).containsOnly(funcionalidadeGrupoAcessoEmpresaBack);
        assertThat(funcionalidadeGrupoAcessoEmpresaBack.getGrupoAcessoEmpresa()).isEqualTo(grupoAcessoEmpresa);

        grupoAcessoEmpresa.removeFuncionalidadeGrupoAcessoEmpresa(funcionalidadeGrupoAcessoEmpresaBack);
        assertThat(grupoAcessoEmpresa.getFuncionalidadeGrupoAcessoEmpresas()).doesNotContain(funcionalidadeGrupoAcessoEmpresaBack);
        assertThat(funcionalidadeGrupoAcessoEmpresaBack.getGrupoAcessoEmpresa()).isNull();

        grupoAcessoEmpresa.funcionalidadeGrupoAcessoEmpresas(new HashSet<>(Set.of(funcionalidadeGrupoAcessoEmpresaBack)));
        assertThat(grupoAcessoEmpresa.getFuncionalidadeGrupoAcessoEmpresas()).containsOnly(funcionalidadeGrupoAcessoEmpresaBack);
        assertThat(funcionalidadeGrupoAcessoEmpresaBack.getGrupoAcessoEmpresa()).isEqualTo(grupoAcessoEmpresa);

        grupoAcessoEmpresa.setFuncionalidadeGrupoAcessoEmpresas(new HashSet<>());
        assertThat(grupoAcessoEmpresa.getFuncionalidadeGrupoAcessoEmpresas()).doesNotContain(funcionalidadeGrupoAcessoEmpresaBack);
        assertThat(funcionalidadeGrupoAcessoEmpresaBack.getGrupoAcessoEmpresa()).isNull();
    }

    @Test
    void grupoAcessoUsuarioEmpresaTest() {
        GrupoAcessoEmpresa grupoAcessoEmpresa = getGrupoAcessoEmpresaRandomSampleGenerator();
        GrupoAcessoUsuarioEmpresa grupoAcessoUsuarioEmpresaBack = getGrupoAcessoUsuarioEmpresaRandomSampleGenerator();

        grupoAcessoEmpresa.addGrupoAcessoUsuarioEmpresa(grupoAcessoUsuarioEmpresaBack);
        assertThat(grupoAcessoEmpresa.getGrupoAcessoUsuarioEmpresas()).containsOnly(grupoAcessoUsuarioEmpresaBack);
        assertThat(grupoAcessoUsuarioEmpresaBack.getGrupoAcessoEmpresa()).isEqualTo(grupoAcessoEmpresa);

        grupoAcessoEmpresa.removeGrupoAcessoUsuarioEmpresa(grupoAcessoUsuarioEmpresaBack);
        assertThat(grupoAcessoEmpresa.getGrupoAcessoUsuarioEmpresas()).doesNotContain(grupoAcessoUsuarioEmpresaBack);
        assertThat(grupoAcessoUsuarioEmpresaBack.getGrupoAcessoEmpresa()).isNull();

        grupoAcessoEmpresa.grupoAcessoUsuarioEmpresas(new HashSet<>(Set.of(grupoAcessoUsuarioEmpresaBack)));
        assertThat(grupoAcessoEmpresa.getGrupoAcessoUsuarioEmpresas()).containsOnly(grupoAcessoUsuarioEmpresaBack);
        assertThat(grupoAcessoUsuarioEmpresaBack.getGrupoAcessoEmpresa()).isEqualTo(grupoAcessoEmpresa);

        grupoAcessoEmpresa.setGrupoAcessoUsuarioEmpresas(new HashSet<>());
        assertThat(grupoAcessoEmpresa.getGrupoAcessoUsuarioEmpresas()).doesNotContain(grupoAcessoUsuarioEmpresaBack);
        assertThat(grupoAcessoUsuarioEmpresaBack.getGrupoAcessoEmpresa()).isNull();
    }

    @Test
    void grupoAcessoEmpresaUsuarioContadorTest() {
        GrupoAcessoEmpresa grupoAcessoEmpresa = getGrupoAcessoEmpresaRandomSampleGenerator();
        GrupoAcessoEmpresaUsuarioContador grupoAcessoEmpresaUsuarioContadorBack =
            getGrupoAcessoEmpresaUsuarioContadorRandomSampleGenerator();

        grupoAcessoEmpresa.addGrupoAcessoEmpresaUsuarioContador(grupoAcessoEmpresaUsuarioContadorBack);
        assertThat(grupoAcessoEmpresa.getGrupoAcessoEmpresaUsuarioContadors()).containsOnly(grupoAcessoEmpresaUsuarioContadorBack);
        assertThat(grupoAcessoEmpresaUsuarioContadorBack.getGrupoAcessoEmpresa()).isEqualTo(grupoAcessoEmpresa);

        grupoAcessoEmpresa.removeGrupoAcessoEmpresaUsuarioContador(grupoAcessoEmpresaUsuarioContadorBack);
        assertThat(grupoAcessoEmpresa.getGrupoAcessoEmpresaUsuarioContadors()).doesNotContain(grupoAcessoEmpresaUsuarioContadorBack);
        assertThat(grupoAcessoEmpresaUsuarioContadorBack.getGrupoAcessoEmpresa()).isNull();

        grupoAcessoEmpresa.grupoAcessoEmpresaUsuarioContadors(new HashSet<>(Set.of(grupoAcessoEmpresaUsuarioContadorBack)));
        assertThat(grupoAcessoEmpresa.getGrupoAcessoEmpresaUsuarioContadors()).containsOnly(grupoAcessoEmpresaUsuarioContadorBack);
        assertThat(grupoAcessoEmpresaUsuarioContadorBack.getGrupoAcessoEmpresa()).isEqualTo(grupoAcessoEmpresa);

        grupoAcessoEmpresa.setGrupoAcessoEmpresaUsuarioContadors(new HashSet<>());
        assertThat(grupoAcessoEmpresa.getGrupoAcessoEmpresaUsuarioContadors()).doesNotContain(grupoAcessoEmpresaUsuarioContadorBack);
        assertThat(grupoAcessoEmpresaUsuarioContadorBack.getGrupoAcessoEmpresa()).isNull();
    }

    @Test
    void assinaturaEmpresaTest() {
        GrupoAcessoEmpresa grupoAcessoEmpresa = getGrupoAcessoEmpresaRandomSampleGenerator();
        AssinaturaEmpresa assinaturaEmpresaBack = getAssinaturaEmpresaRandomSampleGenerator();

        grupoAcessoEmpresa.setAssinaturaEmpresa(assinaturaEmpresaBack);
        assertThat(grupoAcessoEmpresa.getAssinaturaEmpresa()).isEqualTo(assinaturaEmpresaBack);

        grupoAcessoEmpresa.assinaturaEmpresa(null);
        assertThat(grupoAcessoEmpresa.getAssinaturaEmpresa()).isNull();
    }
}
