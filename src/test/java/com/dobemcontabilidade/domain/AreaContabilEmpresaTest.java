package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AreaContabilEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.ContadorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AreaContabilEmpresaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AreaContabilEmpresa.class);
        AreaContabilEmpresa areaContabilEmpresa1 = getAreaContabilEmpresaSample1();
        AreaContabilEmpresa areaContabilEmpresa2 = new AreaContabilEmpresa();
        assertThat(areaContabilEmpresa1).isNotEqualTo(areaContabilEmpresa2);

        areaContabilEmpresa2.setId(areaContabilEmpresa1.getId());
        assertThat(areaContabilEmpresa1).isEqualTo(areaContabilEmpresa2);

        areaContabilEmpresa2 = getAreaContabilEmpresaSample2();
        assertThat(areaContabilEmpresa1).isNotEqualTo(areaContabilEmpresa2);
    }

    @Test
    void contadorTest() {
        AreaContabilEmpresa areaContabilEmpresa = getAreaContabilEmpresaRandomSampleGenerator();
        Contador contadorBack = getContadorRandomSampleGenerator();

        areaContabilEmpresa.setContador(contadorBack);
        assertThat(areaContabilEmpresa.getContador()).isEqualTo(contadorBack);

        areaContabilEmpresa.contador(null);
        assertThat(areaContabilEmpresa.getContador()).isNull();
    }
}
