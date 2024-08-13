package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.FuncionalidadeGrupoAcessoPadraoTestSamples.*;
import static com.dobemcontabilidade.domain.GrupoAcessoPadraoTestSamples.*;
import static com.dobemcontabilidade.domain.GrupoAcessoUsuarioContadorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class GrupoAcessoPadraoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrupoAcessoPadrao.class);
        GrupoAcessoPadrao grupoAcessoPadrao1 = getGrupoAcessoPadraoSample1();
        GrupoAcessoPadrao grupoAcessoPadrao2 = new GrupoAcessoPadrao();
        assertThat(grupoAcessoPadrao1).isNotEqualTo(grupoAcessoPadrao2);

        grupoAcessoPadrao2.setId(grupoAcessoPadrao1.getId());
        assertThat(grupoAcessoPadrao1).isEqualTo(grupoAcessoPadrao2);

        grupoAcessoPadrao2 = getGrupoAcessoPadraoSample2();
        assertThat(grupoAcessoPadrao1).isNotEqualTo(grupoAcessoPadrao2);
    }

    @Test
    void funcionalidadeGrupoAcessoPadraoTest() {
        GrupoAcessoPadrao grupoAcessoPadrao = getGrupoAcessoPadraoRandomSampleGenerator();
        FuncionalidadeGrupoAcessoPadrao funcionalidadeGrupoAcessoPadraoBack = getFuncionalidadeGrupoAcessoPadraoRandomSampleGenerator();

        grupoAcessoPadrao.addFuncionalidadeGrupoAcessoPadrao(funcionalidadeGrupoAcessoPadraoBack);
        assertThat(grupoAcessoPadrao.getFuncionalidadeGrupoAcessoPadraos()).containsOnly(funcionalidadeGrupoAcessoPadraoBack);
        assertThat(funcionalidadeGrupoAcessoPadraoBack.getGrupoAcessoPadrao()).isEqualTo(grupoAcessoPadrao);

        grupoAcessoPadrao.removeFuncionalidadeGrupoAcessoPadrao(funcionalidadeGrupoAcessoPadraoBack);
        assertThat(grupoAcessoPadrao.getFuncionalidadeGrupoAcessoPadraos()).doesNotContain(funcionalidadeGrupoAcessoPadraoBack);
        assertThat(funcionalidadeGrupoAcessoPadraoBack.getGrupoAcessoPadrao()).isNull();

        grupoAcessoPadrao.funcionalidadeGrupoAcessoPadraos(new HashSet<>(Set.of(funcionalidadeGrupoAcessoPadraoBack)));
        assertThat(grupoAcessoPadrao.getFuncionalidadeGrupoAcessoPadraos()).containsOnly(funcionalidadeGrupoAcessoPadraoBack);
        assertThat(funcionalidadeGrupoAcessoPadraoBack.getGrupoAcessoPadrao()).isEqualTo(grupoAcessoPadrao);

        grupoAcessoPadrao.setFuncionalidadeGrupoAcessoPadraos(new HashSet<>());
        assertThat(grupoAcessoPadrao.getFuncionalidadeGrupoAcessoPadraos()).doesNotContain(funcionalidadeGrupoAcessoPadraoBack);
        assertThat(funcionalidadeGrupoAcessoPadraoBack.getGrupoAcessoPadrao()).isNull();
    }

    @Test
    void grupoAcessoUsuarioContadorTest() {
        GrupoAcessoPadrao grupoAcessoPadrao = getGrupoAcessoPadraoRandomSampleGenerator();
        GrupoAcessoUsuarioContador grupoAcessoUsuarioContadorBack = getGrupoAcessoUsuarioContadorRandomSampleGenerator();

        grupoAcessoPadrao.addGrupoAcessoUsuarioContador(grupoAcessoUsuarioContadorBack);
        assertThat(grupoAcessoPadrao.getGrupoAcessoUsuarioContadors()).containsOnly(grupoAcessoUsuarioContadorBack);
        assertThat(grupoAcessoUsuarioContadorBack.getGrupoAcessoPadrao()).isEqualTo(grupoAcessoPadrao);

        grupoAcessoPadrao.removeGrupoAcessoUsuarioContador(grupoAcessoUsuarioContadorBack);
        assertThat(grupoAcessoPadrao.getGrupoAcessoUsuarioContadors()).doesNotContain(grupoAcessoUsuarioContadorBack);
        assertThat(grupoAcessoUsuarioContadorBack.getGrupoAcessoPadrao()).isNull();

        grupoAcessoPadrao.grupoAcessoUsuarioContadors(new HashSet<>(Set.of(grupoAcessoUsuarioContadorBack)));
        assertThat(grupoAcessoPadrao.getGrupoAcessoUsuarioContadors()).containsOnly(grupoAcessoUsuarioContadorBack);
        assertThat(grupoAcessoUsuarioContadorBack.getGrupoAcessoPadrao()).isEqualTo(grupoAcessoPadrao);

        grupoAcessoPadrao.setGrupoAcessoUsuarioContadors(new HashSet<>());
        assertThat(grupoAcessoPadrao.getGrupoAcessoUsuarioContadors()).doesNotContain(grupoAcessoUsuarioContadorBack);
        assertThat(grupoAcessoUsuarioContadorBack.getGrupoAcessoPadrao()).isNull();
    }
}
