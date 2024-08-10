package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.BancoContadorTestSamples.*;
import static com.dobemcontabilidade.domain.BancoTestSamples.*;
import static com.dobemcontabilidade.domain.ContadorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BancoContadorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BancoContador.class);
        BancoContador bancoContador1 = getBancoContadorSample1();
        BancoContador bancoContador2 = new BancoContador();
        assertThat(bancoContador1).isNotEqualTo(bancoContador2);

        bancoContador2.setId(bancoContador1.getId());
        assertThat(bancoContador1).isEqualTo(bancoContador2);

        bancoContador2 = getBancoContadorSample2();
        assertThat(bancoContador1).isNotEqualTo(bancoContador2);
    }

    @Test
    void contadorTest() {
        BancoContador bancoContador = getBancoContadorRandomSampleGenerator();
        Contador contadorBack = getContadorRandomSampleGenerator();

        bancoContador.setContador(contadorBack);
        assertThat(bancoContador.getContador()).isEqualTo(contadorBack);

        bancoContador.contador(null);
        assertThat(bancoContador.getContador()).isNull();
    }

    @Test
    void bancoTest() {
        BancoContador bancoContador = getBancoContadorRandomSampleGenerator();
        Banco bancoBack = getBancoRandomSampleGenerator();

        bancoContador.setBanco(bancoBack);
        assertThat(bancoContador.getBanco()).isEqualTo(bancoBack);

        bancoContador.banco(null);
        assertThat(bancoContador.getBanco()).isNull();
    }
}
