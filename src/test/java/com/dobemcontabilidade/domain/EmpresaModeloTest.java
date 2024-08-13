package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AnexoRequeridoEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.CidadeTestSamples.*;
import static com.dobemcontabilidade.domain.EmpresaModeloTestSamples.*;
import static com.dobemcontabilidade.domain.EmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.EnquadramentoTestSamples.*;
import static com.dobemcontabilidade.domain.ImpostoEmpresaModeloTestSamples.*;
import static com.dobemcontabilidade.domain.RamoTestSamples.*;
import static com.dobemcontabilidade.domain.SegmentoCnaeTestSamples.*;
import static com.dobemcontabilidade.domain.ServicoContabilEmpresaModeloTestSamples.*;
import static com.dobemcontabilidade.domain.TarefaEmpresaModeloTestSamples.*;
import static com.dobemcontabilidade.domain.TributacaoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EmpresaModeloTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmpresaModelo.class);
        EmpresaModelo empresaModelo1 = getEmpresaModeloSample1();
        EmpresaModelo empresaModelo2 = new EmpresaModelo();
        assertThat(empresaModelo1).isNotEqualTo(empresaModelo2);

        empresaModelo2.setId(empresaModelo1.getId());
        assertThat(empresaModelo1).isEqualTo(empresaModelo2);

        empresaModelo2 = getEmpresaModeloSample2();
        assertThat(empresaModelo1).isNotEqualTo(empresaModelo2);
    }

    @Test
    void servicoContabilEmpresaModeloTest() {
        EmpresaModelo empresaModelo = getEmpresaModeloRandomSampleGenerator();
        ServicoContabilEmpresaModelo servicoContabilEmpresaModeloBack = getServicoContabilEmpresaModeloRandomSampleGenerator();

        empresaModelo.addServicoContabilEmpresaModelo(servicoContabilEmpresaModeloBack);
        assertThat(empresaModelo.getServicoContabilEmpresaModelos()).containsOnly(servicoContabilEmpresaModeloBack);
        assertThat(servicoContabilEmpresaModeloBack.getEmpresaModelo()).isEqualTo(empresaModelo);

        empresaModelo.removeServicoContabilEmpresaModelo(servicoContabilEmpresaModeloBack);
        assertThat(empresaModelo.getServicoContabilEmpresaModelos()).doesNotContain(servicoContabilEmpresaModeloBack);
        assertThat(servicoContabilEmpresaModeloBack.getEmpresaModelo()).isNull();

        empresaModelo.servicoContabilEmpresaModelos(new HashSet<>(Set.of(servicoContabilEmpresaModeloBack)));
        assertThat(empresaModelo.getServicoContabilEmpresaModelos()).containsOnly(servicoContabilEmpresaModeloBack);
        assertThat(servicoContabilEmpresaModeloBack.getEmpresaModelo()).isEqualTo(empresaModelo);

        empresaModelo.setServicoContabilEmpresaModelos(new HashSet<>());
        assertThat(empresaModelo.getServicoContabilEmpresaModelos()).doesNotContain(servicoContabilEmpresaModeloBack);
        assertThat(servicoContabilEmpresaModeloBack.getEmpresaModelo()).isNull();
    }

    @Test
    void impostoEmpresaModeloTest() {
        EmpresaModelo empresaModelo = getEmpresaModeloRandomSampleGenerator();
        ImpostoEmpresaModelo impostoEmpresaModeloBack = getImpostoEmpresaModeloRandomSampleGenerator();

        empresaModelo.addImpostoEmpresaModelo(impostoEmpresaModeloBack);
        assertThat(empresaModelo.getImpostoEmpresaModelos()).containsOnly(impostoEmpresaModeloBack);
        assertThat(impostoEmpresaModeloBack.getEmpresaModelo()).isEqualTo(empresaModelo);

        empresaModelo.removeImpostoEmpresaModelo(impostoEmpresaModeloBack);
        assertThat(empresaModelo.getImpostoEmpresaModelos()).doesNotContain(impostoEmpresaModeloBack);
        assertThat(impostoEmpresaModeloBack.getEmpresaModelo()).isNull();

        empresaModelo.impostoEmpresaModelos(new HashSet<>(Set.of(impostoEmpresaModeloBack)));
        assertThat(empresaModelo.getImpostoEmpresaModelos()).containsOnly(impostoEmpresaModeloBack);
        assertThat(impostoEmpresaModeloBack.getEmpresaModelo()).isEqualTo(empresaModelo);

        empresaModelo.setImpostoEmpresaModelos(new HashSet<>());
        assertThat(empresaModelo.getImpostoEmpresaModelos()).doesNotContain(impostoEmpresaModeloBack);
        assertThat(impostoEmpresaModeloBack.getEmpresaModelo()).isNull();
    }

    @Test
    void anexoRequeridoEmpresaTest() {
        EmpresaModelo empresaModelo = getEmpresaModeloRandomSampleGenerator();
        AnexoRequeridoEmpresa anexoRequeridoEmpresaBack = getAnexoRequeridoEmpresaRandomSampleGenerator();

        empresaModelo.addAnexoRequeridoEmpresa(anexoRequeridoEmpresaBack);
        assertThat(empresaModelo.getAnexoRequeridoEmpresas()).containsOnly(anexoRequeridoEmpresaBack);
        assertThat(anexoRequeridoEmpresaBack.getEmpresaModelo()).isEqualTo(empresaModelo);

        empresaModelo.removeAnexoRequeridoEmpresa(anexoRequeridoEmpresaBack);
        assertThat(empresaModelo.getAnexoRequeridoEmpresas()).doesNotContain(anexoRequeridoEmpresaBack);
        assertThat(anexoRequeridoEmpresaBack.getEmpresaModelo()).isNull();

        empresaModelo.anexoRequeridoEmpresas(new HashSet<>(Set.of(anexoRequeridoEmpresaBack)));
        assertThat(empresaModelo.getAnexoRequeridoEmpresas()).containsOnly(anexoRequeridoEmpresaBack);
        assertThat(anexoRequeridoEmpresaBack.getEmpresaModelo()).isEqualTo(empresaModelo);

        empresaModelo.setAnexoRequeridoEmpresas(new HashSet<>());
        assertThat(empresaModelo.getAnexoRequeridoEmpresas()).doesNotContain(anexoRequeridoEmpresaBack);
        assertThat(anexoRequeridoEmpresaBack.getEmpresaModelo()).isNull();
    }

    @Test
    void tarefaEmpresaModeloTest() {
        EmpresaModelo empresaModelo = getEmpresaModeloRandomSampleGenerator();
        TarefaEmpresaModelo tarefaEmpresaModeloBack = getTarefaEmpresaModeloRandomSampleGenerator();

        empresaModelo.addTarefaEmpresaModelo(tarefaEmpresaModeloBack);
        assertThat(empresaModelo.getTarefaEmpresaModelos()).containsOnly(tarefaEmpresaModeloBack);
        assertThat(tarefaEmpresaModeloBack.getEmpresaModelo()).isEqualTo(empresaModelo);

        empresaModelo.removeTarefaEmpresaModelo(tarefaEmpresaModeloBack);
        assertThat(empresaModelo.getTarefaEmpresaModelos()).doesNotContain(tarefaEmpresaModeloBack);
        assertThat(tarefaEmpresaModeloBack.getEmpresaModelo()).isNull();

        empresaModelo.tarefaEmpresaModelos(new HashSet<>(Set.of(tarefaEmpresaModeloBack)));
        assertThat(empresaModelo.getTarefaEmpresaModelos()).containsOnly(tarefaEmpresaModeloBack);
        assertThat(tarefaEmpresaModeloBack.getEmpresaModelo()).isEqualTo(empresaModelo);

        empresaModelo.setTarefaEmpresaModelos(new HashSet<>());
        assertThat(empresaModelo.getTarefaEmpresaModelos()).doesNotContain(tarefaEmpresaModeloBack);
        assertThat(tarefaEmpresaModeloBack.getEmpresaModelo()).isNull();
    }

    @Test
    void empresaTest() {
        EmpresaModelo empresaModelo = getEmpresaModeloRandomSampleGenerator();
        Empresa empresaBack = getEmpresaRandomSampleGenerator();

        empresaModelo.addEmpresa(empresaBack);
        assertThat(empresaModelo.getEmpresas()).containsOnly(empresaBack);
        assertThat(empresaBack.getEmpresaModelo()).isEqualTo(empresaModelo);

        empresaModelo.removeEmpresa(empresaBack);
        assertThat(empresaModelo.getEmpresas()).doesNotContain(empresaBack);
        assertThat(empresaBack.getEmpresaModelo()).isNull();

        empresaModelo.empresas(new HashSet<>(Set.of(empresaBack)));
        assertThat(empresaModelo.getEmpresas()).containsOnly(empresaBack);
        assertThat(empresaBack.getEmpresaModelo()).isEqualTo(empresaModelo);

        empresaModelo.setEmpresas(new HashSet<>());
        assertThat(empresaModelo.getEmpresas()).doesNotContain(empresaBack);
        assertThat(empresaBack.getEmpresaModelo()).isNull();
    }

    @Test
    void segmentoCnaeTest() {
        EmpresaModelo empresaModelo = getEmpresaModeloRandomSampleGenerator();
        SegmentoCnae segmentoCnaeBack = getSegmentoCnaeRandomSampleGenerator();

        empresaModelo.addSegmentoCnae(segmentoCnaeBack);
        assertThat(empresaModelo.getSegmentoCnaes()).containsOnly(segmentoCnaeBack);

        empresaModelo.removeSegmentoCnae(segmentoCnaeBack);
        assertThat(empresaModelo.getSegmentoCnaes()).doesNotContain(segmentoCnaeBack);

        empresaModelo.segmentoCnaes(new HashSet<>(Set.of(segmentoCnaeBack)));
        assertThat(empresaModelo.getSegmentoCnaes()).containsOnly(segmentoCnaeBack);

        empresaModelo.setSegmentoCnaes(new HashSet<>());
        assertThat(empresaModelo.getSegmentoCnaes()).doesNotContain(segmentoCnaeBack);
    }

    @Test
    void ramoTest() {
        EmpresaModelo empresaModelo = getEmpresaModeloRandomSampleGenerator();
        Ramo ramoBack = getRamoRandomSampleGenerator();

        empresaModelo.setRamo(ramoBack);
        assertThat(empresaModelo.getRamo()).isEqualTo(ramoBack);

        empresaModelo.ramo(null);
        assertThat(empresaModelo.getRamo()).isNull();
    }

    @Test
    void enquadramentoTest() {
        EmpresaModelo empresaModelo = getEmpresaModeloRandomSampleGenerator();
        Enquadramento enquadramentoBack = getEnquadramentoRandomSampleGenerator();

        empresaModelo.setEnquadramento(enquadramentoBack);
        assertThat(empresaModelo.getEnquadramento()).isEqualTo(enquadramentoBack);

        empresaModelo.enquadramento(null);
        assertThat(empresaModelo.getEnquadramento()).isNull();
    }

    @Test
    void tributacaoTest() {
        EmpresaModelo empresaModelo = getEmpresaModeloRandomSampleGenerator();
        Tributacao tributacaoBack = getTributacaoRandomSampleGenerator();

        empresaModelo.setTributacao(tributacaoBack);
        assertThat(empresaModelo.getTributacao()).isEqualTo(tributacaoBack);

        empresaModelo.tributacao(null);
        assertThat(empresaModelo.getTributacao()).isNull();
    }

    @Test
    void cidadeTest() {
        EmpresaModelo empresaModelo = getEmpresaModeloRandomSampleGenerator();
        Cidade cidadeBack = getCidadeRandomSampleGenerator();

        empresaModelo.setCidade(cidadeBack);
        assertThat(empresaModelo.getCidade()).isEqualTo(cidadeBack);

        empresaModelo.cidade(null);
        assertThat(empresaModelo.getCidade()).isNull();
    }
}
