package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.CidadeTestSamples.*;
import static com.dobemcontabilidade.domain.ContratoFuncionarioTestSamples.*;
import static com.dobemcontabilidade.domain.InstituicaoEnsinoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class InstituicaoEnsinoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InstituicaoEnsino.class);
        InstituicaoEnsino instituicaoEnsino1 = getInstituicaoEnsinoSample1();
        InstituicaoEnsino instituicaoEnsino2 = new InstituicaoEnsino();
        assertThat(instituicaoEnsino1).isNotEqualTo(instituicaoEnsino2);

        instituicaoEnsino2.setId(instituicaoEnsino1.getId());
        assertThat(instituicaoEnsino1).isEqualTo(instituicaoEnsino2);

        instituicaoEnsino2 = getInstituicaoEnsinoSample2();
        assertThat(instituicaoEnsino1).isNotEqualTo(instituicaoEnsino2);
    }

    @Test
    void contratoFuncionarioTest() {
        InstituicaoEnsino instituicaoEnsino = getInstituicaoEnsinoRandomSampleGenerator();
        ContratoFuncionario contratoFuncionarioBack = getContratoFuncionarioRandomSampleGenerator();

        instituicaoEnsino.addContratoFuncionario(contratoFuncionarioBack);
        assertThat(instituicaoEnsino.getContratoFuncionarios()).containsOnly(contratoFuncionarioBack);
        assertThat(contratoFuncionarioBack.getInstituicaoEnsino()).isEqualTo(instituicaoEnsino);

        instituicaoEnsino.removeContratoFuncionario(contratoFuncionarioBack);
        assertThat(instituicaoEnsino.getContratoFuncionarios()).doesNotContain(contratoFuncionarioBack);
        assertThat(contratoFuncionarioBack.getInstituicaoEnsino()).isNull();

        instituicaoEnsino.contratoFuncionarios(new HashSet<>(Set.of(contratoFuncionarioBack)));
        assertThat(instituicaoEnsino.getContratoFuncionarios()).containsOnly(contratoFuncionarioBack);
        assertThat(contratoFuncionarioBack.getInstituicaoEnsino()).isEqualTo(instituicaoEnsino);

        instituicaoEnsino.setContratoFuncionarios(new HashSet<>());
        assertThat(instituicaoEnsino.getContratoFuncionarios()).doesNotContain(contratoFuncionarioBack);
        assertThat(contratoFuncionarioBack.getInstituicaoEnsino()).isNull();
    }

    @Test
    void cidadeTest() {
        InstituicaoEnsino instituicaoEnsino = getInstituicaoEnsinoRandomSampleGenerator();
        Cidade cidadeBack = getCidadeRandomSampleGenerator();

        instituicaoEnsino.setCidade(cidadeBack);
        assertThat(instituicaoEnsino.getCidade()).isEqualTo(cidadeBack);

        instituicaoEnsino.cidade(null);
        assertThat(instituicaoEnsino.getCidade()).isNull();
    }
}
