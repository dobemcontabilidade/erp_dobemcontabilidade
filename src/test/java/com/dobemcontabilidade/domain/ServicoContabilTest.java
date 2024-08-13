package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AnexoRequeridoServicoContabilTestSamples.*;
import static com.dobemcontabilidade.domain.AreaContabilTestSamples.*;
import static com.dobemcontabilidade.domain.EsferaTestSamples.*;
import static com.dobemcontabilidade.domain.ServicoContabilAssinaturaEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.ServicoContabilEmpresaModeloTestSamples.*;
import static com.dobemcontabilidade.domain.ServicoContabilEtapaFluxoExecucaoTestSamples.*;
import static com.dobemcontabilidade.domain.ServicoContabilEtapaFluxoModeloTestSamples.*;
import static com.dobemcontabilidade.domain.ServicoContabilOrdemServicoTestSamples.*;
import static com.dobemcontabilidade.domain.ServicoContabilTestSamples.*;
import static com.dobemcontabilidade.domain.TarefaEmpresaModeloTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ServicoContabilTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServicoContabil.class);
        ServicoContabil servicoContabil1 = getServicoContabilSample1();
        ServicoContabil servicoContabil2 = new ServicoContabil();
        assertThat(servicoContabil1).isNotEqualTo(servicoContabil2);

        servicoContabil2.setId(servicoContabil1.getId());
        assertThat(servicoContabil1).isEqualTo(servicoContabil2);

        servicoContabil2 = getServicoContabilSample2();
        assertThat(servicoContabil1).isNotEqualTo(servicoContabil2);
    }

    @Test
    void servicoContabilEmpresaModeloTest() {
        ServicoContabil servicoContabil = getServicoContabilRandomSampleGenerator();
        ServicoContabilEmpresaModelo servicoContabilEmpresaModeloBack = getServicoContabilEmpresaModeloRandomSampleGenerator();

        servicoContabil.addServicoContabilEmpresaModelo(servicoContabilEmpresaModeloBack);
        assertThat(servicoContabil.getServicoContabilEmpresaModelos()).containsOnly(servicoContabilEmpresaModeloBack);
        assertThat(servicoContabilEmpresaModeloBack.getServicoContabil()).isEqualTo(servicoContabil);

        servicoContabil.removeServicoContabilEmpresaModelo(servicoContabilEmpresaModeloBack);
        assertThat(servicoContabil.getServicoContabilEmpresaModelos()).doesNotContain(servicoContabilEmpresaModeloBack);
        assertThat(servicoContabilEmpresaModeloBack.getServicoContabil()).isNull();

        servicoContabil.servicoContabilEmpresaModelos(new HashSet<>(Set.of(servicoContabilEmpresaModeloBack)));
        assertThat(servicoContabil.getServicoContabilEmpresaModelos()).containsOnly(servicoContabilEmpresaModeloBack);
        assertThat(servicoContabilEmpresaModeloBack.getServicoContabil()).isEqualTo(servicoContabil);

        servicoContabil.setServicoContabilEmpresaModelos(new HashSet<>());
        assertThat(servicoContabil.getServicoContabilEmpresaModelos()).doesNotContain(servicoContabilEmpresaModeloBack);
        assertThat(servicoContabilEmpresaModeloBack.getServicoContabil()).isNull();
    }

    @Test
    void servicoContabilEtapaFluxoModeloTest() {
        ServicoContabil servicoContabil = getServicoContabilRandomSampleGenerator();
        ServicoContabilEtapaFluxoModelo servicoContabilEtapaFluxoModeloBack = getServicoContabilEtapaFluxoModeloRandomSampleGenerator();

        servicoContabil.addServicoContabilEtapaFluxoModelo(servicoContabilEtapaFluxoModeloBack);
        assertThat(servicoContabil.getServicoContabilEtapaFluxoModelos()).containsOnly(servicoContabilEtapaFluxoModeloBack);
        assertThat(servicoContabilEtapaFluxoModeloBack.getServicoContabil()).isEqualTo(servicoContabil);

        servicoContabil.removeServicoContabilEtapaFluxoModelo(servicoContabilEtapaFluxoModeloBack);
        assertThat(servicoContabil.getServicoContabilEtapaFluxoModelos()).doesNotContain(servicoContabilEtapaFluxoModeloBack);
        assertThat(servicoContabilEtapaFluxoModeloBack.getServicoContabil()).isNull();

        servicoContabil.servicoContabilEtapaFluxoModelos(new HashSet<>(Set.of(servicoContabilEtapaFluxoModeloBack)));
        assertThat(servicoContabil.getServicoContabilEtapaFluxoModelos()).containsOnly(servicoContabilEtapaFluxoModeloBack);
        assertThat(servicoContabilEtapaFluxoModeloBack.getServicoContabil()).isEqualTo(servicoContabil);

        servicoContabil.setServicoContabilEtapaFluxoModelos(new HashSet<>());
        assertThat(servicoContabil.getServicoContabilEtapaFluxoModelos()).doesNotContain(servicoContabilEtapaFluxoModeloBack);
        assertThat(servicoContabilEtapaFluxoModeloBack.getServicoContabil()).isNull();
    }

    @Test
    void servicoContabilEtapaFluxoExecucaoTest() {
        ServicoContabil servicoContabil = getServicoContabilRandomSampleGenerator();
        ServicoContabilEtapaFluxoExecucao servicoContabilEtapaFluxoExecucaoBack =
            getServicoContabilEtapaFluxoExecucaoRandomSampleGenerator();

        servicoContabil.addServicoContabilEtapaFluxoExecucao(servicoContabilEtapaFluxoExecucaoBack);
        assertThat(servicoContabil.getServicoContabilEtapaFluxoExecucaos()).containsOnly(servicoContabilEtapaFluxoExecucaoBack);
        assertThat(servicoContabilEtapaFluxoExecucaoBack.getServicoContabil()).isEqualTo(servicoContabil);

        servicoContabil.removeServicoContabilEtapaFluxoExecucao(servicoContabilEtapaFluxoExecucaoBack);
        assertThat(servicoContabil.getServicoContabilEtapaFluxoExecucaos()).doesNotContain(servicoContabilEtapaFluxoExecucaoBack);
        assertThat(servicoContabilEtapaFluxoExecucaoBack.getServicoContabil()).isNull();

        servicoContabil.servicoContabilEtapaFluxoExecucaos(new HashSet<>(Set.of(servicoContabilEtapaFluxoExecucaoBack)));
        assertThat(servicoContabil.getServicoContabilEtapaFluxoExecucaos()).containsOnly(servicoContabilEtapaFluxoExecucaoBack);
        assertThat(servicoContabilEtapaFluxoExecucaoBack.getServicoContabil()).isEqualTo(servicoContabil);

        servicoContabil.setServicoContabilEtapaFluxoExecucaos(new HashSet<>());
        assertThat(servicoContabil.getServicoContabilEtapaFluxoExecucaos()).doesNotContain(servicoContabilEtapaFluxoExecucaoBack);
        assertThat(servicoContabilEtapaFluxoExecucaoBack.getServicoContabil()).isNull();
    }

    @Test
    void anexoRequeridoServicoContabilTest() {
        ServicoContabil servicoContabil = getServicoContabilRandomSampleGenerator();
        AnexoRequeridoServicoContabil anexoRequeridoServicoContabilBack = getAnexoRequeridoServicoContabilRandomSampleGenerator();

        servicoContabil.addAnexoRequeridoServicoContabil(anexoRequeridoServicoContabilBack);
        assertThat(servicoContabil.getAnexoRequeridoServicoContabils()).containsOnly(anexoRequeridoServicoContabilBack);
        assertThat(anexoRequeridoServicoContabilBack.getServicoContabil()).isEqualTo(servicoContabil);

        servicoContabil.removeAnexoRequeridoServicoContabil(anexoRequeridoServicoContabilBack);
        assertThat(servicoContabil.getAnexoRequeridoServicoContabils()).doesNotContain(anexoRequeridoServicoContabilBack);
        assertThat(anexoRequeridoServicoContabilBack.getServicoContabil()).isNull();

        servicoContabil.anexoRequeridoServicoContabils(new HashSet<>(Set.of(anexoRequeridoServicoContabilBack)));
        assertThat(servicoContabil.getAnexoRequeridoServicoContabils()).containsOnly(anexoRequeridoServicoContabilBack);
        assertThat(anexoRequeridoServicoContabilBack.getServicoContabil()).isEqualTo(servicoContabil);

        servicoContabil.setAnexoRequeridoServicoContabils(new HashSet<>());
        assertThat(servicoContabil.getAnexoRequeridoServicoContabils()).doesNotContain(anexoRequeridoServicoContabilBack);
        assertThat(anexoRequeridoServicoContabilBack.getServicoContabil()).isNull();
    }

    @Test
    void servicoContabilOrdemServicoTest() {
        ServicoContabil servicoContabil = getServicoContabilRandomSampleGenerator();
        ServicoContabilOrdemServico servicoContabilOrdemServicoBack = getServicoContabilOrdemServicoRandomSampleGenerator();

        servicoContabil.addServicoContabilOrdemServico(servicoContabilOrdemServicoBack);
        assertThat(servicoContabil.getServicoContabilOrdemServicos()).containsOnly(servicoContabilOrdemServicoBack);
        assertThat(servicoContabilOrdemServicoBack.getServicoContabil()).isEqualTo(servicoContabil);

        servicoContabil.removeServicoContabilOrdemServico(servicoContabilOrdemServicoBack);
        assertThat(servicoContabil.getServicoContabilOrdemServicos()).doesNotContain(servicoContabilOrdemServicoBack);
        assertThat(servicoContabilOrdemServicoBack.getServicoContabil()).isNull();

        servicoContabil.servicoContabilOrdemServicos(new HashSet<>(Set.of(servicoContabilOrdemServicoBack)));
        assertThat(servicoContabil.getServicoContabilOrdemServicos()).containsOnly(servicoContabilOrdemServicoBack);
        assertThat(servicoContabilOrdemServicoBack.getServicoContabil()).isEqualTo(servicoContabil);

        servicoContabil.setServicoContabilOrdemServicos(new HashSet<>());
        assertThat(servicoContabil.getServicoContabilOrdemServicos()).doesNotContain(servicoContabilOrdemServicoBack);
        assertThat(servicoContabilOrdemServicoBack.getServicoContabil()).isNull();
    }

    @Test
    void servicoContabilAssinaturaEmpresaTest() {
        ServicoContabil servicoContabil = getServicoContabilRandomSampleGenerator();
        ServicoContabilAssinaturaEmpresa servicoContabilAssinaturaEmpresaBack = getServicoContabilAssinaturaEmpresaRandomSampleGenerator();

        servicoContabil.addServicoContabilAssinaturaEmpresa(servicoContabilAssinaturaEmpresaBack);
        assertThat(servicoContabil.getServicoContabilAssinaturaEmpresas()).containsOnly(servicoContabilAssinaturaEmpresaBack);
        assertThat(servicoContabilAssinaturaEmpresaBack.getServicoContabil()).isEqualTo(servicoContabil);

        servicoContabil.removeServicoContabilAssinaturaEmpresa(servicoContabilAssinaturaEmpresaBack);
        assertThat(servicoContabil.getServicoContabilAssinaturaEmpresas()).doesNotContain(servicoContabilAssinaturaEmpresaBack);
        assertThat(servicoContabilAssinaturaEmpresaBack.getServicoContabil()).isNull();

        servicoContabil.servicoContabilAssinaturaEmpresas(new HashSet<>(Set.of(servicoContabilAssinaturaEmpresaBack)));
        assertThat(servicoContabil.getServicoContabilAssinaturaEmpresas()).containsOnly(servicoContabilAssinaturaEmpresaBack);
        assertThat(servicoContabilAssinaturaEmpresaBack.getServicoContabil()).isEqualTo(servicoContabil);

        servicoContabil.setServicoContabilAssinaturaEmpresas(new HashSet<>());
        assertThat(servicoContabil.getServicoContabilAssinaturaEmpresas()).doesNotContain(servicoContabilAssinaturaEmpresaBack);
        assertThat(servicoContabilAssinaturaEmpresaBack.getServicoContabil()).isNull();
    }

    @Test
    void tarefaEmpresaModeloTest() {
        ServicoContabil servicoContabil = getServicoContabilRandomSampleGenerator();
        TarefaEmpresaModelo tarefaEmpresaModeloBack = getTarefaEmpresaModeloRandomSampleGenerator();

        servicoContabil.addTarefaEmpresaModelo(tarefaEmpresaModeloBack);
        assertThat(servicoContabil.getTarefaEmpresaModelos()).containsOnly(tarefaEmpresaModeloBack);
        assertThat(tarefaEmpresaModeloBack.getServicoContabil()).isEqualTo(servicoContabil);

        servicoContabil.removeTarefaEmpresaModelo(tarefaEmpresaModeloBack);
        assertThat(servicoContabil.getTarefaEmpresaModelos()).doesNotContain(tarefaEmpresaModeloBack);
        assertThat(tarefaEmpresaModeloBack.getServicoContabil()).isNull();

        servicoContabil.tarefaEmpresaModelos(new HashSet<>(Set.of(tarefaEmpresaModeloBack)));
        assertThat(servicoContabil.getTarefaEmpresaModelos()).containsOnly(tarefaEmpresaModeloBack);
        assertThat(tarefaEmpresaModeloBack.getServicoContabil()).isEqualTo(servicoContabil);

        servicoContabil.setTarefaEmpresaModelos(new HashSet<>());
        assertThat(servicoContabil.getTarefaEmpresaModelos()).doesNotContain(tarefaEmpresaModeloBack);
        assertThat(tarefaEmpresaModeloBack.getServicoContabil()).isNull();
    }

    @Test
    void areaContabilTest() {
        ServicoContabil servicoContabil = getServicoContabilRandomSampleGenerator();
        AreaContabil areaContabilBack = getAreaContabilRandomSampleGenerator();

        servicoContabil.setAreaContabil(areaContabilBack);
        assertThat(servicoContabil.getAreaContabil()).isEqualTo(areaContabilBack);

        servicoContabil.areaContabil(null);
        assertThat(servicoContabil.getAreaContabil()).isNull();
    }

    @Test
    void esferaTest() {
        ServicoContabil servicoContabil = getServicoContabilRandomSampleGenerator();
        Esfera esferaBack = getEsferaRandomSampleGenerator();

        servicoContabil.setEsfera(esferaBack);
        assertThat(servicoContabil.getEsfera()).isEqualTo(esferaBack);

        servicoContabil.esfera(null);
        assertThat(servicoContabil.getEsfera()).isNull();
    }
}
