package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.DemissaoFuncionarioTestSamples.*;
import static com.dobemcontabilidade.domain.FuncionarioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DemissaoFuncionarioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DemissaoFuncionario.class);
        DemissaoFuncionario demissaoFuncionario1 = getDemissaoFuncionarioSample1();
        DemissaoFuncionario demissaoFuncionario2 = new DemissaoFuncionario();
        assertThat(demissaoFuncionario1).isNotEqualTo(demissaoFuncionario2);

        demissaoFuncionario2.setId(demissaoFuncionario1.getId());
        assertThat(demissaoFuncionario1).isEqualTo(demissaoFuncionario2);

        demissaoFuncionario2 = getDemissaoFuncionarioSample2();
        assertThat(demissaoFuncionario1).isNotEqualTo(demissaoFuncionario2);
    }

    @Test
    void funcionarioTest() {
        DemissaoFuncionario demissaoFuncionario = getDemissaoFuncionarioRandomSampleGenerator();
        Funcionario funcionarioBack = getFuncionarioRandomSampleGenerator();

        demissaoFuncionario.setFuncionario(funcionarioBack);
        assertThat(demissaoFuncionario.getFuncionario()).isEqualTo(funcionarioBack);

        demissaoFuncionario.funcionario(null);
        assertThat(demissaoFuncionario.getFuncionario()).isNull();
    }
}
