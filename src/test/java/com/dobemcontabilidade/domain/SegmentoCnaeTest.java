package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.EmpresaModeloTestSamples.*;
import static com.dobemcontabilidade.domain.EmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.RamoTestSamples.*;
import static com.dobemcontabilidade.domain.SegmentoCnaeTestSamples.*;
import static com.dobemcontabilidade.domain.SubclasseCnaeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class SegmentoCnaeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SegmentoCnae.class);
        SegmentoCnae segmentoCnae1 = getSegmentoCnaeSample1();
        SegmentoCnae segmentoCnae2 = new SegmentoCnae();
        assertThat(segmentoCnae1).isNotEqualTo(segmentoCnae2);

        segmentoCnae2.setId(segmentoCnae1.getId());
        assertThat(segmentoCnae1).isEqualTo(segmentoCnae2);

        segmentoCnae2 = getSegmentoCnaeSample2();
        assertThat(segmentoCnae1).isNotEqualTo(segmentoCnae2);
    }

    @Test
    void subclasseCnaeTest() {
        SegmentoCnae segmentoCnae = getSegmentoCnaeRandomSampleGenerator();
        SubclasseCnae subclasseCnaeBack = getSubclasseCnaeRandomSampleGenerator();

        segmentoCnae.addSubclasseCnae(subclasseCnaeBack);
        assertThat(segmentoCnae.getSubclasseCnaes()).containsOnly(subclasseCnaeBack);

        segmentoCnae.removeSubclasseCnae(subclasseCnaeBack);
        assertThat(segmentoCnae.getSubclasseCnaes()).doesNotContain(subclasseCnaeBack);

        segmentoCnae.subclasseCnaes(new HashSet<>(Set.of(subclasseCnaeBack)));
        assertThat(segmentoCnae.getSubclasseCnaes()).containsOnly(subclasseCnaeBack);

        segmentoCnae.setSubclasseCnaes(new HashSet<>());
        assertThat(segmentoCnae.getSubclasseCnaes()).doesNotContain(subclasseCnaeBack);
    }

    @Test
    void ramoTest() {
        SegmentoCnae segmentoCnae = getSegmentoCnaeRandomSampleGenerator();
        Ramo ramoBack = getRamoRandomSampleGenerator();

        segmentoCnae.setRamo(ramoBack);
        assertThat(segmentoCnae.getRamo()).isEqualTo(ramoBack);

        segmentoCnae.ramo(null);
        assertThat(segmentoCnae.getRamo()).isNull();
    }

    @Test
    void empresaTest() {
        SegmentoCnae segmentoCnae = getSegmentoCnaeRandomSampleGenerator();
        Empresa empresaBack = getEmpresaRandomSampleGenerator();

        segmentoCnae.addEmpresa(empresaBack);
        assertThat(segmentoCnae.getEmpresas()).containsOnly(empresaBack);
        assertThat(empresaBack.getSegmentoCnaes()).containsOnly(segmentoCnae);

        segmentoCnae.removeEmpresa(empresaBack);
        assertThat(segmentoCnae.getEmpresas()).doesNotContain(empresaBack);
        assertThat(empresaBack.getSegmentoCnaes()).doesNotContain(segmentoCnae);

        segmentoCnae.empresas(new HashSet<>(Set.of(empresaBack)));
        assertThat(segmentoCnae.getEmpresas()).containsOnly(empresaBack);
        assertThat(empresaBack.getSegmentoCnaes()).containsOnly(segmentoCnae);

        segmentoCnae.setEmpresas(new HashSet<>());
        assertThat(segmentoCnae.getEmpresas()).doesNotContain(empresaBack);
        assertThat(empresaBack.getSegmentoCnaes()).doesNotContain(segmentoCnae);
    }

    @Test
    void empresaModeloTest() {
        SegmentoCnae segmentoCnae = getSegmentoCnaeRandomSampleGenerator();
        EmpresaModelo empresaModeloBack = getEmpresaModeloRandomSampleGenerator();

        segmentoCnae.addEmpresaModelo(empresaModeloBack);
        assertThat(segmentoCnae.getEmpresaModelos()).containsOnly(empresaModeloBack);
        assertThat(empresaModeloBack.getSegmentoCnaes()).containsOnly(segmentoCnae);

        segmentoCnae.removeEmpresaModelo(empresaModeloBack);
        assertThat(segmentoCnae.getEmpresaModelos()).doesNotContain(empresaModeloBack);
        assertThat(empresaModeloBack.getSegmentoCnaes()).doesNotContain(segmentoCnae);

        segmentoCnae.empresaModelos(new HashSet<>(Set.of(empresaModeloBack)));
        assertThat(segmentoCnae.getEmpresaModelos()).containsOnly(empresaModeloBack);
        assertThat(empresaModeloBack.getSegmentoCnaes()).containsOnly(segmentoCnae);

        segmentoCnae.setEmpresaModelos(new HashSet<>());
        assertThat(segmentoCnae.getEmpresaModelos()).doesNotContain(empresaModeloBack);
        assertThat(empresaModeloBack.getSegmentoCnaes()).doesNotContain(segmentoCnae);
    }
}
