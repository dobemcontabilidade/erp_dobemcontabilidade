package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.PerfilRedeSocialTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PerfilRedeSocialTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PerfilRedeSocial.class);
        PerfilRedeSocial perfilRedeSocial1 = getPerfilRedeSocialSample1();
        PerfilRedeSocial perfilRedeSocial2 = new PerfilRedeSocial();
        assertThat(perfilRedeSocial1).isNotEqualTo(perfilRedeSocial2);

        perfilRedeSocial2.setId(perfilRedeSocial1.getId());
        assertThat(perfilRedeSocial1).isEqualTo(perfilRedeSocial2);

        perfilRedeSocial2 = getPerfilRedeSocialSample2();
        assertThat(perfilRedeSocial1).isNotEqualTo(perfilRedeSocial2);
    }
}
