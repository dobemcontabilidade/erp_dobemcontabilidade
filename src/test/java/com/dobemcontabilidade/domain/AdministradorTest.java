package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AdministradorTestSamples.*;
import static com.dobemcontabilidade.domain.PessoaTestSamples.*;
import static com.dobemcontabilidade.domain.UsuarioContadorTestSamples.*;
import static com.dobemcontabilidade.domain.UsuarioErpTestSamples.*;
import static com.dobemcontabilidade.domain.UsuarioGestaoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
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
    void usuarioContadorTest() {
        Administrador administrador = getAdministradorRandomSampleGenerator();
        UsuarioContador usuarioContadorBack = getUsuarioContadorRandomSampleGenerator();

        administrador.addUsuarioContador(usuarioContadorBack);
        assertThat(administrador.getUsuarioContadors()).containsOnly(usuarioContadorBack);
        assertThat(usuarioContadorBack.getAdministrador()).isEqualTo(administrador);

        administrador.removeUsuarioContador(usuarioContadorBack);
        assertThat(administrador.getUsuarioContadors()).doesNotContain(usuarioContadorBack);
        assertThat(usuarioContadorBack.getAdministrador()).isNull();

        administrador.usuarioContadors(new HashSet<>(Set.of(usuarioContadorBack)));
        assertThat(administrador.getUsuarioContadors()).containsOnly(usuarioContadorBack);
        assertThat(usuarioContadorBack.getAdministrador()).isEqualTo(administrador);

        administrador.setUsuarioContadors(new HashSet<>());
        assertThat(administrador.getUsuarioContadors()).doesNotContain(usuarioContadorBack);
        assertThat(usuarioContadorBack.getAdministrador()).isNull();
    }

    @Test
    void usuarioErpTest() {
        Administrador administrador = getAdministradorRandomSampleGenerator();
        UsuarioErp usuarioErpBack = getUsuarioErpRandomSampleGenerator();

        administrador.setUsuarioErp(usuarioErpBack);
        assertThat(administrador.getUsuarioErp()).isEqualTo(usuarioErpBack);
        assertThat(usuarioErpBack.getAdministrador()).isEqualTo(administrador);

        administrador.usuarioErp(null);
        assertThat(administrador.getUsuarioErp()).isNull();
        assertThat(usuarioErpBack.getAdministrador()).isNull();
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
