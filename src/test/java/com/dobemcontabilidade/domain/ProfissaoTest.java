package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.ProfissaoTestSamples.*;
import static com.dobemcontabilidade.domain.SocioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProfissaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Profissao.class);
        Profissao profissao1 = getProfissaoSample1();
        Profissao profissao2 = new Profissao();
        assertThat(profissao1).isNotEqualTo(profissao2);

        profissao2.setId(profissao1.getId());
        assertThat(profissao1).isEqualTo(profissao2);

        profissao2 = getProfissaoSample2();
        assertThat(profissao1).isNotEqualTo(profissao2);
    }

    @Test
    void socioTest() {
        Profissao profissao = getProfissaoRandomSampleGenerator();
        Socio socioBack = getSocioRandomSampleGenerator();

        profissao.setSocio(socioBack);
        assertThat(profissao.getSocio()).isEqualTo(socioBack);

        profissao.socio(null);
        assertThat(profissao.getSocio()).isNull();
    }
}
