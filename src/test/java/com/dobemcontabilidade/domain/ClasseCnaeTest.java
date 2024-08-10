package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.ClasseCnaeTestSamples.*;
import static com.dobemcontabilidade.domain.GrupoCnaeTestSamples.*;
import static com.dobemcontabilidade.domain.SubclasseCnaeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ClasseCnaeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClasseCnae.class);
        ClasseCnae classeCnae1 = getClasseCnaeSample1();
        ClasseCnae classeCnae2 = new ClasseCnae();
        assertThat(classeCnae1).isNotEqualTo(classeCnae2);

        classeCnae2.setId(classeCnae1.getId());
        assertThat(classeCnae1).isEqualTo(classeCnae2);

        classeCnae2 = getClasseCnaeSample2();
        assertThat(classeCnae1).isNotEqualTo(classeCnae2);
    }

    @Test
    void subclasseCnaeTest() {
        ClasseCnae classeCnae = getClasseCnaeRandomSampleGenerator();
        SubclasseCnae subclasseCnaeBack = getSubclasseCnaeRandomSampleGenerator();

        classeCnae.addSubclasseCnae(subclasseCnaeBack);
        assertThat(classeCnae.getSubclasseCnaes()).containsOnly(subclasseCnaeBack);
        assertThat(subclasseCnaeBack.getClasse()).isEqualTo(classeCnae);

        classeCnae.removeSubclasseCnae(subclasseCnaeBack);
        assertThat(classeCnae.getSubclasseCnaes()).doesNotContain(subclasseCnaeBack);
        assertThat(subclasseCnaeBack.getClasse()).isNull();

        classeCnae.subclasseCnaes(new HashSet<>(Set.of(subclasseCnaeBack)));
        assertThat(classeCnae.getSubclasseCnaes()).containsOnly(subclasseCnaeBack);
        assertThat(subclasseCnaeBack.getClasse()).isEqualTo(classeCnae);

        classeCnae.setSubclasseCnaes(new HashSet<>());
        assertThat(classeCnae.getSubclasseCnaes()).doesNotContain(subclasseCnaeBack);
        assertThat(subclasseCnaeBack.getClasse()).isNull();
    }

    @Test
    void grupoTest() {
        ClasseCnae classeCnae = getClasseCnaeRandomSampleGenerator();
        GrupoCnae grupoCnaeBack = getGrupoCnaeRandomSampleGenerator();

        classeCnae.setGrupo(grupoCnaeBack);
        assertThat(classeCnae.getGrupo()).isEqualTo(grupoCnaeBack);

        classeCnae.grupo(null);
        assertThat(classeCnae.getGrupo()).isNull();
    }
}
