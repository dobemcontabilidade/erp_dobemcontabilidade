package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.EmpresaVinculadaTestSamples.*;
import static com.dobemcontabilidade.domain.FuncionarioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmpresaVinculadaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmpresaVinculada.class);
        EmpresaVinculada empresaVinculada1 = getEmpresaVinculadaSample1();
        EmpresaVinculada empresaVinculada2 = new EmpresaVinculada();
        assertThat(empresaVinculada1).isNotEqualTo(empresaVinculada2);

        empresaVinculada2.setId(empresaVinculada1.getId());
        assertThat(empresaVinculada1).isEqualTo(empresaVinculada2);

        empresaVinculada2 = getEmpresaVinculadaSample2();
        assertThat(empresaVinculada1).isNotEqualTo(empresaVinculada2);
    }

    @Test
    void funcionarioTest() {
        EmpresaVinculada empresaVinculada = getEmpresaVinculadaRandomSampleGenerator();
        Funcionario funcionarioBack = getFuncionarioRandomSampleGenerator();

        empresaVinculada.setFuncionario(funcionarioBack);
        assertThat(empresaVinculada.getFuncionario()).isEqualTo(funcionarioBack);

        empresaVinculada.funcionario(null);
        assertThat(empresaVinculada.getFuncionario()).isNull();
    }
}
