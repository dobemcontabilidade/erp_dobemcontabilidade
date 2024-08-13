package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AvaliacaoContadorTestSamples.*;
import static com.dobemcontabilidade.domain.AvaliacaoTestSamples.*;
import static com.dobemcontabilidade.domain.ContadorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AvaliacaoContadorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AvaliacaoContador.class);
        AvaliacaoContador avaliacaoContador1 = getAvaliacaoContadorSample1();
        AvaliacaoContador avaliacaoContador2 = new AvaliacaoContador();
        assertThat(avaliacaoContador1).isNotEqualTo(avaliacaoContador2);

        avaliacaoContador2.setId(avaliacaoContador1.getId());
        assertThat(avaliacaoContador1).isEqualTo(avaliacaoContador2);

        avaliacaoContador2 = getAvaliacaoContadorSample2();
        assertThat(avaliacaoContador1).isNotEqualTo(avaliacaoContador2);
    }

    @Test
    void contadorTest() {
        AvaliacaoContador avaliacaoContador = getAvaliacaoContadorRandomSampleGenerator();
        Contador contadorBack = getContadorRandomSampleGenerator();

        avaliacaoContador.setContador(contadorBack);
        assertThat(avaliacaoContador.getContador()).isEqualTo(contadorBack);

        avaliacaoContador.contador(null);
        assertThat(avaliacaoContador.getContador()).isNull();
    }

    @Test
    void avaliacaoTest() {
        AvaliacaoContador avaliacaoContador = getAvaliacaoContadorRandomSampleGenerator();
        Avaliacao avaliacaoBack = getAvaliacaoRandomSampleGenerator();

        avaliacaoContador.setAvaliacao(avaliacaoBack);
        assertThat(avaliacaoContador.getAvaliacao()).isEqualTo(avaliacaoBack);

        avaliacaoContador.avaliacao(null);
        assertThat(avaliacaoContador.getAvaliacao()).isNull();
    }
}
