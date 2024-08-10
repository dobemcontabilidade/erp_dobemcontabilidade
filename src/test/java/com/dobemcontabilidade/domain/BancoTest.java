package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.BancoContadorTestSamples.*;
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
    void bancoContadorTest() {
        Banco banco = getBancoRandomSampleGenerator();
        BancoContador bancoContadorBack = getBancoContadorRandomSampleGenerator();

        banco.addBancoContador(bancoContadorBack);
        assertThat(banco.getBancoContadors()).containsOnly(bancoContadorBack);
        assertThat(bancoContadorBack.getBanco()).isEqualTo(banco);

        banco.removeBancoContador(bancoContadorBack);
        assertThat(banco.getBancoContadors()).doesNotContain(bancoContadorBack);
        assertThat(bancoContadorBack.getBanco()).isNull();

        banco.bancoContadors(new HashSet<>(Set.of(bancoContadorBack)));
        assertThat(banco.getBancoContadors()).containsOnly(bancoContadorBack);
        assertThat(bancoContadorBack.getBanco()).isEqualTo(banco);

        banco.setBancoContadors(new HashSet<>());
        assertThat(banco.getBancoContadors()).doesNotContain(bancoContadorBack);
        assertThat(bancoContadorBack.getBanco()).isNull();
    }
}
