package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.CidadeTestSamples.*;
import static com.dobemcontabilidade.domain.EmpresaTestSamples.*;
import static com.dobemcontabilidade.domain.EnderecoEmpresaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EnderecoEmpresaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EnderecoEmpresa.class);
        EnderecoEmpresa enderecoEmpresa1 = getEnderecoEmpresaSample1();
        EnderecoEmpresa enderecoEmpresa2 = new EnderecoEmpresa();
        assertThat(enderecoEmpresa1).isNotEqualTo(enderecoEmpresa2);

        enderecoEmpresa2.setId(enderecoEmpresa1.getId());
        assertThat(enderecoEmpresa1).isEqualTo(enderecoEmpresa2);

        enderecoEmpresa2 = getEnderecoEmpresaSample2();
        assertThat(enderecoEmpresa1).isNotEqualTo(enderecoEmpresa2);
    }

    @Test
    void empresaTest() {
        EnderecoEmpresa enderecoEmpresa = getEnderecoEmpresaRandomSampleGenerator();
        Empresa empresaBack = getEmpresaRandomSampleGenerator();

        enderecoEmpresa.setEmpresa(empresaBack);
        assertThat(enderecoEmpresa.getEmpresa()).isEqualTo(empresaBack);

        enderecoEmpresa.empresa(null);
        assertThat(enderecoEmpresa.getEmpresa()).isNull();
    }

    @Test
    void cidadeTest() {
        EnderecoEmpresa enderecoEmpresa = getEnderecoEmpresaRandomSampleGenerator();
        Cidade cidadeBack = getCidadeRandomSampleGenerator();

        enderecoEmpresa.setCidade(cidadeBack);
        assertThat(enderecoEmpresa.getCidade()).isEqualTo(cidadeBack);

        enderecoEmpresa.cidade(null);
        assertThat(enderecoEmpresa.getCidade()).isNull();
    }
}
