package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AnexoRequeridoEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.AnexoRequeridoPessoaTestSamples.*;
import static com.dobemcontabilidade.domain.AnexoRequeridoServicoContabilTestSamples.*;
import static com.dobemcontabilidade.domain.AnexoRequeridoTarefaOrdemServicoTestSamples.*;
import static com.dobemcontabilidade.domain.AnexoRequeridoTarefaRecorrenteTestSamples.*;
import static com.dobemcontabilidade.domain.AnexoRequeridoTestSamples.*;
import static com.dobemcontabilidade.domain.AnexoServicoContabilEmpresaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AnexoRequeridoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnexoRequerido.class);
        AnexoRequerido anexoRequerido1 = getAnexoRequeridoSample1();
        AnexoRequerido anexoRequerido2 = new AnexoRequerido();
        assertThat(anexoRequerido1).isNotEqualTo(anexoRequerido2);

        anexoRequerido2.setId(anexoRequerido1.getId());
        assertThat(anexoRequerido1).isEqualTo(anexoRequerido2);

        anexoRequerido2 = getAnexoRequeridoSample2();
        assertThat(anexoRequerido1).isNotEqualTo(anexoRequerido2);
    }

    @Test
    void anexoRequeridoPessoaTest() {
        AnexoRequerido anexoRequerido = getAnexoRequeridoRandomSampleGenerator();
        AnexoRequeridoPessoa anexoRequeridoPessoaBack = getAnexoRequeridoPessoaRandomSampleGenerator();

        anexoRequerido.addAnexoRequeridoPessoa(anexoRequeridoPessoaBack);
        assertThat(anexoRequerido.getAnexoRequeridoPessoas()).containsOnly(anexoRequeridoPessoaBack);
        assertThat(anexoRequeridoPessoaBack.getAnexoRequerido()).isEqualTo(anexoRequerido);

        anexoRequerido.removeAnexoRequeridoPessoa(anexoRequeridoPessoaBack);
        assertThat(anexoRequerido.getAnexoRequeridoPessoas()).doesNotContain(anexoRequeridoPessoaBack);
        assertThat(anexoRequeridoPessoaBack.getAnexoRequerido()).isNull();

        anexoRequerido.anexoRequeridoPessoas(new HashSet<>(Set.of(anexoRequeridoPessoaBack)));
        assertThat(anexoRequerido.getAnexoRequeridoPessoas()).containsOnly(anexoRequeridoPessoaBack);
        assertThat(anexoRequeridoPessoaBack.getAnexoRequerido()).isEqualTo(anexoRequerido);

        anexoRequerido.setAnexoRequeridoPessoas(new HashSet<>());
        assertThat(anexoRequerido.getAnexoRequeridoPessoas()).doesNotContain(anexoRequeridoPessoaBack);
        assertThat(anexoRequeridoPessoaBack.getAnexoRequerido()).isNull();
    }

    @Test
    void anexoRequeridoEmpresaTest() {
        AnexoRequerido anexoRequerido = getAnexoRequeridoRandomSampleGenerator();
        AnexoRequeridoEmpresa anexoRequeridoEmpresaBack = getAnexoRequeridoEmpresaRandomSampleGenerator();

        anexoRequerido.addAnexoRequeridoEmpresa(anexoRequeridoEmpresaBack);
        assertThat(anexoRequerido.getAnexoRequeridoEmpresas()).containsOnly(anexoRequeridoEmpresaBack);
        assertThat(anexoRequeridoEmpresaBack.getAnexoRequerido()).isEqualTo(anexoRequerido);

        anexoRequerido.removeAnexoRequeridoEmpresa(anexoRequeridoEmpresaBack);
        assertThat(anexoRequerido.getAnexoRequeridoEmpresas()).doesNotContain(anexoRequeridoEmpresaBack);
        assertThat(anexoRequeridoEmpresaBack.getAnexoRequerido()).isNull();

        anexoRequerido.anexoRequeridoEmpresas(new HashSet<>(Set.of(anexoRequeridoEmpresaBack)));
        assertThat(anexoRequerido.getAnexoRequeridoEmpresas()).containsOnly(anexoRequeridoEmpresaBack);
        assertThat(anexoRequeridoEmpresaBack.getAnexoRequerido()).isEqualTo(anexoRequerido);

        anexoRequerido.setAnexoRequeridoEmpresas(new HashSet<>());
        assertThat(anexoRequerido.getAnexoRequeridoEmpresas()).doesNotContain(anexoRequeridoEmpresaBack);
        assertThat(anexoRequeridoEmpresaBack.getAnexoRequerido()).isNull();
    }

    @Test
    void anexoRequeridoServicoContabilTest() {
        AnexoRequerido anexoRequerido = getAnexoRequeridoRandomSampleGenerator();
        AnexoRequeridoServicoContabil anexoRequeridoServicoContabilBack = getAnexoRequeridoServicoContabilRandomSampleGenerator();

        anexoRequerido.addAnexoRequeridoServicoContabil(anexoRequeridoServicoContabilBack);
        assertThat(anexoRequerido.getAnexoRequeridoServicoContabils()).containsOnly(anexoRequeridoServicoContabilBack);
        assertThat(anexoRequeridoServicoContabilBack.getAnexoRequerido()).isEqualTo(anexoRequerido);

        anexoRequerido.removeAnexoRequeridoServicoContabil(anexoRequeridoServicoContabilBack);
        assertThat(anexoRequerido.getAnexoRequeridoServicoContabils()).doesNotContain(anexoRequeridoServicoContabilBack);
        assertThat(anexoRequeridoServicoContabilBack.getAnexoRequerido()).isNull();

        anexoRequerido.anexoRequeridoServicoContabils(new HashSet<>(Set.of(anexoRequeridoServicoContabilBack)));
        assertThat(anexoRequerido.getAnexoRequeridoServicoContabils()).containsOnly(anexoRequeridoServicoContabilBack);
        assertThat(anexoRequeridoServicoContabilBack.getAnexoRequerido()).isEqualTo(anexoRequerido);

        anexoRequerido.setAnexoRequeridoServicoContabils(new HashSet<>());
        assertThat(anexoRequerido.getAnexoRequeridoServicoContabils()).doesNotContain(anexoRequeridoServicoContabilBack);
        assertThat(anexoRequeridoServicoContabilBack.getAnexoRequerido()).isNull();
    }

    @Test
    void anexoServicoContabilEmpresaTest() {
        AnexoRequerido anexoRequerido = getAnexoRequeridoRandomSampleGenerator();
        AnexoServicoContabilEmpresa anexoServicoContabilEmpresaBack = getAnexoServicoContabilEmpresaRandomSampleGenerator();

        anexoRequerido.addAnexoServicoContabilEmpresa(anexoServicoContabilEmpresaBack);
        assertThat(anexoRequerido.getAnexoServicoContabilEmpresas()).containsOnly(anexoServicoContabilEmpresaBack);
        assertThat(anexoServicoContabilEmpresaBack.getAnexoRequerido()).isEqualTo(anexoRequerido);

        anexoRequerido.removeAnexoServicoContabilEmpresa(anexoServicoContabilEmpresaBack);
        assertThat(anexoRequerido.getAnexoServicoContabilEmpresas()).doesNotContain(anexoServicoContabilEmpresaBack);
        assertThat(anexoServicoContabilEmpresaBack.getAnexoRequerido()).isNull();

        anexoRequerido.anexoServicoContabilEmpresas(new HashSet<>(Set.of(anexoServicoContabilEmpresaBack)));
        assertThat(anexoRequerido.getAnexoServicoContabilEmpresas()).containsOnly(anexoServicoContabilEmpresaBack);
        assertThat(anexoServicoContabilEmpresaBack.getAnexoRequerido()).isEqualTo(anexoRequerido);

        anexoRequerido.setAnexoServicoContabilEmpresas(new HashSet<>());
        assertThat(anexoRequerido.getAnexoServicoContabilEmpresas()).doesNotContain(anexoServicoContabilEmpresaBack);
        assertThat(anexoServicoContabilEmpresaBack.getAnexoRequerido()).isNull();
    }

    @Test
    void anexoRequeridoTarefaOrdemServicoTest() {
        AnexoRequerido anexoRequerido = getAnexoRequeridoRandomSampleGenerator();
        AnexoRequeridoTarefaOrdemServico anexoRequeridoTarefaOrdemServicoBack = getAnexoRequeridoTarefaOrdemServicoRandomSampleGenerator();

        anexoRequerido.addAnexoRequeridoTarefaOrdemServico(anexoRequeridoTarefaOrdemServicoBack);
        assertThat(anexoRequerido.getAnexoRequeridoTarefaOrdemServicos()).containsOnly(anexoRequeridoTarefaOrdemServicoBack);
        assertThat(anexoRequeridoTarefaOrdemServicoBack.getAnexoRequerido()).isEqualTo(anexoRequerido);

        anexoRequerido.removeAnexoRequeridoTarefaOrdemServico(anexoRequeridoTarefaOrdemServicoBack);
        assertThat(anexoRequerido.getAnexoRequeridoTarefaOrdemServicos()).doesNotContain(anexoRequeridoTarefaOrdemServicoBack);
        assertThat(anexoRequeridoTarefaOrdemServicoBack.getAnexoRequerido()).isNull();

        anexoRequerido.anexoRequeridoTarefaOrdemServicos(new HashSet<>(Set.of(anexoRequeridoTarefaOrdemServicoBack)));
        assertThat(anexoRequerido.getAnexoRequeridoTarefaOrdemServicos()).containsOnly(anexoRequeridoTarefaOrdemServicoBack);
        assertThat(anexoRequeridoTarefaOrdemServicoBack.getAnexoRequerido()).isEqualTo(anexoRequerido);

        anexoRequerido.setAnexoRequeridoTarefaOrdemServicos(new HashSet<>());
        assertThat(anexoRequerido.getAnexoRequeridoTarefaOrdemServicos()).doesNotContain(anexoRequeridoTarefaOrdemServicoBack);
        assertThat(anexoRequeridoTarefaOrdemServicoBack.getAnexoRequerido()).isNull();
    }

    @Test
    void anexoRequeridoTarefaRecorrenteTest() {
        AnexoRequerido anexoRequerido = getAnexoRequeridoRandomSampleGenerator();
        AnexoRequeridoTarefaRecorrente anexoRequeridoTarefaRecorrenteBack = getAnexoRequeridoTarefaRecorrenteRandomSampleGenerator();

        anexoRequerido.addAnexoRequeridoTarefaRecorrente(anexoRequeridoTarefaRecorrenteBack);
        assertThat(anexoRequerido.getAnexoRequeridoTarefaRecorrentes()).containsOnly(anexoRequeridoTarefaRecorrenteBack);
        assertThat(anexoRequeridoTarefaRecorrenteBack.getAnexoRequerido()).isEqualTo(anexoRequerido);

        anexoRequerido.removeAnexoRequeridoTarefaRecorrente(anexoRequeridoTarefaRecorrenteBack);
        assertThat(anexoRequerido.getAnexoRequeridoTarefaRecorrentes()).doesNotContain(anexoRequeridoTarefaRecorrenteBack);
        assertThat(anexoRequeridoTarefaRecorrenteBack.getAnexoRequerido()).isNull();

        anexoRequerido.anexoRequeridoTarefaRecorrentes(new HashSet<>(Set.of(anexoRequeridoTarefaRecorrenteBack)));
        assertThat(anexoRequerido.getAnexoRequeridoTarefaRecorrentes()).containsOnly(anexoRequeridoTarefaRecorrenteBack);
        assertThat(anexoRequeridoTarefaRecorrenteBack.getAnexoRequerido()).isEqualTo(anexoRequerido);

        anexoRequerido.setAnexoRequeridoTarefaRecorrentes(new HashSet<>());
        assertThat(anexoRequerido.getAnexoRequeridoTarefaRecorrentes()).doesNotContain(anexoRequeridoTarefaRecorrenteBack);
        assertThat(anexoRequeridoTarefaRecorrenteBack.getAnexoRequerido()).isNull();
    }
}
