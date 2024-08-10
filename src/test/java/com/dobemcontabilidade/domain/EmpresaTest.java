package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AnexoEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.AssinaturaEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.AtividadeEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.CertificadoDigitalTestSamples.*;
import static com.dobemcontabilidade.domain.DepartamentoEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.EmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.EnderecoEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.EnquadramentoTestSamples.*;
import static com.dobemcontabilidade.domain.FuncionarioTestSamples.*;
import static com.dobemcontabilidade.domain.OpcaoNomeFantasiaEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.OpcaoRazaoSocialEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.RamoTestSamples.*;
import static com.dobemcontabilidade.domain.SocioTestSamples.*;
import static com.dobemcontabilidade.domain.TarefaEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.TributacaoTestSamples.*;
import static com.dobemcontabilidade.domain.UsuarioEmpresaTestSamples.*;
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
    void funcionarioTest() {
        Empresa empresa = getEmpresaRandomSampleGenerator();
        Funcionario funcionarioBack = getFuncionarioRandomSampleGenerator();

        empresa.addFuncionario(funcionarioBack);
        assertThat(empresa.getFuncionarios()).containsOnly(funcionarioBack);
        assertThat(funcionarioBack.getEmpresa()).isEqualTo(empresa);

        empresa.removeFuncionario(funcionarioBack);
        assertThat(empresa.getFuncionarios()).doesNotContain(funcionarioBack);
        assertThat(funcionarioBack.getEmpresa()).isNull();

        empresa.funcionarios(new HashSet<>(Set.of(funcionarioBack)));
        assertThat(empresa.getFuncionarios()).containsOnly(funcionarioBack);
        assertThat(funcionarioBack.getEmpresa()).isEqualTo(empresa);

        empresa.setFuncionarios(new HashSet<>());
        assertThat(empresa.getFuncionarios()).doesNotContain(funcionarioBack);
        assertThat(funcionarioBack.getEmpresa()).isNull();
    }

    @Test
    void departamentoEmpresaTest() {
        Empresa empresa = getEmpresaRandomSampleGenerator();
        DepartamentoEmpresa departamentoEmpresaBack = getDepartamentoEmpresaRandomSampleGenerator();

        empresa.addDepartamentoEmpresa(departamentoEmpresaBack);
        assertThat(empresa.getDepartamentoEmpresas()).containsOnly(departamentoEmpresaBack);
        assertThat(departamentoEmpresaBack.getEmpresa()).isEqualTo(empresa);

        empresa.removeDepartamentoEmpresa(departamentoEmpresaBack);
        assertThat(empresa.getDepartamentoEmpresas()).doesNotContain(departamentoEmpresaBack);
        assertThat(departamentoEmpresaBack.getEmpresa()).isNull();

        empresa.departamentoEmpresas(new HashSet<>(Set.of(departamentoEmpresaBack)));
        assertThat(empresa.getDepartamentoEmpresas()).containsOnly(departamentoEmpresaBack);
        assertThat(departamentoEmpresaBack.getEmpresa()).isEqualTo(empresa);

        empresa.setDepartamentoEmpresas(new HashSet<>());
        assertThat(empresa.getDepartamentoEmpresas()).doesNotContain(departamentoEmpresaBack);
        assertThat(departamentoEmpresaBack.getEmpresa()).isNull();
    }

    @Test
    void tarefaEmpresaTest() {
        Empresa empresa = getEmpresaRandomSampleGenerator();
        TarefaEmpresa tarefaEmpresaBack = getTarefaEmpresaRandomSampleGenerator();

        empresa.addTarefaEmpresa(tarefaEmpresaBack);
        assertThat(empresa.getTarefaEmpresas()).containsOnly(tarefaEmpresaBack);
        assertThat(tarefaEmpresaBack.getEmpresa()).isEqualTo(empresa);

        empresa.removeTarefaEmpresa(tarefaEmpresaBack);
        assertThat(empresa.getTarefaEmpresas()).doesNotContain(tarefaEmpresaBack);
        assertThat(tarefaEmpresaBack.getEmpresa()).isNull();

        empresa.tarefaEmpresas(new HashSet<>(Set.of(tarefaEmpresaBack)));
        assertThat(empresa.getTarefaEmpresas()).containsOnly(tarefaEmpresaBack);
        assertThat(tarefaEmpresaBack.getEmpresa()).isEqualTo(empresa);

        empresa.setTarefaEmpresas(new HashSet<>());
        assertThat(empresa.getTarefaEmpresas()).doesNotContain(tarefaEmpresaBack);
        assertThat(tarefaEmpresaBack.getEmpresa()).isNull();
    }

    @Test
    void enderecoEmpresaTest() {
        Empresa empresa = getEmpresaRandomSampleGenerator();
        EnderecoEmpresa enderecoEmpresaBack = getEnderecoEmpresaRandomSampleGenerator();

        empresa.addEnderecoEmpresa(enderecoEmpresaBack);
        assertThat(empresa.getEnderecoEmpresas()).containsOnly(enderecoEmpresaBack);
        assertThat(enderecoEmpresaBack.getEmpresa()).isEqualTo(empresa);

        empresa.removeEnderecoEmpresa(enderecoEmpresaBack);
        assertThat(empresa.getEnderecoEmpresas()).doesNotContain(enderecoEmpresaBack);
        assertThat(enderecoEmpresaBack.getEmpresa()).isNull();

        empresa.enderecoEmpresas(new HashSet<>(Set.of(enderecoEmpresaBack)));
        assertThat(empresa.getEnderecoEmpresas()).containsOnly(enderecoEmpresaBack);
        assertThat(enderecoEmpresaBack.getEmpresa()).isEqualTo(empresa);

        empresa.setEnderecoEmpresas(new HashSet<>());
        assertThat(empresa.getEnderecoEmpresas()).doesNotContain(enderecoEmpresaBack);
        assertThat(enderecoEmpresaBack.getEmpresa()).isNull();
    }

    @Test
    void atividadeEmpresaTest() {
        Empresa empresa = getEmpresaRandomSampleGenerator();
        AtividadeEmpresa atividadeEmpresaBack = getAtividadeEmpresaRandomSampleGenerator();

        empresa.addAtividadeEmpresa(atividadeEmpresaBack);
        assertThat(empresa.getAtividadeEmpresas()).containsOnly(atividadeEmpresaBack);
        assertThat(atividadeEmpresaBack.getEmpresa()).isEqualTo(empresa);

        empresa.removeAtividadeEmpresa(atividadeEmpresaBack);
        assertThat(empresa.getAtividadeEmpresas()).doesNotContain(atividadeEmpresaBack);
        assertThat(atividadeEmpresaBack.getEmpresa()).isNull();

        empresa.atividadeEmpresas(new HashSet<>(Set.of(atividadeEmpresaBack)));
        assertThat(empresa.getAtividadeEmpresas()).containsOnly(atividadeEmpresaBack);
        assertThat(atividadeEmpresaBack.getEmpresa()).isEqualTo(empresa);

        empresa.setAtividadeEmpresas(new HashSet<>());
        assertThat(empresa.getAtividadeEmpresas()).doesNotContain(atividadeEmpresaBack);
        assertThat(atividadeEmpresaBack.getEmpresa()).isNull();
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
    void anexoEmpresaTest() {
        Empresa empresa = getEmpresaRandomSampleGenerator();
        AnexoEmpresa anexoEmpresaBack = getAnexoEmpresaRandomSampleGenerator();

        empresa.addAnexoEmpresa(anexoEmpresaBack);
        assertThat(empresa.getAnexoEmpresas()).containsOnly(anexoEmpresaBack);
        assertThat(anexoEmpresaBack.getEmpresa()).isEqualTo(empresa);

        empresa.removeAnexoEmpresa(anexoEmpresaBack);
        assertThat(empresa.getAnexoEmpresas()).doesNotContain(anexoEmpresaBack);
        assertThat(anexoEmpresaBack.getEmpresa()).isNull();

        empresa.anexoEmpresas(new HashSet<>(Set.of(anexoEmpresaBack)));
        assertThat(empresa.getAnexoEmpresas()).containsOnly(anexoEmpresaBack);
        assertThat(anexoEmpresaBack.getEmpresa()).isEqualTo(empresa);

        empresa.setAnexoEmpresas(new HashSet<>());
        assertThat(empresa.getAnexoEmpresas()).doesNotContain(anexoEmpresaBack);
        assertThat(anexoEmpresaBack.getEmpresa()).isNull();
    }

    @Test
    void certificadoDigitalTest() {
        Empresa empresa = getEmpresaRandomSampleGenerator();
        CertificadoDigital certificadoDigitalBack = getCertificadoDigitalRandomSampleGenerator();

        empresa.addCertificadoDigital(certificadoDigitalBack);
        assertThat(empresa.getCertificadoDigitals()).containsOnly(certificadoDigitalBack);
        assertThat(certificadoDigitalBack.getEmpresa()).isEqualTo(empresa);

        empresa.removeCertificadoDigital(certificadoDigitalBack);
        assertThat(empresa.getCertificadoDigitals()).doesNotContain(certificadoDigitalBack);
        assertThat(certificadoDigitalBack.getEmpresa()).isNull();

        empresa.certificadoDigitals(new HashSet<>(Set.of(certificadoDigitalBack)));
        assertThat(empresa.getCertificadoDigitals()).containsOnly(certificadoDigitalBack);
        assertThat(certificadoDigitalBack.getEmpresa()).isEqualTo(empresa);

        empresa.setCertificadoDigitals(new HashSet<>());
        assertThat(empresa.getCertificadoDigitals()).doesNotContain(certificadoDigitalBack);
        assertThat(certificadoDigitalBack.getEmpresa()).isNull();
    }

    @Test
    void usuarioEmpresaTest() {
        Empresa empresa = getEmpresaRandomSampleGenerator();
        UsuarioEmpresa usuarioEmpresaBack = getUsuarioEmpresaRandomSampleGenerator();

        empresa.addUsuarioEmpresa(usuarioEmpresaBack);
        assertThat(empresa.getUsuarioEmpresas()).containsOnly(usuarioEmpresaBack);
        assertThat(usuarioEmpresaBack.getEmpresa()).isEqualTo(empresa);

        empresa.removeUsuarioEmpresa(usuarioEmpresaBack);
        assertThat(empresa.getUsuarioEmpresas()).doesNotContain(usuarioEmpresaBack);
        assertThat(usuarioEmpresaBack.getEmpresa()).isNull();

        empresa.usuarioEmpresas(new HashSet<>(Set.of(usuarioEmpresaBack)));
        assertThat(empresa.getUsuarioEmpresas()).containsOnly(usuarioEmpresaBack);
        assertThat(usuarioEmpresaBack.getEmpresa()).isEqualTo(empresa);

        empresa.setUsuarioEmpresas(new HashSet<>());
        assertThat(empresa.getUsuarioEmpresas()).doesNotContain(usuarioEmpresaBack);
        assertThat(usuarioEmpresaBack.getEmpresa()).isNull();
    }

    @Test
    void opcaoRazaoSocialEmpresaTest() {
        Empresa empresa = getEmpresaRandomSampleGenerator();
        OpcaoRazaoSocialEmpresa opcaoRazaoSocialEmpresaBack = getOpcaoRazaoSocialEmpresaRandomSampleGenerator();

        empresa.addOpcaoRazaoSocialEmpresa(opcaoRazaoSocialEmpresaBack);
        assertThat(empresa.getOpcaoRazaoSocialEmpresas()).containsOnly(opcaoRazaoSocialEmpresaBack);
        assertThat(opcaoRazaoSocialEmpresaBack.getEmpresa()).isEqualTo(empresa);

        empresa.removeOpcaoRazaoSocialEmpresa(opcaoRazaoSocialEmpresaBack);
        assertThat(empresa.getOpcaoRazaoSocialEmpresas()).doesNotContain(opcaoRazaoSocialEmpresaBack);
        assertThat(opcaoRazaoSocialEmpresaBack.getEmpresa()).isNull();

        empresa.opcaoRazaoSocialEmpresas(new HashSet<>(Set.of(opcaoRazaoSocialEmpresaBack)));
        assertThat(empresa.getOpcaoRazaoSocialEmpresas()).containsOnly(opcaoRazaoSocialEmpresaBack);
        assertThat(opcaoRazaoSocialEmpresaBack.getEmpresa()).isEqualTo(empresa);

        empresa.setOpcaoRazaoSocialEmpresas(new HashSet<>());
        assertThat(empresa.getOpcaoRazaoSocialEmpresas()).doesNotContain(opcaoRazaoSocialEmpresaBack);
        assertThat(opcaoRazaoSocialEmpresaBack.getEmpresa()).isNull();
    }

    @Test
    void opcaoNomeFantasiaEmpresaTest() {
        Empresa empresa = getEmpresaRandomSampleGenerator();
        OpcaoNomeFantasiaEmpresa opcaoNomeFantasiaEmpresaBack = getOpcaoNomeFantasiaEmpresaRandomSampleGenerator();

        empresa.addOpcaoNomeFantasiaEmpresa(opcaoNomeFantasiaEmpresaBack);
        assertThat(empresa.getOpcaoNomeFantasiaEmpresas()).containsOnly(opcaoNomeFantasiaEmpresaBack);
        assertThat(opcaoNomeFantasiaEmpresaBack.getEmpresa()).isEqualTo(empresa);

        empresa.removeOpcaoNomeFantasiaEmpresa(opcaoNomeFantasiaEmpresaBack);
        assertThat(empresa.getOpcaoNomeFantasiaEmpresas()).doesNotContain(opcaoNomeFantasiaEmpresaBack);
        assertThat(opcaoNomeFantasiaEmpresaBack.getEmpresa()).isNull();

        empresa.opcaoNomeFantasiaEmpresas(new HashSet<>(Set.of(opcaoNomeFantasiaEmpresaBack)));
        assertThat(empresa.getOpcaoNomeFantasiaEmpresas()).containsOnly(opcaoNomeFantasiaEmpresaBack);
        assertThat(opcaoNomeFantasiaEmpresaBack.getEmpresa()).isEqualTo(empresa);

        empresa.setOpcaoNomeFantasiaEmpresas(new HashSet<>());
        assertThat(empresa.getOpcaoNomeFantasiaEmpresas()).doesNotContain(opcaoNomeFantasiaEmpresaBack);
        assertThat(opcaoNomeFantasiaEmpresaBack.getEmpresa()).isNull();
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
    void tributacaoTest() {
        Empresa empresa = getEmpresaRandomSampleGenerator();
        Tributacao tributacaoBack = getTributacaoRandomSampleGenerator();

        empresa.setTributacao(tributacaoBack);
        assertThat(empresa.getTributacao()).isEqualTo(tributacaoBack);

        empresa.tributacao(null);
        assertThat(empresa.getTributacao()).isNull();
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
