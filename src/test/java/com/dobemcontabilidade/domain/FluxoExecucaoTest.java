package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.FluxoExecucaoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FluxoExecucaoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FluxoExecucao.class);
        FluxoExecucao fluxoExecucao1 = getFluxoExecucaoSample1();
        FluxoExecucao fluxoExecucao2 = new FluxoExecucao();
        assertThat(fluxoExecucao1).isNotEqualTo(fluxoExecucao2);

        fluxoExecucao2.setId(fluxoExecucao1.getId());
        assertThat(fluxoExecucao1).isEqualTo(fluxoExecucao2);

        fluxoExecucao2 = getFluxoExecucaoSample2();
        assertThat(fluxoExecucao1).isNotEqualTo(fluxoExecucao2);
    }
}
