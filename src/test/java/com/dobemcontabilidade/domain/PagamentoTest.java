package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AssinaturaEmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.PagamentoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PagamentoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pagamento.class);
        Pagamento pagamento1 = getPagamentoSample1();
        Pagamento pagamento2 = new Pagamento();
        assertThat(pagamento1).isNotEqualTo(pagamento2);

        pagamento2.setId(pagamento1.getId());
        assertThat(pagamento1).isEqualTo(pagamento2);

        pagamento2 = getPagamentoSample2();
        assertThat(pagamento1).isNotEqualTo(pagamento2);
    }

    @Test
    void assinaturaEmpresaTest() {
        Pagamento pagamento = getPagamentoRandomSampleGenerator();
        AssinaturaEmpresa assinaturaEmpresaBack = getAssinaturaEmpresaRandomSampleGenerator();

        pagamento.setAssinaturaEmpresa(assinaturaEmpresaBack);
        assertThat(pagamento.getAssinaturaEmpresa()).isEqualTo(assinaturaEmpresaBack);

        pagamento.assinaturaEmpresa(null);
        assertThat(pagamento.getAssinaturaEmpresa()).isNull();
    }
}
