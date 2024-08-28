package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.EmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.PessoaFisicaTestSamples.*;
import static com.dobemcontabilidade.domain.SocioTestSamples.*;
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
    void pessoaFisicaTest() {
        Socio socio = getSocioRandomSampleGenerator();
        PessoaFisica pessoaFisicaBack = getPessoaFisicaRandomSampleGenerator();

        socio.setPessoaFisica(pessoaFisicaBack);
        assertThat(socio.getPessoaFisica()).isEqualTo(pessoaFisicaBack);

        socio.pessoaFisica(null);
        assertThat(socio.getPessoaFisica()).isNull();
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
}
