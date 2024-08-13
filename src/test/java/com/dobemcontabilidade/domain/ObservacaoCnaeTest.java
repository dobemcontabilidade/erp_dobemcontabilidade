package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.ObservacaoCnaeTestSamples.*;
import static com.dobemcontabilidade.domain.SubclasseCnaeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ObservacaoCnaeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ObservacaoCnae.class);
        ObservacaoCnae observacaoCnae1 = getObservacaoCnaeSample1();
        ObservacaoCnae observacaoCnae2 = new ObservacaoCnae();
        assertThat(observacaoCnae1).isNotEqualTo(observacaoCnae2);

        observacaoCnae2.setId(observacaoCnae1.getId());
        assertThat(observacaoCnae1).isEqualTo(observacaoCnae2);

        observacaoCnae2 = getObservacaoCnaeSample2();
        assertThat(observacaoCnae1).isNotEqualTo(observacaoCnae2);
    }

    @Test
    void subclasseTest() {
        ObservacaoCnae observacaoCnae = getObservacaoCnaeRandomSampleGenerator();
        SubclasseCnae subclasseCnaeBack = getSubclasseCnaeRandomSampleGenerator();

        observacaoCnae.setSubclasse(subclasseCnaeBack);
        assertThat(observacaoCnae.getSubclasse()).isEqualTo(subclasseCnaeBack);

        observacaoCnae.subclasse(null);
        assertThat(observacaoCnae.getSubclasse()).isNull();
    }
}
