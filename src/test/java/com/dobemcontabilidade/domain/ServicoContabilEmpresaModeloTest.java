package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.EmpresaModeloTestSamples.*;
import static com.dobemcontabilidade.domain.ServicoContabilEmpresaModeloTestSamples.*;
import static com.dobemcontabilidade.domain.ServicoContabilTestSamples.*;
import static com.dobemcontabilidade.domain.TarefaRecorrenteEmpresaModeloTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ServicoContabilEmpresaModeloTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServicoContabilEmpresaModelo.class);
        ServicoContabilEmpresaModelo servicoContabilEmpresaModelo1 = getServicoContabilEmpresaModeloSample1();
        ServicoContabilEmpresaModelo servicoContabilEmpresaModelo2 = new ServicoContabilEmpresaModelo();
        assertThat(servicoContabilEmpresaModelo1).isNotEqualTo(servicoContabilEmpresaModelo2);

        servicoContabilEmpresaModelo2.setId(servicoContabilEmpresaModelo1.getId());
        assertThat(servicoContabilEmpresaModelo1).isEqualTo(servicoContabilEmpresaModelo2);

        servicoContabilEmpresaModelo2 = getServicoContabilEmpresaModeloSample2();
        assertThat(servicoContabilEmpresaModelo1).isNotEqualTo(servicoContabilEmpresaModelo2);
    }

    @Test
    void tarefaRecorrenteEmpresaModeloTest() {
        ServicoContabilEmpresaModelo servicoContabilEmpresaModelo = getServicoContabilEmpresaModeloRandomSampleGenerator();
        TarefaRecorrenteEmpresaModelo tarefaRecorrenteEmpresaModeloBack = getTarefaRecorrenteEmpresaModeloRandomSampleGenerator();

        servicoContabilEmpresaModelo.addTarefaRecorrenteEmpresaModelo(tarefaRecorrenteEmpresaModeloBack);
        assertThat(servicoContabilEmpresaModelo.getTarefaRecorrenteEmpresaModelos()).containsOnly(tarefaRecorrenteEmpresaModeloBack);
        assertThat(tarefaRecorrenteEmpresaModeloBack.getServicoContabilEmpresaModelo()).isEqualTo(servicoContabilEmpresaModelo);

        servicoContabilEmpresaModelo.removeTarefaRecorrenteEmpresaModelo(tarefaRecorrenteEmpresaModeloBack);
        assertThat(servicoContabilEmpresaModelo.getTarefaRecorrenteEmpresaModelos()).doesNotContain(tarefaRecorrenteEmpresaModeloBack);
        assertThat(tarefaRecorrenteEmpresaModeloBack.getServicoContabilEmpresaModelo()).isNull();

        servicoContabilEmpresaModelo.tarefaRecorrenteEmpresaModelos(new HashSet<>(Set.of(tarefaRecorrenteEmpresaModeloBack)));
        assertThat(servicoContabilEmpresaModelo.getTarefaRecorrenteEmpresaModelos()).containsOnly(tarefaRecorrenteEmpresaModeloBack);
        assertThat(tarefaRecorrenteEmpresaModeloBack.getServicoContabilEmpresaModelo()).isEqualTo(servicoContabilEmpresaModelo);

        servicoContabilEmpresaModelo.setTarefaRecorrenteEmpresaModelos(new HashSet<>());
        assertThat(servicoContabilEmpresaModelo.getTarefaRecorrenteEmpresaModelos()).doesNotContain(tarefaRecorrenteEmpresaModeloBack);
        assertThat(tarefaRecorrenteEmpresaModeloBack.getServicoContabilEmpresaModelo()).isNull();
    }

    @Test
    void empresaModeloTest() {
        ServicoContabilEmpresaModelo servicoContabilEmpresaModelo = getServicoContabilEmpresaModeloRandomSampleGenerator();
        EmpresaModelo empresaModeloBack = getEmpresaModeloRandomSampleGenerator();

        servicoContabilEmpresaModelo.setEmpresaModelo(empresaModeloBack);
        assertThat(servicoContabilEmpresaModelo.getEmpresaModelo()).isEqualTo(empresaModeloBack);

        servicoContabilEmpresaModelo.empresaModelo(null);
        assertThat(servicoContabilEmpresaModelo.getEmpresaModelo()).isNull();
    }

    @Test
    void servicoContabilTest() {
        ServicoContabilEmpresaModelo servicoContabilEmpresaModelo = getServicoContabilEmpresaModeloRandomSampleGenerator();
        ServicoContabil servicoContabilBack = getServicoContabilRandomSampleGenerator();

        servicoContabilEmpresaModelo.setServicoContabil(servicoContabilBack);
        assertThat(servicoContabilEmpresaModelo.getServicoContabil()).isEqualTo(servicoContabilBack);

        servicoContabilEmpresaModelo.servicoContabil(null);
        assertThat(servicoContabilEmpresaModelo.getServicoContabil()).isNull();
    }
}
