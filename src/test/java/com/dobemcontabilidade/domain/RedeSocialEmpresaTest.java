package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.PessoajuridicaTestSamples.*;
import static com.dobemcontabilidade.domain.RedeSocialEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.RedeSocialTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RedeSocialEmpresaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RedeSocialEmpresa.class);
        RedeSocialEmpresa redeSocialEmpresa1 = getRedeSocialEmpresaSample1();
        RedeSocialEmpresa redeSocialEmpresa2 = new RedeSocialEmpresa();
        assertThat(redeSocialEmpresa1).isNotEqualTo(redeSocialEmpresa2);

        redeSocialEmpresa2.setId(redeSocialEmpresa1.getId());
        assertThat(redeSocialEmpresa1).isEqualTo(redeSocialEmpresa2);

        redeSocialEmpresa2 = getRedeSocialEmpresaSample2();
        assertThat(redeSocialEmpresa1).isNotEqualTo(redeSocialEmpresa2);
    }

    @Test
    void redeSocialTest() {
        RedeSocialEmpresa redeSocialEmpresa = getRedeSocialEmpresaRandomSampleGenerator();
        RedeSocial redeSocialBack = getRedeSocialRandomSampleGenerator();

        redeSocialEmpresa.setRedeSocial(redeSocialBack);
        assertThat(redeSocialEmpresa.getRedeSocial()).isEqualTo(redeSocialBack);

        redeSocialEmpresa.redeSocial(null);
        assertThat(redeSocialEmpresa.getRedeSocial()).isNull();
    }

    @Test
    void pessoajuridicaTest() {
        RedeSocialEmpresa redeSocialEmpresa = getRedeSocialEmpresaRandomSampleGenerator();
        Pessoajuridica pessoajuridicaBack = getPessoajuridicaRandomSampleGenerator();

        redeSocialEmpresa.setPessoajuridica(pessoajuridicaBack);
        assertThat(redeSocialEmpresa.getPessoajuridica()).isEqualTo(pessoajuridicaBack);

        redeSocialEmpresa.pessoajuridica(null);
        assertThat(redeSocialEmpresa.getPessoajuridica()).isNull();
    }
}
