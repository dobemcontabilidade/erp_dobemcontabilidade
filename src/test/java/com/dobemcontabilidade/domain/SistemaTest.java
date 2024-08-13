package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.ModuloTestSamples.*;
import static com.dobemcontabilidade.domain.SistemaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class SistemaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sistema.class);
        Sistema sistema1 = getSistemaSample1();
        Sistema sistema2 = new Sistema();
        assertThat(sistema1).isNotEqualTo(sistema2);

        sistema2.setId(sistema1.getId());
        assertThat(sistema1).isEqualTo(sistema2);

        sistema2 = getSistemaSample2();
        assertThat(sistema1).isNotEqualTo(sistema2);
    }

    @Test
    void moduloTest() {
        Sistema sistema = getSistemaRandomSampleGenerator();
        Modulo moduloBack = getModuloRandomSampleGenerator();

        sistema.addModulo(moduloBack);
        assertThat(sistema.getModulos()).containsOnly(moduloBack);
        assertThat(moduloBack.getSistema()).isEqualTo(sistema);

        sistema.removeModulo(moduloBack);
        assertThat(sistema.getModulos()).doesNotContain(moduloBack);
        assertThat(moduloBack.getSistema()).isNull();

        sistema.modulos(new HashSet<>(Set.of(moduloBack)));
        assertThat(sistema.getModulos()).containsOnly(moduloBack);
        assertThat(moduloBack.getSistema()).isEqualTo(sistema);

        sistema.setModulos(new HashSet<>());
        assertThat(sistema.getModulos()).doesNotContain(moduloBack);
        assertThat(moduloBack.getSistema()).isNull();
    }
}
