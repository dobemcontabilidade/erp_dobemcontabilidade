package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.EmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.PessoaTestSamples.*;
import static com.dobemcontabilidade.domain.UsuarioEmpresaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UsuarioEmpresaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UsuarioEmpresa.class);
        UsuarioEmpresa usuarioEmpresa1 = getUsuarioEmpresaSample1();
        UsuarioEmpresa usuarioEmpresa2 = new UsuarioEmpresa();
        assertThat(usuarioEmpresa1).isNotEqualTo(usuarioEmpresa2);

        usuarioEmpresa2.setId(usuarioEmpresa1.getId());
        assertThat(usuarioEmpresa1).isEqualTo(usuarioEmpresa2);

        usuarioEmpresa2 = getUsuarioEmpresaSample2();
        assertThat(usuarioEmpresa1).isNotEqualTo(usuarioEmpresa2);
    }

    @Test
    void pessoaTest() {
        UsuarioEmpresa usuarioEmpresa = getUsuarioEmpresaRandomSampleGenerator();
        Pessoa pessoaBack = getPessoaRandomSampleGenerator();

        usuarioEmpresa.setPessoa(pessoaBack);
        assertThat(usuarioEmpresa.getPessoa()).isEqualTo(pessoaBack);

        usuarioEmpresa.pessoa(null);
        assertThat(usuarioEmpresa.getPessoa()).isNull();
    }

    @Test
    void empresaTest() {
        UsuarioEmpresa usuarioEmpresa = getUsuarioEmpresaRandomSampleGenerator();
        Empresa empresaBack = getEmpresaRandomSampleGenerator();

        usuarioEmpresa.setEmpresa(empresaBack);
        assertThat(usuarioEmpresa.getEmpresa()).isEqualTo(empresaBack);

        usuarioEmpresa.empresa(null);
        assertThat(usuarioEmpresa.getEmpresa()).isNull();
    }
}
