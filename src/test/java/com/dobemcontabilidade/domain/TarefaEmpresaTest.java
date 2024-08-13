package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.ContadorTestSamples.*;
import static com.dobemcontabilidade.domain.EmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.TarefaEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.TarefaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TarefaEmpresaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TarefaEmpresa.class);
        TarefaEmpresa tarefaEmpresa1 = getTarefaEmpresaSample1();
        TarefaEmpresa tarefaEmpresa2 = new TarefaEmpresa();
        assertThat(tarefaEmpresa1).isNotEqualTo(tarefaEmpresa2);

        tarefaEmpresa2.setId(tarefaEmpresa1.getId());
        assertThat(tarefaEmpresa1).isEqualTo(tarefaEmpresa2);

        tarefaEmpresa2 = getTarefaEmpresaSample2();
        assertThat(tarefaEmpresa1).isNotEqualTo(tarefaEmpresa2);
    }

    @Test
    void empresaTest() {
        TarefaEmpresa tarefaEmpresa = getTarefaEmpresaRandomSampleGenerator();
        Empresa empresaBack = getEmpresaRandomSampleGenerator();

        tarefaEmpresa.setEmpresa(empresaBack);
        assertThat(tarefaEmpresa.getEmpresa()).isEqualTo(empresaBack);

        tarefaEmpresa.empresa(null);
        assertThat(tarefaEmpresa.getEmpresa()).isNull();
    }

    @Test
    void contadorTest() {
        TarefaEmpresa tarefaEmpresa = getTarefaEmpresaRandomSampleGenerator();
        Contador contadorBack = getContadorRandomSampleGenerator();

        tarefaEmpresa.setContador(contadorBack);
        assertThat(tarefaEmpresa.getContador()).isEqualTo(contadorBack);

        tarefaEmpresa.contador(null);
        assertThat(tarefaEmpresa.getContador()).isNull();
    }

    @Test
    void tarefaTest() {
        TarefaEmpresa tarefaEmpresa = getTarefaEmpresaRandomSampleGenerator();
        Tarefa tarefaBack = getTarefaRandomSampleGenerator();

        tarefaEmpresa.setTarefa(tarefaBack);
        assertThat(tarefaEmpresa.getTarefa()).isEqualTo(tarefaBack);

        tarefaEmpresa.tarefa(null);
        assertThat(tarefaEmpresa.getTarefa()).isNull();
    }
}
