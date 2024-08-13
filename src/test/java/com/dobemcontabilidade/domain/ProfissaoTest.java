package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.FuncionarioTestSamples.*;
import static com.dobemcontabilidade.domain.ProfissaoTestSamples.*;
import static com.dobemcontabilidade.domain.SocioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ProfissaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Profissao.class);
        Profissao profissao1 = getProfissaoSample1();
        Profissao profissao2 = new Profissao();
        assertThat(profissao1).isNotEqualTo(profissao2);

        profissao2.setId(profissao1.getId());
        assertThat(profissao1).isEqualTo(profissao2);

        profissao2 = getProfissaoSample2();
        assertThat(profissao1).isNotEqualTo(profissao2);
    }

    @Test
    void funcionarioTest() {
        Profissao profissao = getProfissaoRandomSampleGenerator();
        Funcionario funcionarioBack = getFuncionarioRandomSampleGenerator();

        profissao.addFuncionario(funcionarioBack);
        assertThat(profissao.getFuncionarios()).containsOnly(funcionarioBack);
        assertThat(funcionarioBack.getProfissao()).isEqualTo(profissao);

        profissao.removeFuncionario(funcionarioBack);
        assertThat(profissao.getFuncionarios()).doesNotContain(funcionarioBack);
        assertThat(funcionarioBack.getProfissao()).isNull();

        profissao.funcionarios(new HashSet<>(Set.of(funcionarioBack)));
        assertThat(profissao.getFuncionarios()).containsOnly(funcionarioBack);
        assertThat(funcionarioBack.getProfissao()).isEqualTo(profissao);

        profissao.setFuncionarios(new HashSet<>());
        assertThat(profissao.getFuncionarios()).doesNotContain(funcionarioBack);
        assertThat(funcionarioBack.getProfissao()).isNull();
    }

    @Test
    void socioTest() {
        Profissao profissao = getProfissaoRandomSampleGenerator();
        Socio socioBack = getSocioRandomSampleGenerator();

        profissao.addSocio(socioBack);
        assertThat(profissao.getSocios()).containsOnly(socioBack);
        assertThat(socioBack.getProfissao()).isEqualTo(profissao);

        profissao.removeSocio(socioBack);
        assertThat(profissao.getSocios()).doesNotContain(socioBack);
        assertThat(socioBack.getProfissao()).isNull();

        profissao.socios(new HashSet<>(Set.of(socioBack)));
        assertThat(profissao.getSocios()).containsOnly(socioBack);
        assertThat(socioBack.getProfissao()).isEqualTo(profissao);

        profissao.setSocios(new HashSet<>());
        assertThat(profissao.getSocios()).doesNotContain(socioBack);
        assertThat(socioBack.getProfissao()).isNull();
    }
}
