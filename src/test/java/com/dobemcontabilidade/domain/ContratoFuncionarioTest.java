package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AgenteIntegracaoEstagioTestSamples.*;
import static com.dobemcontabilidade.domain.ContratoFuncionarioTestSamples.*;
import static com.dobemcontabilidade.domain.FuncionarioTestSamples.*;
import static com.dobemcontabilidade.domain.InstituicaoEnsinoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContratoFuncionarioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContratoFuncionario.class);
        ContratoFuncionario contratoFuncionario1 = getContratoFuncionarioSample1();
        ContratoFuncionario contratoFuncionario2 = new ContratoFuncionario();
        assertThat(contratoFuncionario1).isNotEqualTo(contratoFuncionario2);

        contratoFuncionario2.setId(contratoFuncionario1.getId());
        assertThat(contratoFuncionario1).isEqualTo(contratoFuncionario2);

        contratoFuncionario2 = getContratoFuncionarioSample2();
        assertThat(contratoFuncionario1).isNotEqualTo(contratoFuncionario2);
    }

    @Test
    void funcionarioTest() {
        ContratoFuncionario contratoFuncionario = getContratoFuncionarioRandomSampleGenerator();
        Funcionario funcionarioBack = getFuncionarioRandomSampleGenerator();

        contratoFuncionario.setFuncionario(funcionarioBack);
        assertThat(contratoFuncionario.getFuncionario()).isEqualTo(funcionarioBack);

        contratoFuncionario.funcionario(null);
        assertThat(contratoFuncionario.getFuncionario()).isNull();
    }

    @Test
    void agenteIntegracaoEstagioTest() {
        ContratoFuncionario contratoFuncionario = getContratoFuncionarioRandomSampleGenerator();
        AgenteIntegracaoEstagio agenteIntegracaoEstagioBack = getAgenteIntegracaoEstagioRandomSampleGenerator();

        contratoFuncionario.setAgenteIntegracaoEstagio(agenteIntegracaoEstagioBack);
        assertThat(contratoFuncionario.getAgenteIntegracaoEstagio()).isEqualTo(agenteIntegracaoEstagioBack);

        contratoFuncionario.agenteIntegracaoEstagio(null);
        assertThat(contratoFuncionario.getAgenteIntegracaoEstagio()).isNull();
    }

    @Test
    void instituicaoEnsinoTest() {
        ContratoFuncionario contratoFuncionario = getContratoFuncionarioRandomSampleGenerator();
        InstituicaoEnsino instituicaoEnsinoBack = getInstituicaoEnsinoRandomSampleGenerator();

        contratoFuncionario.setInstituicaoEnsino(instituicaoEnsinoBack);
        assertThat(contratoFuncionario.getInstituicaoEnsino()).isEqualTo(instituicaoEnsinoBack);

        contratoFuncionario.instituicaoEnsino(null);
        assertThat(contratoFuncionario.getInstituicaoEnsino()).isNull();
    }
}
