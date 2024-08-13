package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AnexoRequeridoTarefaRecorrenteTestSamples.*;
import static com.dobemcontabilidade.domain.ServicoContabilAssinaturaEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.TarefaRecorrenteExecucaoTestSamples.*;
import static com.dobemcontabilidade.domain.TarefaRecorrenteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TarefaRecorrenteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TarefaRecorrente.class);
        TarefaRecorrente tarefaRecorrente1 = getTarefaRecorrenteSample1();
        TarefaRecorrente tarefaRecorrente2 = new TarefaRecorrente();
        assertThat(tarefaRecorrente1).isNotEqualTo(tarefaRecorrente2);

        tarefaRecorrente2.setId(tarefaRecorrente1.getId());
        assertThat(tarefaRecorrente1).isEqualTo(tarefaRecorrente2);

        tarefaRecorrente2 = getTarefaRecorrenteSample2();
        assertThat(tarefaRecorrente1).isNotEqualTo(tarefaRecorrente2);
    }

    @Test
    void tarefaRecorrenteExecucaoTest() {
        TarefaRecorrente tarefaRecorrente = getTarefaRecorrenteRandomSampleGenerator();
        TarefaRecorrenteExecucao tarefaRecorrenteExecucaoBack = getTarefaRecorrenteExecucaoRandomSampleGenerator();

        tarefaRecorrente.addTarefaRecorrenteExecucao(tarefaRecorrenteExecucaoBack);
        assertThat(tarefaRecorrente.getTarefaRecorrenteExecucaos()).containsOnly(tarefaRecorrenteExecucaoBack);
        assertThat(tarefaRecorrenteExecucaoBack.getTarefaRecorrente()).isEqualTo(tarefaRecorrente);

        tarefaRecorrente.removeTarefaRecorrenteExecucao(tarefaRecorrenteExecucaoBack);
        assertThat(tarefaRecorrente.getTarefaRecorrenteExecucaos()).doesNotContain(tarefaRecorrenteExecucaoBack);
        assertThat(tarefaRecorrenteExecucaoBack.getTarefaRecorrente()).isNull();

        tarefaRecorrente.tarefaRecorrenteExecucaos(new HashSet<>(Set.of(tarefaRecorrenteExecucaoBack)));
        assertThat(tarefaRecorrente.getTarefaRecorrenteExecucaos()).containsOnly(tarefaRecorrenteExecucaoBack);
        assertThat(tarefaRecorrenteExecucaoBack.getTarefaRecorrente()).isEqualTo(tarefaRecorrente);

        tarefaRecorrente.setTarefaRecorrenteExecucaos(new HashSet<>());
        assertThat(tarefaRecorrente.getTarefaRecorrenteExecucaos()).doesNotContain(tarefaRecorrenteExecucaoBack);
        assertThat(tarefaRecorrenteExecucaoBack.getTarefaRecorrente()).isNull();
    }

    @Test
    void anexoRequeridoTarefaRecorrenteTest() {
        TarefaRecorrente tarefaRecorrente = getTarefaRecorrenteRandomSampleGenerator();
        AnexoRequeridoTarefaRecorrente anexoRequeridoTarefaRecorrenteBack = getAnexoRequeridoTarefaRecorrenteRandomSampleGenerator();

        tarefaRecorrente.addAnexoRequeridoTarefaRecorrente(anexoRequeridoTarefaRecorrenteBack);
        assertThat(tarefaRecorrente.getAnexoRequeridoTarefaRecorrentes()).containsOnly(anexoRequeridoTarefaRecorrenteBack);
        assertThat(anexoRequeridoTarefaRecorrenteBack.getTarefaRecorrente()).isEqualTo(tarefaRecorrente);

        tarefaRecorrente.removeAnexoRequeridoTarefaRecorrente(anexoRequeridoTarefaRecorrenteBack);
        assertThat(tarefaRecorrente.getAnexoRequeridoTarefaRecorrentes()).doesNotContain(anexoRequeridoTarefaRecorrenteBack);
        assertThat(anexoRequeridoTarefaRecorrenteBack.getTarefaRecorrente()).isNull();

        tarefaRecorrente.anexoRequeridoTarefaRecorrentes(new HashSet<>(Set.of(anexoRequeridoTarefaRecorrenteBack)));
        assertThat(tarefaRecorrente.getAnexoRequeridoTarefaRecorrentes()).containsOnly(anexoRequeridoTarefaRecorrenteBack);
        assertThat(anexoRequeridoTarefaRecorrenteBack.getTarefaRecorrente()).isEqualTo(tarefaRecorrente);

        tarefaRecorrente.setAnexoRequeridoTarefaRecorrentes(new HashSet<>());
        assertThat(tarefaRecorrente.getAnexoRequeridoTarefaRecorrentes()).doesNotContain(anexoRequeridoTarefaRecorrenteBack);
        assertThat(anexoRequeridoTarefaRecorrenteBack.getTarefaRecorrente()).isNull();
    }

    @Test
    void servicoContabilAssinaturaEmpresaTest() {
        TarefaRecorrente tarefaRecorrente = getTarefaRecorrenteRandomSampleGenerator();
        ServicoContabilAssinaturaEmpresa servicoContabilAssinaturaEmpresaBack = getServicoContabilAssinaturaEmpresaRandomSampleGenerator();

        tarefaRecorrente.setServicoContabilAssinaturaEmpresa(servicoContabilAssinaturaEmpresaBack);
        assertThat(tarefaRecorrente.getServicoContabilAssinaturaEmpresa()).isEqualTo(servicoContabilAssinaturaEmpresaBack);

        tarefaRecorrente.servicoContabilAssinaturaEmpresa(null);
        assertThat(tarefaRecorrente.getServicoContabilAssinaturaEmpresa()).isNull();
    }
}
