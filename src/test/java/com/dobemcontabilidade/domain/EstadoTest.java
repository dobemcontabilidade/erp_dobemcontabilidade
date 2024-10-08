package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.CidadeTestSamples.*;
import static com.dobemcontabilidade.domain.EstadoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EstadoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Estado.class);
        Estado estado1 = getEstadoSample1();
        Estado estado2 = new Estado();
        assertThat(estado1).isNotEqualTo(estado2);

        estado2.setId(estado1.getId());
        assertThat(estado1).isEqualTo(estado2);

        estado2 = getEstadoSample2();
        assertThat(estado1).isNotEqualTo(estado2);
    }

    @Test
    void cidadeTest() {
        Estado estado = getEstadoRandomSampleGenerator();
        Cidade cidadeBack = getCidadeRandomSampleGenerator();

        estado.addCidade(cidadeBack);
        assertThat(estado.getCidades()).containsOnly(cidadeBack);
        assertThat(cidadeBack.getEstado()).isEqualTo(estado);

        estado.removeCidade(cidadeBack);
        assertThat(estado.getCidades()).doesNotContain(cidadeBack);
        assertThat(cidadeBack.getEstado()).isNull();

        estado.cidades(new HashSet<>(Set.of(cidadeBack)));
        assertThat(estado.getCidades()).containsOnly(cidadeBack);
        assertThat(cidadeBack.getEstado()).isEqualTo(estado);

        estado.setCidades(new HashSet<>());
        assertThat(estado.getCidades()).doesNotContain(cidadeBack);
        assertThat(cidadeBack.getEstado()).isNull();
    }
}
