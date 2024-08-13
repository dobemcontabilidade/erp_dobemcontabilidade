package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AreaContabilContadorTestSamples.*;
import static com.dobemcontabilidade.domain.AreaContabilTestSamples.*;
import static com.dobemcontabilidade.domain.ContadorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AreaContabilContadorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AreaContabilContador.class);
        AreaContabilContador areaContabilContador1 = getAreaContabilContadorSample1();
        AreaContabilContador areaContabilContador2 = new AreaContabilContador();
        assertThat(areaContabilContador1).isNotEqualTo(areaContabilContador2);

        areaContabilContador2.setId(areaContabilContador1.getId());
        assertThat(areaContabilContador1).isEqualTo(areaContabilContador2);

        areaContabilContador2 = getAreaContabilContadorSample2();
        assertThat(areaContabilContador1).isNotEqualTo(areaContabilContador2);
    }

    @Test
    void contadorTest() {
        AreaContabilContador areaContabilContador = getAreaContabilContadorRandomSampleGenerator();
        Contador contadorBack = getContadorRandomSampleGenerator();

        areaContabilContador.setContador(contadorBack);
        assertThat(areaContabilContador.getContador()).isEqualTo(contadorBack);

        areaContabilContador.contador(null);
        assertThat(areaContabilContador.getContador()).isNull();
    }

    @Test
    void areaContabilTest() {
        AreaContabilContador areaContabilContador = getAreaContabilContadorRandomSampleGenerator();
        AreaContabil areaContabilBack = getAreaContabilRandomSampleGenerator();

        areaContabilContador.setAreaContabil(areaContabilBack);
        assertThat(areaContabilContador.getAreaContabil()).isEqualTo(areaContabilBack);

        areaContabilContador.areaContabil(null);
        assertThat(areaContabilContador.getAreaContabil()).isNull();
    }
}
