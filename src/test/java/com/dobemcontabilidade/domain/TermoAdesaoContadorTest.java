package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.ContadorTestSamples.*;
import static com.dobemcontabilidade.domain.TermoAdesaoContadorTestSamples.*;
import static com.dobemcontabilidade.domain.TermoDeAdesaoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TermoAdesaoContadorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TermoAdesaoContador.class);
        TermoAdesaoContador termoAdesaoContador1 = getTermoAdesaoContadorSample1();
        TermoAdesaoContador termoAdesaoContador2 = new TermoAdesaoContador();
        assertThat(termoAdesaoContador1).isNotEqualTo(termoAdesaoContador2);

        termoAdesaoContador2.setId(termoAdesaoContador1.getId());
        assertThat(termoAdesaoContador1).isEqualTo(termoAdesaoContador2);

        termoAdesaoContador2 = getTermoAdesaoContadorSample2();
        assertThat(termoAdesaoContador1).isNotEqualTo(termoAdesaoContador2);
    }

    @Test
    void contadorTest() {
        TermoAdesaoContador termoAdesaoContador = getTermoAdesaoContadorRandomSampleGenerator();
        Contador contadorBack = getContadorRandomSampleGenerator();

        termoAdesaoContador.setContador(contadorBack);
        assertThat(termoAdesaoContador.getContador()).isEqualTo(contadorBack);

        termoAdesaoContador.contador(null);
        assertThat(termoAdesaoContador.getContador()).isNull();
    }

    @Test
    void termoDeAdesaoTest() {
        TermoAdesaoContador termoAdesaoContador = getTermoAdesaoContadorRandomSampleGenerator();
        TermoDeAdesao termoDeAdesaoBack = getTermoDeAdesaoRandomSampleGenerator();

        termoAdesaoContador.setTermoDeAdesao(termoDeAdesaoBack);
        assertThat(termoAdesaoContador.getTermoDeAdesao()).isEqualTo(termoDeAdesaoBack);

        termoAdesaoContador.termoDeAdesao(null);
        assertThat(termoAdesaoContador.getTermoDeAdesao()).isNull();
    }
}
