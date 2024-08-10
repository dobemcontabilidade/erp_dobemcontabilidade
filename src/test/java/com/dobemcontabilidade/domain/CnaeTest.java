package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.CnaeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CnaeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cnae.class);
        Cnae cnae1 = getCnaeSample1();
        Cnae cnae2 = new Cnae();
        assertThat(cnae1).isNotEqualTo(cnae2);

        cnae2.setId(cnae1.getId());
        assertThat(cnae1).isEqualTo(cnae2);

        cnae2 = getCnaeSample2();
        assertThat(cnae1).isNotEqualTo(cnae2);
    }
}
