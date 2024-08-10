package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AdicionalRamoTestSamples.*;
import static com.dobemcontabilidade.domain.CalculoPlanoAssinaturaTestSamples.*;
import static com.dobemcontabilidade.domain.EmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.RamoTestSamples.*;
import static com.dobemcontabilidade.domain.SegmentoCnaeTestSamples.*;
import static com.dobemcontabilidade.domain.ValorBaseRamoTestSamples.*;
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
    void calculoPlanoAssinaturaTest() {
        Ramo ramo = getRamoRandomSampleGenerator();
        CalculoPlanoAssinatura calculoPlanoAssinaturaBack = getCalculoPlanoAssinaturaRandomSampleGenerator();

        ramo.addCalculoPlanoAssinatura(calculoPlanoAssinaturaBack);
        assertThat(ramo.getCalculoPlanoAssinaturas()).containsOnly(calculoPlanoAssinaturaBack);
        assertThat(calculoPlanoAssinaturaBack.getRamo()).isEqualTo(ramo);

        ramo.removeCalculoPlanoAssinatura(calculoPlanoAssinaturaBack);
        assertThat(ramo.getCalculoPlanoAssinaturas()).doesNotContain(calculoPlanoAssinaturaBack);
        assertThat(calculoPlanoAssinaturaBack.getRamo()).isNull();

        ramo.calculoPlanoAssinaturas(new HashSet<>(Set.of(calculoPlanoAssinaturaBack)));
        assertThat(ramo.getCalculoPlanoAssinaturas()).containsOnly(calculoPlanoAssinaturaBack);
        assertThat(calculoPlanoAssinaturaBack.getRamo()).isEqualTo(ramo);

        ramo.setCalculoPlanoAssinaturas(new HashSet<>());
        assertThat(ramo.getCalculoPlanoAssinaturas()).doesNotContain(calculoPlanoAssinaturaBack);
        assertThat(calculoPlanoAssinaturaBack.getRamo()).isNull();
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

    @Test
    void valorBaseRamoTest() {
        Ramo ramo = getRamoRandomSampleGenerator();
        ValorBaseRamo valorBaseRamoBack = getValorBaseRamoRandomSampleGenerator();

        ramo.addValorBaseRamo(valorBaseRamoBack);
        assertThat(ramo.getValorBaseRamos()).containsOnly(valorBaseRamoBack);
        assertThat(valorBaseRamoBack.getRamo()).isEqualTo(ramo);

        ramo.removeValorBaseRamo(valorBaseRamoBack);
        assertThat(ramo.getValorBaseRamos()).doesNotContain(valorBaseRamoBack);
        assertThat(valorBaseRamoBack.getRamo()).isNull();

        ramo.valorBaseRamos(new HashSet<>(Set.of(valorBaseRamoBack)));
        assertThat(ramo.getValorBaseRamos()).containsOnly(valorBaseRamoBack);
        assertThat(valorBaseRamoBack.getRamo()).isEqualTo(ramo);

        ramo.setValorBaseRamos(new HashSet<>());
        assertThat(ramo.getValorBaseRamos()).doesNotContain(valorBaseRamoBack);
        assertThat(valorBaseRamoBack.getRamo()).isNull();
    }

    @Test
    void segmentoCnaeTest() {
        Ramo ramo = getRamoRandomSampleGenerator();
        SegmentoCnae segmentoCnaeBack = getSegmentoCnaeRandomSampleGenerator();

        ramo.addSegmentoCnae(segmentoCnaeBack);
        assertThat(ramo.getSegmentoCnaes()).containsOnly(segmentoCnaeBack);
        assertThat(segmentoCnaeBack.getRamo()).isEqualTo(ramo);

        ramo.removeSegmentoCnae(segmentoCnaeBack);
        assertThat(ramo.getSegmentoCnaes()).doesNotContain(segmentoCnaeBack);
        assertThat(segmentoCnaeBack.getRamo()).isNull();

        ramo.segmentoCnaes(new HashSet<>(Set.of(segmentoCnaeBack)));
        assertThat(ramo.getSegmentoCnaes()).containsOnly(segmentoCnaeBack);
        assertThat(segmentoCnaeBack.getRamo()).isEqualTo(ramo);

        ramo.setSegmentoCnaes(new HashSet<>());
        assertThat(ramo.getSegmentoCnaes()).doesNotContain(segmentoCnaeBack);
        assertThat(segmentoCnaeBack.getRamo()).isNull();
    }
}
