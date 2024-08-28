package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AssinaturaEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.EmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.EnquadramentoTestSamples.*;
import static com.dobemcontabilidade.domain.PessoajuridicaTestSamples.*;
import static com.dobemcontabilidade.domain.RamoTestSamples.*;
import static com.dobemcontabilidade.domain.SocioTestSamples.*;
import static com.dobemcontabilidade.domain.TributacaoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EmpresaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Empresa.class);
        Empresa empresa1 = getEmpresaSample1();
        Empresa empresa2 = new Empresa();
        assertThat(empresa1).isNotEqualTo(empresa2);

        empresa2.setId(empresa1.getId());
        assertThat(empresa1).isEqualTo(empresa2);

        empresa2 = getEmpresaSample2();
        assertThat(empresa1).isNotEqualTo(empresa2);
    }

    @Test
    void pessoaJuridicaTest() {
        Empresa empresa = getEmpresaRandomSampleGenerator();
        Pessoajuridica pessoajuridicaBack = getPessoajuridicaRandomSampleGenerator();

        empresa.setPessoaJuridica(pessoajuridicaBack);
        assertThat(empresa.getPessoaJuridica()).isEqualTo(pessoajuridicaBack);

        empresa.pessoaJuridica(null);
        assertThat(empresa.getPessoaJuridica()).isNull();
    }

    @Test
    void socioTest() {
        Empresa empresa = getEmpresaRandomSampleGenerator();
        Socio socioBack = getSocioRandomSampleGenerator();

        empresa.addSocio(socioBack);
        assertThat(empresa.getSocios()).containsOnly(socioBack);
        assertThat(socioBack.getEmpresa()).isEqualTo(empresa);

        empresa.removeSocio(socioBack);
        assertThat(empresa.getSocios()).doesNotContain(socioBack);
        assertThat(socioBack.getEmpresa()).isNull();

        empresa.socios(new HashSet<>(Set.of(socioBack)));
        assertThat(empresa.getSocios()).containsOnly(socioBack);
        assertThat(socioBack.getEmpresa()).isEqualTo(empresa);

        empresa.setSocios(new HashSet<>());
        assertThat(empresa.getSocios()).doesNotContain(socioBack);
        assertThat(socioBack.getEmpresa()).isNull();
    }

    @Test
    void assinaturaEmpresaTest() {
        Empresa empresa = getEmpresaRandomSampleGenerator();
        AssinaturaEmpresa assinaturaEmpresaBack = getAssinaturaEmpresaRandomSampleGenerator();

        empresa.addAssinaturaEmpresa(assinaturaEmpresaBack);
        assertThat(empresa.getAssinaturaEmpresas()).containsOnly(assinaturaEmpresaBack);
        assertThat(assinaturaEmpresaBack.getEmpresa()).isEqualTo(empresa);

        empresa.removeAssinaturaEmpresa(assinaturaEmpresaBack);
        assertThat(empresa.getAssinaturaEmpresas()).doesNotContain(assinaturaEmpresaBack);
        assertThat(assinaturaEmpresaBack.getEmpresa()).isNull();

        empresa.assinaturaEmpresas(new HashSet<>(Set.of(assinaturaEmpresaBack)));
        assertThat(empresa.getAssinaturaEmpresas()).containsOnly(assinaturaEmpresaBack);
        assertThat(assinaturaEmpresaBack.getEmpresa()).isEqualTo(empresa);

        empresa.setAssinaturaEmpresas(new HashSet<>());
        assertThat(empresa.getAssinaturaEmpresas()).doesNotContain(assinaturaEmpresaBack);
        assertThat(assinaturaEmpresaBack.getEmpresa()).isNull();
    }

    @Test
    void empresaTest() {
        Empresa empresa = getEmpresaRandomSampleGenerator();
        Tributacao tributacaoBack = getTributacaoRandomSampleGenerator();

        empresa.setEmpresa(tributacaoBack);
        assertThat(empresa.getEmpresa()).isEqualTo(tributacaoBack);

        empresa.empresa(null);
        assertThat(empresa.getEmpresa()).isNull();
    }

    @Test
    void ramoTest() {
        Empresa empresa = getEmpresaRandomSampleGenerator();
        Ramo ramoBack = getRamoRandomSampleGenerator();

        empresa.setRamo(ramoBack);
        assertThat(empresa.getRamo()).isEqualTo(ramoBack);

        empresa.ramo(null);
        assertThat(empresa.getRamo()).isNull();
    }

    @Test
    void enquadramentoTest() {
        Empresa empresa = getEmpresaRandomSampleGenerator();
        Enquadramento enquadramentoBack = getEnquadramentoRandomSampleGenerator();

        empresa.setEnquadramento(enquadramentoBack);
        assertThat(empresa.getEnquadramento()).isEqualTo(enquadramentoBack);

        empresa.enquadramento(null);
        assertThat(empresa.getEnquadramento()).isNull();
    }
}
