package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.EmpresaModeloTestSamples.*;
import static com.dobemcontabilidade.domain.ServicoContabilTestSamples.*;
import static com.dobemcontabilidade.domain.TarefaEmpresaModeloTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TarefaEmpresaModeloTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TarefaEmpresaModelo.class);
        TarefaEmpresaModelo tarefaEmpresaModelo1 = getTarefaEmpresaModeloSample1();
        TarefaEmpresaModelo tarefaEmpresaModelo2 = new TarefaEmpresaModelo();
        assertThat(tarefaEmpresaModelo1).isNotEqualTo(tarefaEmpresaModelo2);

        tarefaEmpresaModelo2.setId(tarefaEmpresaModelo1.getId());
        assertThat(tarefaEmpresaModelo1).isEqualTo(tarefaEmpresaModelo2);

        tarefaEmpresaModelo2 = getTarefaEmpresaModeloSample2();
        assertThat(tarefaEmpresaModelo1).isNotEqualTo(tarefaEmpresaModelo2);
    }

    @Test
    void empresaModeloTest() {
        TarefaEmpresaModelo tarefaEmpresaModelo = getTarefaEmpresaModeloRandomSampleGenerator();
        EmpresaModelo empresaModeloBack = getEmpresaModeloRandomSampleGenerator();

        tarefaEmpresaModelo.setEmpresaModelo(empresaModeloBack);
        assertThat(tarefaEmpresaModelo.getEmpresaModelo()).isEqualTo(empresaModeloBack);

        tarefaEmpresaModelo.empresaModelo(null);
        assertThat(tarefaEmpresaModelo.getEmpresaModelo()).isNull();
    }

    @Test
    void servicoContabilTest() {
        TarefaEmpresaModelo tarefaEmpresaModelo = getTarefaEmpresaModeloRandomSampleGenerator();
        ServicoContabil servicoContabilBack = getServicoContabilRandomSampleGenerator();

        tarefaEmpresaModelo.setServicoContabil(servicoContabilBack);
        assertThat(tarefaEmpresaModelo.getServicoContabil()).isEqualTo(servicoContabilBack);

        tarefaEmpresaModelo.servicoContabil(null);
        assertThat(tarefaEmpresaModelo.getServicoContabil()).isNull();
    }
}
