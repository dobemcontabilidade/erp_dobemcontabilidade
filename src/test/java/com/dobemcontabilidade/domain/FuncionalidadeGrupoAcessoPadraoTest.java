package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.FuncionalidadeGrupoAcessoPadraoTestSamples.*;
import static com.dobemcontabilidade.domain.FuncionalidadeTestSamples.*;
import static com.dobemcontabilidade.domain.GrupoAcessoPadraoTestSamples.*;
import static com.dobemcontabilidade.domain.PermisaoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FuncionalidadeGrupoAcessoPadraoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FuncionalidadeGrupoAcessoPadrao.class);
        FuncionalidadeGrupoAcessoPadrao funcionalidadeGrupoAcessoPadrao1 = getFuncionalidadeGrupoAcessoPadraoSample1();
        FuncionalidadeGrupoAcessoPadrao funcionalidadeGrupoAcessoPadrao2 = new FuncionalidadeGrupoAcessoPadrao();
        assertThat(funcionalidadeGrupoAcessoPadrao1).isNotEqualTo(funcionalidadeGrupoAcessoPadrao2);

        funcionalidadeGrupoAcessoPadrao2.setId(funcionalidadeGrupoAcessoPadrao1.getId());
        assertThat(funcionalidadeGrupoAcessoPadrao1).isEqualTo(funcionalidadeGrupoAcessoPadrao2);

        funcionalidadeGrupoAcessoPadrao2 = getFuncionalidadeGrupoAcessoPadraoSample2();
        assertThat(funcionalidadeGrupoAcessoPadrao1).isNotEqualTo(funcionalidadeGrupoAcessoPadrao2);
    }

    @Test
    void funcionalidadeTest() {
        FuncionalidadeGrupoAcessoPadrao funcionalidadeGrupoAcessoPadrao = getFuncionalidadeGrupoAcessoPadraoRandomSampleGenerator();
        Funcionalidade funcionalidadeBack = getFuncionalidadeRandomSampleGenerator();

        funcionalidadeGrupoAcessoPadrao.setFuncionalidade(funcionalidadeBack);
        assertThat(funcionalidadeGrupoAcessoPadrao.getFuncionalidade()).isEqualTo(funcionalidadeBack);

        funcionalidadeGrupoAcessoPadrao.funcionalidade(null);
        assertThat(funcionalidadeGrupoAcessoPadrao.getFuncionalidade()).isNull();
    }

    @Test
    void grupoAcessoPadraoTest() {
        FuncionalidadeGrupoAcessoPadrao funcionalidadeGrupoAcessoPadrao = getFuncionalidadeGrupoAcessoPadraoRandomSampleGenerator();
        GrupoAcessoPadrao grupoAcessoPadraoBack = getGrupoAcessoPadraoRandomSampleGenerator();

        funcionalidadeGrupoAcessoPadrao.setGrupoAcessoPadrao(grupoAcessoPadraoBack);
        assertThat(funcionalidadeGrupoAcessoPadrao.getGrupoAcessoPadrao()).isEqualTo(grupoAcessoPadraoBack);

        funcionalidadeGrupoAcessoPadrao.grupoAcessoPadrao(null);
        assertThat(funcionalidadeGrupoAcessoPadrao.getGrupoAcessoPadrao()).isNull();
    }

    @Test
    void permisaoTest() {
        FuncionalidadeGrupoAcessoPadrao funcionalidadeGrupoAcessoPadrao = getFuncionalidadeGrupoAcessoPadraoRandomSampleGenerator();
        Permisao permisaoBack = getPermisaoRandomSampleGenerator();

        funcionalidadeGrupoAcessoPadrao.setPermisao(permisaoBack);
        assertThat(funcionalidadeGrupoAcessoPadrao.getPermisao()).isEqualTo(permisaoBack);

        funcionalidadeGrupoAcessoPadrao.permisao(null);
        assertThat(funcionalidadeGrupoAcessoPadrao.getPermisao()).isNull();
    }
}
