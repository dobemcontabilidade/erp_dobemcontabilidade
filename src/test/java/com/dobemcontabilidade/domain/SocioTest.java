package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.EmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.PessoaTestSamples.*;
import static com.dobemcontabilidade.domain.ProfissaoTestSamples.*;
import static com.dobemcontabilidade.domain.SocioTestSamples.*;
import static com.dobemcontabilidade.domain.UsuarioEmpresaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SocioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Socio.class);
        Socio socio1 = getSocioSample1();
        Socio socio2 = new Socio();
        assertThat(socio1).isNotEqualTo(socio2);

        socio2.setId(socio1.getId());
        assertThat(socio1).isEqualTo(socio2);

        socio2 = getSocioSample2();
        assertThat(socio1).isNotEqualTo(socio2);
    }

    @Test
    void pessoaTest() {
        Socio socio = getSocioRandomSampleGenerator();
        Pessoa pessoaBack = getPessoaRandomSampleGenerator();

        socio.setPessoa(pessoaBack);
        assertThat(socio.getPessoa()).isEqualTo(pessoaBack);

        socio.pessoa(null);
        assertThat(socio.getPessoa()).isNull();
    }

    @Test
    void usuarioEmpresaTest() {
        Socio socio = getSocioRandomSampleGenerator();
        UsuarioEmpresa usuarioEmpresaBack = getUsuarioEmpresaRandomSampleGenerator();

        socio.setUsuarioEmpresa(usuarioEmpresaBack);
        assertThat(socio.getUsuarioEmpresa()).isEqualTo(usuarioEmpresaBack);

        socio.usuarioEmpresa(null);
        assertThat(socio.getUsuarioEmpresa()).isNull();
    }

    @Test
    void empresaTest() {
        Socio socio = getSocioRandomSampleGenerator();
        Empresa empresaBack = getEmpresaRandomSampleGenerator();

        socio.setEmpresa(empresaBack);
        assertThat(socio.getEmpresa()).isEqualTo(empresaBack);

        socio.empresa(null);
        assertThat(socio.getEmpresa()).isNull();
    }

    @Test
    void profissaoTest() {
        Socio socio = getSocioRandomSampleGenerator();
        Profissao profissaoBack = getProfissaoRandomSampleGenerator();

        socio.setProfissao(profissaoBack);
        assertThat(socio.getProfissao()).isEqualTo(profissaoBack);

        socio.profissao(null);
        assertThat(socio.getProfissao()).isNull();
    }
}
