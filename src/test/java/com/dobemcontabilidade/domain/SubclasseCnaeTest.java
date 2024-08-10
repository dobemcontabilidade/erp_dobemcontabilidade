package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AtividadeEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.ClasseCnaeTestSamples.*;
import static com.dobemcontabilidade.domain.ObservacaoCnaeTestSamples.*;
import static com.dobemcontabilidade.domain.SegmentoCnaeTestSamples.*;
import static com.dobemcontabilidade.domain.SubclasseCnaeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class SubclasseCnaeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubclasseCnae.class);
        SubclasseCnae subclasseCnae1 = getSubclasseCnaeSample1();
        SubclasseCnae subclasseCnae2 = new SubclasseCnae();
        assertThat(subclasseCnae1).isNotEqualTo(subclasseCnae2);

        subclasseCnae2.setId(subclasseCnae1.getId());
        assertThat(subclasseCnae1).isEqualTo(subclasseCnae2);

        subclasseCnae2 = getSubclasseCnaeSample2();
        assertThat(subclasseCnae1).isNotEqualTo(subclasseCnae2);
    }

    @Test
    void observacaoCnaeTest() {
        SubclasseCnae subclasseCnae = getSubclasseCnaeRandomSampleGenerator();
        ObservacaoCnae observacaoCnaeBack = getObservacaoCnaeRandomSampleGenerator();

        subclasseCnae.addObservacaoCnae(observacaoCnaeBack);
        assertThat(subclasseCnae.getObservacaoCnaes()).containsOnly(observacaoCnaeBack);
        assertThat(observacaoCnaeBack.getSubclasse()).isEqualTo(subclasseCnae);

        subclasseCnae.removeObservacaoCnae(observacaoCnaeBack);
        assertThat(subclasseCnae.getObservacaoCnaes()).doesNotContain(observacaoCnaeBack);
        assertThat(observacaoCnaeBack.getSubclasse()).isNull();

        subclasseCnae.observacaoCnaes(new HashSet<>(Set.of(observacaoCnaeBack)));
        assertThat(subclasseCnae.getObservacaoCnaes()).containsOnly(observacaoCnaeBack);
        assertThat(observacaoCnaeBack.getSubclasse()).isEqualTo(subclasseCnae);

        subclasseCnae.setObservacaoCnaes(new HashSet<>());
        assertThat(subclasseCnae.getObservacaoCnaes()).doesNotContain(observacaoCnaeBack);
        assertThat(observacaoCnaeBack.getSubclasse()).isNull();
    }

    @Test
    void atividadeEmpresaTest() {
        SubclasseCnae subclasseCnae = getSubclasseCnaeRandomSampleGenerator();
        AtividadeEmpresa atividadeEmpresaBack = getAtividadeEmpresaRandomSampleGenerator();

        subclasseCnae.addAtividadeEmpresa(atividadeEmpresaBack);
        assertThat(subclasseCnae.getAtividadeEmpresas()).containsOnly(atividadeEmpresaBack);
        assertThat(atividadeEmpresaBack.getCnae()).isEqualTo(subclasseCnae);

        subclasseCnae.removeAtividadeEmpresa(atividadeEmpresaBack);
        assertThat(subclasseCnae.getAtividadeEmpresas()).doesNotContain(atividadeEmpresaBack);
        assertThat(atividadeEmpresaBack.getCnae()).isNull();

        subclasseCnae.atividadeEmpresas(new HashSet<>(Set.of(atividadeEmpresaBack)));
        assertThat(subclasseCnae.getAtividadeEmpresas()).containsOnly(atividadeEmpresaBack);
        assertThat(atividadeEmpresaBack.getCnae()).isEqualTo(subclasseCnae);

        subclasseCnae.setAtividadeEmpresas(new HashSet<>());
        assertThat(subclasseCnae.getAtividadeEmpresas()).doesNotContain(atividadeEmpresaBack);
        assertThat(atividadeEmpresaBack.getCnae()).isNull();
    }

    @Test
    void classeTest() {
        SubclasseCnae subclasseCnae = getSubclasseCnaeRandomSampleGenerator();
        ClasseCnae classeCnaeBack = getClasseCnaeRandomSampleGenerator();

        subclasseCnae.setClasse(classeCnaeBack);
        assertThat(subclasseCnae.getClasse()).isEqualTo(classeCnaeBack);

        subclasseCnae.classe(null);
        assertThat(subclasseCnae.getClasse()).isNull();
    }

    @Test
    void segmentoCnaeTest() {
        SubclasseCnae subclasseCnae = getSubclasseCnaeRandomSampleGenerator();
        SegmentoCnae segmentoCnaeBack = getSegmentoCnaeRandomSampleGenerator();

        subclasseCnae.addSegmentoCnae(segmentoCnaeBack);
        assertThat(subclasseCnae.getSegmentoCnaes()).containsOnly(segmentoCnaeBack);
        assertThat(segmentoCnaeBack.getSubclasseCnaes()).containsOnly(subclasseCnae);

        subclasseCnae.removeSegmentoCnae(segmentoCnaeBack);
        assertThat(subclasseCnae.getSegmentoCnaes()).doesNotContain(segmentoCnaeBack);
        assertThat(segmentoCnaeBack.getSubclasseCnaes()).doesNotContain(subclasseCnae);

        subclasseCnae.segmentoCnaes(new HashSet<>(Set.of(segmentoCnaeBack)));
        assertThat(subclasseCnae.getSegmentoCnaes()).containsOnly(segmentoCnaeBack);
        assertThat(segmentoCnaeBack.getSubclasseCnaes()).containsOnly(subclasseCnae);

        subclasseCnae.setSegmentoCnaes(new HashSet<>());
        assertThat(subclasseCnae.getSegmentoCnaes()).doesNotContain(segmentoCnaeBack);
        assertThat(segmentoCnaeBack.getSubclasseCnaes()).doesNotContain(subclasseCnae);
    }
}
