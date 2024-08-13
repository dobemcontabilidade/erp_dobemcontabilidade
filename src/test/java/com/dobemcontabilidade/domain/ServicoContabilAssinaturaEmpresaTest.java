package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AnexoServicoContabilEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.AssinaturaEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.ServicoContabilAssinaturaEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.ServicoContabilTestSamples.*;
import static com.dobemcontabilidade.domain.TarefaRecorrenteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ServicoContabilAssinaturaEmpresaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServicoContabilAssinaturaEmpresa.class);
        ServicoContabilAssinaturaEmpresa servicoContabilAssinaturaEmpresa1 = getServicoContabilAssinaturaEmpresaSample1();
        ServicoContabilAssinaturaEmpresa servicoContabilAssinaturaEmpresa2 = new ServicoContabilAssinaturaEmpresa();
        assertThat(servicoContabilAssinaturaEmpresa1).isNotEqualTo(servicoContabilAssinaturaEmpresa2);

        servicoContabilAssinaturaEmpresa2.setId(servicoContabilAssinaturaEmpresa1.getId());
        assertThat(servicoContabilAssinaturaEmpresa1).isEqualTo(servicoContabilAssinaturaEmpresa2);

        servicoContabilAssinaturaEmpresa2 = getServicoContabilAssinaturaEmpresaSample2();
        assertThat(servicoContabilAssinaturaEmpresa1).isNotEqualTo(servicoContabilAssinaturaEmpresa2);
    }

    @Test
    void anexoServicoContabilEmpresaTest() {
        ServicoContabilAssinaturaEmpresa servicoContabilAssinaturaEmpresa = getServicoContabilAssinaturaEmpresaRandomSampleGenerator();
        AnexoServicoContabilEmpresa anexoServicoContabilEmpresaBack = getAnexoServicoContabilEmpresaRandomSampleGenerator();

        servicoContabilAssinaturaEmpresa.addAnexoServicoContabilEmpresa(anexoServicoContabilEmpresaBack);
        assertThat(servicoContabilAssinaturaEmpresa.getAnexoServicoContabilEmpresas()).containsOnly(anexoServicoContabilEmpresaBack);
        assertThat(anexoServicoContabilEmpresaBack.getServicoContabilAssinaturaEmpresa()).isEqualTo(servicoContabilAssinaturaEmpresa);

        servicoContabilAssinaturaEmpresa.removeAnexoServicoContabilEmpresa(anexoServicoContabilEmpresaBack);
        assertThat(servicoContabilAssinaturaEmpresa.getAnexoServicoContabilEmpresas()).doesNotContain(anexoServicoContabilEmpresaBack);
        assertThat(anexoServicoContabilEmpresaBack.getServicoContabilAssinaturaEmpresa()).isNull();

        servicoContabilAssinaturaEmpresa.anexoServicoContabilEmpresas(new HashSet<>(Set.of(anexoServicoContabilEmpresaBack)));
        assertThat(servicoContabilAssinaturaEmpresa.getAnexoServicoContabilEmpresas()).containsOnly(anexoServicoContabilEmpresaBack);
        assertThat(anexoServicoContabilEmpresaBack.getServicoContabilAssinaturaEmpresa()).isEqualTo(servicoContabilAssinaturaEmpresa);

        servicoContabilAssinaturaEmpresa.setAnexoServicoContabilEmpresas(new HashSet<>());
        assertThat(servicoContabilAssinaturaEmpresa.getAnexoServicoContabilEmpresas()).doesNotContain(anexoServicoContabilEmpresaBack);
        assertThat(anexoServicoContabilEmpresaBack.getServicoContabilAssinaturaEmpresa()).isNull();
    }

    @Test
    void tarefaRecorrenteTest() {
        ServicoContabilAssinaturaEmpresa servicoContabilAssinaturaEmpresa = getServicoContabilAssinaturaEmpresaRandomSampleGenerator();
        TarefaRecorrente tarefaRecorrenteBack = getTarefaRecorrenteRandomSampleGenerator();

        servicoContabilAssinaturaEmpresa.addTarefaRecorrente(tarefaRecorrenteBack);
        assertThat(servicoContabilAssinaturaEmpresa.getTarefaRecorrentes()).containsOnly(tarefaRecorrenteBack);
        assertThat(tarefaRecorrenteBack.getServicoContabilAssinaturaEmpresa()).isEqualTo(servicoContabilAssinaturaEmpresa);

        servicoContabilAssinaturaEmpresa.removeTarefaRecorrente(tarefaRecorrenteBack);
        assertThat(servicoContabilAssinaturaEmpresa.getTarefaRecorrentes()).doesNotContain(tarefaRecorrenteBack);
        assertThat(tarefaRecorrenteBack.getServicoContabilAssinaturaEmpresa()).isNull();

        servicoContabilAssinaturaEmpresa.tarefaRecorrentes(new HashSet<>(Set.of(tarefaRecorrenteBack)));
        assertThat(servicoContabilAssinaturaEmpresa.getTarefaRecorrentes()).containsOnly(tarefaRecorrenteBack);
        assertThat(tarefaRecorrenteBack.getServicoContabilAssinaturaEmpresa()).isEqualTo(servicoContabilAssinaturaEmpresa);

        servicoContabilAssinaturaEmpresa.setTarefaRecorrentes(new HashSet<>());
        assertThat(servicoContabilAssinaturaEmpresa.getTarefaRecorrentes()).doesNotContain(tarefaRecorrenteBack);
        assertThat(tarefaRecorrenteBack.getServicoContabilAssinaturaEmpresa()).isNull();
    }

    @Test
    void servicoContabilTest() {
        ServicoContabilAssinaturaEmpresa servicoContabilAssinaturaEmpresa = getServicoContabilAssinaturaEmpresaRandomSampleGenerator();
        ServicoContabil servicoContabilBack = getServicoContabilRandomSampleGenerator();

        servicoContabilAssinaturaEmpresa.setServicoContabil(servicoContabilBack);
        assertThat(servicoContabilAssinaturaEmpresa.getServicoContabil()).isEqualTo(servicoContabilBack);

        servicoContabilAssinaturaEmpresa.servicoContabil(null);
        assertThat(servicoContabilAssinaturaEmpresa.getServicoContabil()).isNull();
    }

    @Test
    void assinaturaEmpresaTest() {
        ServicoContabilAssinaturaEmpresa servicoContabilAssinaturaEmpresa = getServicoContabilAssinaturaEmpresaRandomSampleGenerator();
        AssinaturaEmpresa assinaturaEmpresaBack = getAssinaturaEmpresaRandomSampleGenerator();

        servicoContabilAssinaturaEmpresa.setAssinaturaEmpresa(assinaturaEmpresaBack);
        assertThat(servicoContabilAssinaturaEmpresa.getAssinaturaEmpresa()).isEqualTo(assinaturaEmpresaBack);

        servicoContabilAssinaturaEmpresa.assinaturaEmpresa(null);
        assertThat(servicoContabilAssinaturaEmpresa.getAssinaturaEmpresa()).isNull();
    }
}
