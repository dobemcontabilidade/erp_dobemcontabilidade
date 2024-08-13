package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AnexoEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.AnexoRequeridoEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.AssinaturaEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.AtividadeEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.CertificadoDigitalTestSamples.*;
import static com.dobemcontabilidade.domain.DepartamentoEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.EmpresaModeloTestSamples.*;
import static com.dobemcontabilidade.domain.EmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.EnderecoEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.FuncionarioTestSamples.*;
import static com.dobemcontabilidade.domain.ImpostoEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.OpcaoNomeFantasiaEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.OpcaoRazaoSocialEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.OrdemServicoTestSamples.*;
import static com.dobemcontabilidade.domain.ParcelamentoImpostoTestSamples.*;
import static com.dobemcontabilidade.domain.SegmentoCnaeTestSamples.*;
import static com.dobemcontabilidade.domain.SocioTestSamples.*;
import static com.dobemcontabilidade.domain.TarefaEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.TermoAdesaoEmpresaTestSamples.*;
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
    void ordemServicoTest() {
        Empresa empresa = getEmpresaRandomSampleGenerator();
        OrdemServico ordemServicoBack = getOrdemServicoRandomSampleGenerator();

        empresa.addOrdemServico(ordemServicoBack);
        assertThat(empresa.getOrdemServicos()).containsOnly(ordemServicoBack);
        assertThat(ordemServicoBack.getEmpresa()).isEqualTo(empresa);

        empresa.removeOrdemServico(ordemServicoBack);
        assertThat(empresa.getOrdemServicos()).doesNotContain(ordemServicoBack);
        assertThat(ordemServicoBack.getEmpresa()).isNull();

        empresa.ordemServicos(new HashSet<>(Set.of(ordemServicoBack)));
        assertThat(empresa.getOrdemServicos()).containsOnly(ordemServicoBack);
        assertThat(ordemServicoBack.getEmpresa()).isEqualTo(empresa);

        empresa.setOrdemServicos(new HashSet<>());
        assertThat(empresa.getOrdemServicos()).doesNotContain(ordemServicoBack);
        assertThat(ordemServicoBack.getEmpresa()).isNull();
    }

    @Test
    void anexoRequeridoEmpresaTest() {
        Empresa empresa = getEmpresaRandomSampleGenerator();
        AnexoRequeridoEmpresa anexoRequeridoEmpresaBack = getAnexoRequeridoEmpresaRandomSampleGenerator();

        empresa.addAnexoRequeridoEmpresa(anexoRequeridoEmpresaBack);
        assertThat(empresa.getAnexoRequeridoEmpresas()).containsOnly(anexoRequeridoEmpresaBack);
        assertThat(anexoRequeridoEmpresaBack.getEmpresa()).isEqualTo(empresa);

        empresa.removeAnexoRequeridoEmpresa(anexoRequeridoEmpresaBack);
        assertThat(empresa.getAnexoRequeridoEmpresas()).doesNotContain(anexoRequeridoEmpresaBack);
        assertThat(anexoRequeridoEmpresaBack.getEmpresa()).isNull();

        empresa.anexoRequeridoEmpresas(new HashSet<>(Set.of(anexoRequeridoEmpresaBack)));
        assertThat(empresa.getAnexoRequeridoEmpresas()).containsOnly(anexoRequeridoEmpresaBack);
        assertThat(anexoRequeridoEmpresaBack.getEmpresa()).isEqualTo(empresa);

        empresa.setAnexoRequeridoEmpresas(new HashSet<>());
        assertThat(empresa.getAnexoRequeridoEmpresas()).doesNotContain(anexoRequeridoEmpresaBack);
        assertThat(anexoRequeridoEmpresaBack.getEmpresa()).isNull();
    }

    @Test
    void impostoEmpresaTest() {
        Empresa empresa = getEmpresaRandomSampleGenerator();
        ImpostoEmpresa impostoEmpresaBack = getImpostoEmpresaRandomSampleGenerator();

        empresa.addImpostoEmpresa(impostoEmpresaBack);
        assertThat(empresa.getImpostoEmpresas()).containsOnly(impostoEmpresaBack);
        assertThat(impostoEmpresaBack.getEmpresa()).isEqualTo(empresa);

        empresa.removeImpostoEmpresa(impostoEmpresaBack);
        assertThat(empresa.getImpostoEmpresas()).doesNotContain(impostoEmpresaBack);
        assertThat(impostoEmpresaBack.getEmpresa()).isNull();

        empresa.impostoEmpresas(new HashSet<>(Set.of(impostoEmpresaBack)));
        assertThat(empresa.getImpostoEmpresas()).containsOnly(impostoEmpresaBack);
        assertThat(impostoEmpresaBack.getEmpresa()).isEqualTo(empresa);

        empresa.setImpostoEmpresas(new HashSet<>());
        assertThat(empresa.getImpostoEmpresas()).doesNotContain(impostoEmpresaBack);
        assertThat(impostoEmpresaBack.getEmpresa()).isNull();
    }

    @Test
    void parcelamentoImpostoTest() {
        Empresa empresa = getEmpresaRandomSampleGenerator();
        ParcelamentoImposto parcelamentoImpostoBack = getParcelamentoImpostoRandomSampleGenerator();

        empresa.addParcelamentoImposto(parcelamentoImpostoBack);
        assertThat(empresa.getParcelamentoImpostos()).containsOnly(parcelamentoImpostoBack);
        assertThat(parcelamentoImpostoBack.getEmpresa()).isEqualTo(empresa);

        empresa.removeParcelamentoImposto(parcelamentoImpostoBack);
        assertThat(empresa.getParcelamentoImpostos()).doesNotContain(parcelamentoImpostoBack);
        assertThat(parcelamentoImpostoBack.getEmpresa()).isNull();

        empresa.parcelamentoImpostos(new HashSet<>(Set.of(parcelamentoImpostoBack)));
        assertThat(empresa.getParcelamentoImpostos()).containsOnly(parcelamentoImpostoBack);
        assertThat(parcelamentoImpostoBack.getEmpresa()).isEqualTo(empresa);

        empresa.setParcelamentoImpostos(new HashSet<>());
        assertThat(empresa.getParcelamentoImpostos()).doesNotContain(parcelamentoImpostoBack);
        assertThat(parcelamentoImpostoBack.getEmpresa()).isNull();
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
    void termoAdesaoEmpresaTest() {
        Empresa empresa = getEmpresaRandomSampleGenerator();
        TermoAdesaoEmpresa termoAdesaoEmpresaBack = getTermoAdesaoEmpresaRandomSampleGenerator();

        empresa.addTermoAdesaoEmpresa(termoAdesaoEmpresaBack);
        assertThat(empresa.getTermoAdesaoEmpresas()).containsOnly(termoAdesaoEmpresaBack);
        assertThat(termoAdesaoEmpresaBack.getEmpresa()).isEqualTo(empresa);

        empresa.removeTermoAdesaoEmpresa(termoAdesaoEmpresaBack);
        assertThat(empresa.getTermoAdesaoEmpresas()).doesNotContain(termoAdesaoEmpresaBack);
        assertThat(termoAdesaoEmpresaBack.getEmpresa()).isNull();

        empresa.termoAdesaoEmpresas(new HashSet<>(Set.of(termoAdesaoEmpresaBack)));
        assertThat(empresa.getTermoAdesaoEmpresas()).containsOnly(termoAdesaoEmpresaBack);
        assertThat(termoAdesaoEmpresaBack.getEmpresa()).isEqualTo(empresa);

        empresa.setTermoAdesaoEmpresas(new HashSet<>());
        assertThat(empresa.getTermoAdesaoEmpresas()).doesNotContain(termoAdesaoEmpresaBack);
        assertThat(termoAdesaoEmpresaBack.getEmpresa()).isNull();
    }

    @Test
    void segmentoCnaeTest() {
        Empresa empresa = getEmpresaRandomSampleGenerator();
        SegmentoCnae segmentoCnaeBack = getSegmentoCnaeRandomSampleGenerator();

        empresa.addSegmentoCnae(segmentoCnaeBack);
        assertThat(empresa.getSegmentoCnaes()).containsOnly(segmentoCnaeBack);

        empresa.removeSegmentoCnae(segmentoCnaeBack);
        assertThat(empresa.getSegmentoCnaes()).doesNotContain(segmentoCnaeBack);

        empresa.segmentoCnaes(new HashSet<>(Set.of(segmentoCnaeBack)));
        assertThat(empresa.getSegmentoCnaes()).containsOnly(segmentoCnaeBack);

        empresa.setSegmentoCnaes(new HashSet<>());
        assertThat(empresa.getSegmentoCnaes()).doesNotContain(segmentoCnaeBack);
    }

    @Test
    void empresaModeloTest() {
        Empresa empresa = getEmpresaRandomSampleGenerator();
        EmpresaModelo empresaModeloBack = getEmpresaModeloRandomSampleGenerator();

        empresa.setEmpresaModelo(empresaModeloBack);
        assertThat(empresa.getEmpresaModelo()).isEqualTo(empresaModeloBack);

        empresa.empresaModelo(null);
        assertThat(empresa.getEmpresaModelo()).isNull();
    }
}
