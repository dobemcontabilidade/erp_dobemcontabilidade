package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.FuncionalidadeGrupoAcessoEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.FuncionalidadeTestSamples.*;
import static com.dobemcontabilidade.domain.GrupoAcessoEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.PermisaoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FuncionalidadeGrupoAcessoEmpresaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FuncionalidadeGrupoAcessoEmpresa.class);
        FuncionalidadeGrupoAcessoEmpresa funcionalidadeGrupoAcessoEmpresa1 = getFuncionalidadeGrupoAcessoEmpresaSample1();
        FuncionalidadeGrupoAcessoEmpresa funcionalidadeGrupoAcessoEmpresa2 = new FuncionalidadeGrupoAcessoEmpresa();
        assertThat(funcionalidadeGrupoAcessoEmpresa1).isNotEqualTo(funcionalidadeGrupoAcessoEmpresa2);

        funcionalidadeGrupoAcessoEmpresa2.setId(funcionalidadeGrupoAcessoEmpresa1.getId());
        assertThat(funcionalidadeGrupoAcessoEmpresa1).isEqualTo(funcionalidadeGrupoAcessoEmpresa2);

        funcionalidadeGrupoAcessoEmpresa2 = getFuncionalidadeGrupoAcessoEmpresaSample2();
        assertThat(funcionalidadeGrupoAcessoEmpresa1).isNotEqualTo(funcionalidadeGrupoAcessoEmpresa2);
    }

    @Test
    void funcionalidadeTest() {
        FuncionalidadeGrupoAcessoEmpresa funcionalidadeGrupoAcessoEmpresa = getFuncionalidadeGrupoAcessoEmpresaRandomSampleGenerator();
        Funcionalidade funcionalidadeBack = getFuncionalidadeRandomSampleGenerator();

        funcionalidadeGrupoAcessoEmpresa.setFuncionalidade(funcionalidadeBack);
        assertThat(funcionalidadeGrupoAcessoEmpresa.getFuncionalidade()).isEqualTo(funcionalidadeBack);

        funcionalidadeGrupoAcessoEmpresa.funcionalidade(null);
        assertThat(funcionalidadeGrupoAcessoEmpresa.getFuncionalidade()).isNull();
    }

    @Test
    void grupoAcessoEmpresaTest() {
        FuncionalidadeGrupoAcessoEmpresa funcionalidadeGrupoAcessoEmpresa = getFuncionalidadeGrupoAcessoEmpresaRandomSampleGenerator();
        GrupoAcessoEmpresa grupoAcessoEmpresaBack = getGrupoAcessoEmpresaRandomSampleGenerator();

        funcionalidadeGrupoAcessoEmpresa.setGrupoAcessoEmpresa(grupoAcessoEmpresaBack);
        assertThat(funcionalidadeGrupoAcessoEmpresa.getGrupoAcessoEmpresa()).isEqualTo(grupoAcessoEmpresaBack);

        funcionalidadeGrupoAcessoEmpresa.grupoAcessoEmpresa(null);
        assertThat(funcionalidadeGrupoAcessoEmpresa.getGrupoAcessoEmpresa()).isNull();
    }

    @Test
    void permisaoTest() {
        FuncionalidadeGrupoAcessoEmpresa funcionalidadeGrupoAcessoEmpresa = getFuncionalidadeGrupoAcessoEmpresaRandomSampleGenerator();
        Permisao permisaoBack = getPermisaoRandomSampleGenerator();

        funcionalidadeGrupoAcessoEmpresa.setPermisao(permisaoBack);
        assertThat(funcionalidadeGrupoAcessoEmpresa.getPermisao()).isEqualTo(permisaoBack);

        funcionalidadeGrupoAcessoEmpresa.permisao(null);
        assertThat(funcionalidadeGrupoAcessoEmpresa.getPermisao()).isNull();
    }
}
