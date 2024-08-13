package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.PerfilAcessoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PerfilAcessoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PerfilAcesso.class);
        PerfilAcesso perfilAcesso1 = getPerfilAcessoSample1();
        PerfilAcesso perfilAcesso2 = new PerfilAcesso();
        assertThat(perfilAcesso1).isNotEqualTo(perfilAcesso2);

        perfilAcesso2.setId(perfilAcesso1.getId());
        assertThat(perfilAcesso1).isEqualTo(perfilAcesso2);

        perfilAcesso2 = getPerfilAcessoSample2();
        assertThat(perfilAcesso1).isNotEqualTo(perfilAcesso2);
    }
}
