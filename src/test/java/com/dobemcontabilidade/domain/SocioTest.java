package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.EmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.PessoaTestSamples.*;
import static com.dobemcontabilidade.domain.ProfissaoTestSamples.*;
import static com.dobemcontabilidade.domain.SocioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
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
    void profissaoTest() {
        Socio socio = getSocioRandomSampleGenerator();
        Profissao profissaoBack = getProfissaoRandomSampleGenerator();

        socio.addProfissao(profissaoBack);
        assertThat(socio.getProfissaos()).containsOnly(profissaoBack);
        assertThat(profissaoBack.getSocio()).isEqualTo(socio);

        socio.removeProfissao(profissaoBack);
        assertThat(socio.getProfissaos()).doesNotContain(profissaoBack);
        assertThat(profissaoBack.getSocio()).isNull();

        socio.profissaos(new HashSet<>(Set.of(profissaoBack)));
        assertThat(socio.getProfissaos()).containsOnly(profissaoBack);
        assertThat(profissaoBack.getSocio()).isEqualTo(socio);

        socio.setProfissaos(new HashSet<>());
        assertThat(socio.getProfissaos()).doesNotContain(profissaoBack);
        assertThat(profissaoBack.getSocio()).isNull();
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
