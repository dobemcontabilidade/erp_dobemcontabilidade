package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.ServicoContabilEmpresaModeloTestSamples.*;
import static com.dobemcontabilidade.domain.TarefaRecorrenteEmpresaModeloTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TarefaRecorrenteEmpresaModeloTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TarefaRecorrenteEmpresaModelo.class);
        TarefaRecorrenteEmpresaModelo tarefaRecorrenteEmpresaModelo1 = getTarefaRecorrenteEmpresaModeloSample1();
        TarefaRecorrenteEmpresaModelo tarefaRecorrenteEmpresaModelo2 = new TarefaRecorrenteEmpresaModelo();
        assertThat(tarefaRecorrenteEmpresaModelo1).isNotEqualTo(tarefaRecorrenteEmpresaModelo2);

        tarefaRecorrenteEmpresaModelo2.setId(tarefaRecorrenteEmpresaModelo1.getId());
        assertThat(tarefaRecorrenteEmpresaModelo1).isEqualTo(tarefaRecorrenteEmpresaModelo2);

        tarefaRecorrenteEmpresaModelo2 = getTarefaRecorrenteEmpresaModeloSample2();
        assertThat(tarefaRecorrenteEmpresaModelo1).isNotEqualTo(tarefaRecorrenteEmpresaModelo2);
    }

    @Test
    void servicoContabilEmpresaModeloTest() {
        TarefaRecorrenteEmpresaModelo tarefaRecorrenteEmpresaModelo = getTarefaRecorrenteEmpresaModeloRandomSampleGenerator();
        ServicoContabilEmpresaModelo servicoContabilEmpresaModeloBack = getServicoContabilEmpresaModeloRandomSampleGenerator();

        tarefaRecorrenteEmpresaModelo.setServicoContabilEmpresaModelo(servicoContabilEmpresaModeloBack);
        assertThat(tarefaRecorrenteEmpresaModelo.getServicoContabilEmpresaModelo()).isEqualTo(servicoContabilEmpresaModeloBack);

        tarefaRecorrenteEmpresaModelo.servicoContabilEmpresaModelo(null);
        assertThat(tarefaRecorrenteEmpresaModelo.getServicoContabilEmpresaModelo()).isNull();
    }
}
