package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AgenteIntegracaoEstagioTestSamples.*;
import static com.dobemcontabilidade.domain.CidadeTestSamples.*;
import static com.dobemcontabilidade.domain.ContratoFuncionarioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class AgenteIntegracaoEstagioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgenteIntegracaoEstagio.class);
        AgenteIntegracaoEstagio agenteIntegracaoEstagio1 = getAgenteIntegracaoEstagioSample1();
        AgenteIntegracaoEstagio agenteIntegracaoEstagio2 = new AgenteIntegracaoEstagio();
        assertThat(agenteIntegracaoEstagio1).isNotEqualTo(agenteIntegracaoEstagio2);

        agenteIntegracaoEstagio2.setId(agenteIntegracaoEstagio1.getId());
        assertThat(agenteIntegracaoEstagio1).isEqualTo(agenteIntegracaoEstagio2);

        agenteIntegracaoEstagio2 = getAgenteIntegracaoEstagioSample2();
        assertThat(agenteIntegracaoEstagio1).isNotEqualTo(agenteIntegracaoEstagio2);
    }

    @Test
    void contratoFuncionarioTest() {
        AgenteIntegracaoEstagio agenteIntegracaoEstagio = getAgenteIntegracaoEstagioRandomSampleGenerator();
        ContratoFuncionario contratoFuncionarioBack = getContratoFuncionarioRandomSampleGenerator();

        agenteIntegracaoEstagio.addContratoFuncionario(contratoFuncionarioBack);
        assertThat(agenteIntegracaoEstagio.getContratoFuncionarios()).containsOnly(contratoFuncionarioBack);
        assertThat(contratoFuncionarioBack.getAgenteIntegracaoEstagio()).isEqualTo(agenteIntegracaoEstagio);

        agenteIntegracaoEstagio.removeContratoFuncionario(contratoFuncionarioBack);
        assertThat(agenteIntegracaoEstagio.getContratoFuncionarios()).doesNotContain(contratoFuncionarioBack);
        assertThat(contratoFuncionarioBack.getAgenteIntegracaoEstagio()).isNull();

        agenteIntegracaoEstagio.contratoFuncionarios(new HashSet<>(Set.of(contratoFuncionarioBack)));
        assertThat(agenteIntegracaoEstagio.getContratoFuncionarios()).containsOnly(contratoFuncionarioBack);
        assertThat(contratoFuncionarioBack.getAgenteIntegracaoEstagio()).isEqualTo(agenteIntegracaoEstagio);

        agenteIntegracaoEstagio.setContratoFuncionarios(new HashSet<>());
        assertThat(agenteIntegracaoEstagio.getContratoFuncionarios()).doesNotContain(contratoFuncionarioBack);
        assertThat(contratoFuncionarioBack.getAgenteIntegracaoEstagio()).isNull();
    }

    @Test
    void cidadeTest() {
        AgenteIntegracaoEstagio agenteIntegracaoEstagio = getAgenteIntegracaoEstagioRandomSampleGenerator();
        Cidade cidadeBack = getCidadeRandomSampleGenerator();

        agenteIntegracaoEstagio.setCidade(cidadeBack);
        assertThat(agenteIntegracaoEstagio.getCidade()).isEqualTo(cidadeBack);

        agenteIntegracaoEstagio.cidade(null);
        assertThat(agenteIntegracaoEstagio.getCidade()).isNull();
    }
}
