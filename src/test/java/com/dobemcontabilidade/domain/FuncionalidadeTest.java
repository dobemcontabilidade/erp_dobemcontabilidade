package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.FuncionalidadeGrupoAcessoEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.FuncionalidadeGrupoAcessoPadraoTestSamples.*;
import static com.dobemcontabilidade.domain.FuncionalidadeTestSamples.*;
import static com.dobemcontabilidade.domain.ModuloTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class FuncionalidadeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Funcionalidade.class);
        Funcionalidade funcionalidade1 = getFuncionalidadeSample1();
        Funcionalidade funcionalidade2 = new Funcionalidade();
        assertThat(funcionalidade1).isNotEqualTo(funcionalidade2);

        funcionalidade2.setId(funcionalidade1.getId());
        assertThat(funcionalidade1).isEqualTo(funcionalidade2);

        funcionalidade2 = getFuncionalidadeSample2();
        assertThat(funcionalidade1).isNotEqualTo(funcionalidade2);
    }

    @Test
    void funcionalidadeGrupoAcessoPadraoTest() {
        Funcionalidade funcionalidade = getFuncionalidadeRandomSampleGenerator();
        FuncionalidadeGrupoAcessoPadrao funcionalidadeGrupoAcessoPadraoBack = getFuncionalidadeGrupoAcessoPadraoRandomSampleGenerator();

        funcionalidade.addFuncionalidadeGrupoAcessoPadrao(funcionalidadeGrupoAcessoPadraoBack);
        assertThat(funcionalidade.getFuncionalidadeGrupoAcessoPadraos()).containsOnly(funcionalidadeGrupoAcessoPadraoBack);
        assertThat(funcionalidadeGrupoAcessoPadraoBack.getFuncionalidade()).isEqualTo(funcionalidade);

        funcionalidade.removeFuncionalidadeGrupoAcessoPadrao(funcionalidadeGrupoAcessoPadraoBack);
        assertThat(funcionalidade.getFuncionalidadeGrupoAcessoPadraos()).doesNotContain(funcionalidadeGrupoAcessoPadraoBack);
        assertThat(funcionalidadeGrupoAcessoPadraoBack.getFuncionalidade()).isNull();

        funcionalidade.funcionalidadeGrupoAcessoPadraos(new HashSet<>(Set.of(funcionalidadeGrupoAcessoPadraoBack)));
        assertThat(funcionalidade.getFuncionalidadeGrupoAcessoPadraos()).containsOnly(funcionalidadeGrupoAcessoPadraoBack);
        assertThat(funcionalidadeGrupoAcessoPadraoBack.getFuncionalidade()).isEqualTo(funcionalidade);

        funcionalidade.setFuncionalidadeGrupoAcessoPadraos(new HashSet<>());
        assertThat(funcionalidade.getFuncionalidadeGrupoAcessoPadraos()).doesNotContain(funcionalidadeGrupoAcessoPadraoBack);
        assertThat(funcionalidadeGrupoAcessoPadraoBack.getFuncionalidade()).isNull();
    }

    @Test
    void funcionalidadeGrupoAcessoEmpresaTest() {
        Funcionalidade funcionalidade = getFuncionalidadeRandomSampleGenerator();
        FuncionalidadeGrupoAcessoEmpresa funcionalidadeGrupoAcessoEmpresaBack = getFuncionalidadeGrupoAcessoEmpresaRandomSampleGenerator();

        funcionalidade.addFuncionalidadeGrupoAcessoEmpresa(funcionalidadeGrupoAcessoEmpresaBack);
        assertThat(funcionalidade.getFuncionalidadeGrupoAcessoEmpresas()).containsOnly(funcionalidadeGrupoAcessoEmpresaBack);
        assertThat(funcionalidadeGrupoAcessoEmpresaBack.getFuncionalidade()).isEqualTo(funcionalidade);

        funcionalidade.removeFuncionalidadeGrupoAcessoEmpresa(funcionalidadeGrupoAcessoEmpresaBack);
        assertThat(funcionalidade.getFuncionalidadeGrupoAcessoEmpresas()).doesNotContain(funcionalidadeGrupoAcessoEmpresaBack);
        assertThat(funcionalidadeGrupoAcessoEmpresaBack.getFuncionalidade()).isNull();

        funcionalidade.funcionalidadeGrupoAcessoEmpresas(new HashSet<>(Set.of(funcionalidadeGrupoAcessoEmpresaBack)));
        assertThat(funcionalidade.getFuncionalidadeGrupoAcessoEmpresas()).containsOnly(funcionalidadeGrupoAcessoEmpresaBack);
        assertThat(funcionalidadeGrupoAcessoEmpresaBack.getFuncionalidade()).isEqualTo(funcionalidade);

        funcionalidade.setFuncionalidadeGrupoAcessoEmpresas(new HashSet<>());
        assertThat(funcionalidade.getFuncionalidadeGrupoAcessoEmpresas()).doesNotContain(funcionalidadeGrupoAcessoEmpresaBack);
        assertThat(funcionalidadeGrupoAcessoEmpresaBack.getFuncionalidade()).isNull();
    }

    @Test
    void moduloTest() {
        Funcionalidade funcionalidade = getFuncionalidadeRandomSampleGenerator();
        Modulo moduloBack = getModuloRandomSampleGenerator();

        funcionalidade.setModulo(moduloBack);
        assertThat(funcionalidade.getModulo()).isEqualTo(moduloBack);

        funcionalidade.modulo(null);
        assertThat(funcionalidade.getModulo()).isNull();
    }
}
