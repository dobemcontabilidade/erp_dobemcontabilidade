package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AdministradorTestSamples.*;
import static com.dobemcontabilidade.domain.PessoaTestSamples.*;
import static com.dobemcontabilidade.domain.UsuarioGestaoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdministradorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Administrador.class);
        Administrador administrador1 = getAdministradorSample1();
        Administrador administrador2 = new Administrador();
        assertThat(administrador1).isNotEqualTo(administrador2);

        administrador2.setId(administrador1.getId());
        assertThat(administrador1).isEqualTo(administrador2);

        administrador2 = getAdministradorSample2();
        assertThat(administrador1).isNotEqualTo(administrador2);
    }

    @Test
    void pessoaTest() {
        Administrador administrador = getAdministradorRandomSampleGenerator();
        Pessoa pessoaBack = getPessoaRandomSampleGenerator();

        administrador.setPessoa(pessoaBack);
        assertThat(administrador.getPessoa()).isEqualTo(pessoaBack);

        administrador.pessoa(null);
        assertThat(administrador.getPessoa()).isNull();
    }

    @Test
    void usuarioGestaoTest() {
        Administrador administrador = getAdministradorRandomSampleGenerator();
        UsuarioGestao usuarioGestaoBack = getUsuarioGestaoRandomSampleGenerator();

        administrador.setUsuarioGestao(usuarioGestaoBack);
        assertThat(administrador.getUsuarioGestao()).isEqualTo(usuarioGestaoBack);
        assertThat(usuarioGestaoBack.getAdministrador()).isEqualTo(administrador);

        administrador.usuarioGestao(null);
        assertThat(administrador.getUsuarioGestao()).isNull();
        assertThat(usuarioGestaoBack.getAdministrador()).isNull();
    }
}
