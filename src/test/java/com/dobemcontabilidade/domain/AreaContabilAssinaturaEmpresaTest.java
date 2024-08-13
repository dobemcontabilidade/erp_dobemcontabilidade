package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AreaContabilAssinaturaEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.AreaContabilTestSamples.*;
import static com.dobemcontabilidade.domain.AssinaturaEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.ContadorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AreaContabilAssinaturaEmpresaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AreaContabilAssinaturaEmpresa.class);
        AreaContabilAssinaturaEmpresa areaContabilAssinaturaEmpresa1 = getAreaContabilAssinaturaEmpresaSample1();
        AreaContabilAssinaturaEmpresa areaContabilAssinaturaEmpresa2 = new AreaContabilAssinaturaEmpresa();
        assertThat(areaContabilAssinaturaEmpresa1).isNotEqualTo(areaContabilAssinaturaEmpresa2);

        areaContabilAssinaturaEmpresa2.setId(areaContabilAssinaturaEmpresa1.getId());
        assertThat(areaContabilAssinaturaEmpresa1).isEqualTo(areaContabilAssinaturaEmpresa2);

        areaContabilAssinaturaEmpresa2 = getAreaContabilAssinaturaEmpresaSample2();
        assertThat(areaContabilAssinaturaEmpresa1).isNotEqualTo(areaContabilAssinaturaEmpresa2);
    }

    @Test
    void areaContabilTest() {
        AreaContabilAssinaturaEmpresa areaContabilAssinaturaEmpresa = getAreaContabilAssinaturaEmpresaRandomSampleGenerator();
        AreaContabil areaContabilBack = getAreaContabilRandomSampleGenerator();

        areaContabilAssinaturaEmpresa.setAreaContabil(areaContabilBack);
        assertThat(areaContabilAssinaturaEmpresa.getAreaContabil()).isEqualTo(areaContabilBack);

        areaContabilAssinaturaEmpresa.areaContabil(null);
        assertThat(areaContabilAssinaturaEmpresa.getAreaContabil()).isNull();
    }

    @Test
    void assinaturaEmpresaTest() {
        AreaContabilAssinaturaEmpresa areaContabilAssinaturaEmpresa = getAreaContabilAssinaturaEmpresaRandomSampleGenerator();
        AssinaturaEmpresa assinaturaEmpresaBack = getAssinaturaEmpresaRandomSampleGenerator();

        areaContabilAssinaturaEmpresa.setAssinaturaEmpresa(assinaturaEmpresaBack);
        assertThat(areaContabilAssinaturaEmpresa.getAssinaturaEmpresa()).isEqualTo(assinaturaEmpresaBack);

        areaContabilAssinaturaEmpresa.assinaturaEmpresa(null);
        assertThat(areaContabilAssinaturaEmpresa.getAssinaturaEmpresa()).isNull();
    }

    @Test
    void contadorTest() {
        AreaContabilAssinaturaEmpresa areaContabilAssinaturaEmpresa = getAreaContabilAssinaturaEmpresaRandomSampleGenerator();
        Contador contadorBack = getContadorRandomSampleGenerator();

        areaContabilAssinaturaEmpresa.setContador(contadorBack);
        assertThat(areaContabilAssinaturaEmpresa.getContador()).isEqualTo(contadorBack);

        areaContabilAssinaturaEmpresa.contador(null);
        assertThat(areaContabilAssinaturaEmpresa.getContador()).isNull();
    }
}
