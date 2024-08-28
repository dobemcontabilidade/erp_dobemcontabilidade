package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AdicionalRamoTestSamples.*;
import static com.dobemcontabilidade.domain.EmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.RamoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class RamoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ramo.class);
        Ramo ramo1 = getRamoSample1();
        Ramo ramo2 = new Ramo();
        assertThat(ramo1).isNotEqualTo(ramo2);

        ramo2.setId(ramo1.getId());
        assertThat(ramo1).isEqualTo(ramo2);

        ramo2 = getRamoSample2();
        assertThat(ramo1).isNotEqualTo(ramo2);
    }

    @Test
    void empresaTest() {
        Ramo ramo = getRamoRandomSampleGenerator();
        Empresa empresaBack = getEmpresaRandomSampleGenerator();

        ramo.addEmpresa(empresaBack);
        assertThat(ramo.getEmpresas()).containsOnly(empresaBack);
        assertThat(empresaBack.getRamo()).isEqualTo(ramo);

        ramo.removeEmpresa(empresaBack);
        assertThat(ramo.getEmpresas()).doesNotContain(empresaBack);
        assertThat(empresaBack.getRamo()).isNull();

        ramo.empresas(new HashSet<>(Set.of(empresaBack)));
        assertThat(ramo.getEmpresas()).containsOnly(empresaBack);
        assertThat(empresaBack.getRamo()).isEqualTo(ramo);

        ramo.setEmpresas(new HashSet<>());
        assertThat(ramo.getEmpresas()).doesNotContain(empresaBack);
        assertThat(empresaBack.getRamo()).isNull();
    }

    @Test
    void adicionalRamoTest() {
        Ramo ramo = getRamoRandomSampleGenerator();
        AdicionalRamo adicionalRamoBack = getAdicionalRamoRandomSampleGenerator();

        ramo.addAdicionalRamo(adicionalRamoBack);
        assertThat(ramo.getAdicionalRamos()).containsOnly(adicionalRamoBack);
        assertThat(adicionalRamoBack.getRamo()).isEqualTo(ramo);

        ramo.removeAdicionalRamo(adicionalRamoBack);
        assertThat(ramo.getAdicionalRamos()).doesNotContain(adicionalRamoBack);
        assertThat(adicionalRamoBack.getRamo()).isNull();

        ramo.adicionalRamos(new HashSet<>(Set.of(adicionalRamoBack)));
        assertThat(ramo.getAdicionalRamos()).containsOnly(adicionalRamoBack);
        assertThat(adicionalRamoBack.getRamo()).isEqualTo(ramo);

        ramo.setAdicionalRamos(new HashSet<>());
        assertThat(ramo.getAdicionalRamos()).doesNotContain(adicionalRamoBack);
        assertThat(adicionalRamoBack.getRamo()).isNull();
    }
}
