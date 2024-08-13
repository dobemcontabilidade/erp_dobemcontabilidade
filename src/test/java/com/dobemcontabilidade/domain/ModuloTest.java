package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.FuncionalidadeTestSamples.*;
import static com.dobemcontabilidade.domain.ModuloTestSamples.*;
import static com.dobemcontabilidade.domain.SistemaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ModuloTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Modulo.class);
        Modulo modulo1 = getModuloSample1();
        Modulo modulo2 = new Modulo();
        assertThat(modulo1).isNotEqualTo(modulo2);

        modulo2.setId(modulo1.getId());
        assertThat(modulo1).isEqualTo(modulo2);

        modulo2 = getModuloSample2();
        assertThat(modulo1).isNotEqualTo(modulo2);
    }

    @Test
    void funcionalidadeTest() {
        Modulo modulo = getModuloRandomSampleGenerator();
        Funcionalidade funcionalidadeBack = getFuncionalidadeRandomSampleGenerator();

        modulo.addFuncionalidade(funcionalidadeBack);
        assertThat(modulo.getFuncionalidades()).containsOnly(funcionalidadeBack);
        assertThat(funcionalidadeBack.getModulo()).isEqualTo(modulo);

        modulo.removeFuncionalidade(funcionalidadeBack);
        assertThat(modulo.getFuncionalidades()).doesNotContain(funcionalidadeBack);
        assertThat(funcionalidadeBack.getModulo()).isNull();

        modulo.funcionalidades(new HashSet<>(Set.of(funcionalidadeBack)));
        assertThat(modulo.getFuncionalidades()).containsOnly(funcionalidadeBack);
        assertThat(funcionalidadeBack.getModulo()).isEqualTo(modulo);

        modulo.setFuncionalidades(new HashSet<>());
        assertThat(modulo.getFuncionalidades()).doesNotContain(funcionalidadeBack);
        assertThat(funcionalidadeBack.getModulo()).isNull();
    }

    @Test
    void sistemaTest() {
        Modulo modulo = getModuloRandomSampleGenerator();
        Sistema sistemaBack = getSistemaRandomSampleGenerator();

        modulo.setSistema(sistemaBack);
        assertThat(modulo.getSistema()).isEqualTo(sistemaBack);

        modulo.sistema(null);
        assertThat(modulo.getSistema()).isNull();
    }
}
