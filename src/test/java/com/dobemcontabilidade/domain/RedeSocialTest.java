package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.RedeSocialEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.RedeSocialTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class RedeSocialTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RedeSocial.class);
        RedeSocial redeSocial1 = getRedeSocialSample1();
        RedeSocial redeSocial2 = new RedeSocial();
        assertThat(redeSocial1).isNotEqualTo(redeSocial2);

        redeSocial2.setId(redeSocial1.getId());
        assertThat(redeSocial1).isEqualTo(redeSocial2);

        redeSocial2 = getRedeSocialSample2();
        assertThat(redeSocial1).isNotEqualTo(redeSocial2);
    }

    @Test
    void redeSocialEmpresaTest() {
        RedeSocial redeSocial = getRedeSocialRandomSampleGenerator();
        RedeSocialEmpresa redeSocialEmpresaBack = getRedeSocialEmpresaRandomSampleGenerator();

        redeSocial.addRedeSocialEmpresa(redeSocialEmpresaBack);
        assertThat(redeSocial.getRedeSocialEmpresas()).containsOnly(redeSocialEmpresaBack);
        assertThat(redeSocialEmpresaBack.getRedeSocial()).isEqualTo(redeSocial);

        redeSocial.removeRedeSocialEmpresa(redeSocialEmpresaBack);
        assertThat(redeSocial.getRedeSocialEmpresas()).doesNotContain(redeSocialEmpresaBack);
        assertThat(redeSocialEmpresaBack.getRedeSocial()).isNull();

        redeSocial.redeSocialEmpresas(new HashSet<>(Set.of(redeSocialEmpresaBack)));
        assertThat(redeSocial.getRedeSocialEmpresas()).containsOnly(redeSocialEmpresaBack);
        assertThat(redeSocialEmpresaBack.getRedeSocial()).isEqualTo(redeSocial);

        redeSocial.setRedeSocialEmpresas(new HashSet<>());
        assertThat(redeSocial.getRedeSocialEmpresas()).doesNotContain(redeSocialEmpresaBack);
        assertThat(redeSocialEmpresaBack.getRedeSocial()).isNull();
    }
}
