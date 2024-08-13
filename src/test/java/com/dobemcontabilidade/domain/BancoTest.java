package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.BancoPessoaTestSamples.*;
import static com.dobemcontabilidade.domain.BancoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class BancoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Banco.class);
        Banco banco1 = getBancoSample1();
        Banco banco2 = new Banco();
        assertThat(banco1).isNotEqualTo(banco2);

        banco2.setId(banco1.getId());
        assertThat(banco1).isEqualTo(banco2);

        banco2 = getBancoSample2();
        assertThat(banco1).isNotEqualTo(banco2);
    }

    @Test
    void bancoPessoaTest() {
        Banco banco = getBancoRandomSampleGenerator();
        BancoPessoa bancoPessoaBack = getBancoPessoaRandomSampleGenerator();

        banco.addBancoPessoa(bancoPessoaBack);
        assertThat(banco.getBancoPessoas()).containsOnly(bancoPessoaBack);
        assertThat(bancoPessoaBack.getBanco()).isEqualTo(banco);

        banco.removeBancoPessoa(bancoPessoaBack);
        assertThat(banco.getBancoPessoas()).doesNotContain(bancoPessoaBack);
        assertThat(bancoPessoaBack.getBanco()).isNull();

        banco.bancoPessoas(new HashSet<>(Set.of(bancoPessoaBack)));
        assertThat(banco.getBancoPessoas()).containsOnly(bancoPessoaBack);
        assertThat(bancoPessoaBack.getBanco()).isEqualTo(banco);

        banco.setBancoPessoas(new HashSet<>());
        assertThat(banco.getBancoPessoas()).doesNotContain(bancoPessoaBack);
        assertThat(bancoPessoaBack.getBanco()).isNull();
    }
}
