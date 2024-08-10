package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.AdministradorTestSamples.*;
import static com.dobemcontabilidade.domain.AnexoPessoaTestSamples.*;
import static com.dobemcontabilidade.domain.ContadorTestSamples.*;
import static com.dobemcontabilidade.domain.EmailTestSamples.*;
import static com.dobemcontabilidade.domain.EnderecoPessoaTestSamples.*;
import static com.dobemcontabilidade.domain.FuncionarioTestSamples.*;
import static com.dobemcontabilidade.domain.PessoaTestSamples.*;
import static com.dobemcontabilidade.domain.SocioTestSamples.*;
import static com.dobemcontabilidade.domain.TelefoneTestSamples.*;
import static com.dobemcontabilidade.domain.UsuarioEmpresaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PessoaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pessoa.class);
        Pessoa pessoa1 = getPessoaSample1();
        Pessoa pessoa2 = new Pessoa();
        assertThat(pessoa1).isNotEqualTo(pessoa2);

        pessoa2.setId(pessoa1.getId());
        assertThat(pessoa1).isEqualTo(pessoa2);

        pessoa2 = getPessoaSample2();
        assertThat(pessoa1).isNotEqualTo(pessoa2);
    }

    @Test
    void enderecoPessoaTest() {
        Pessoa pessoa = getPessoaRandomSampleGenerator();
        EnderecoPessoa enderecoPessoaBack = getEnderecoPessoaRandomSampleGenerator();

        pessoa.addEnderecoPessoa(enderecoPessoaBack);
        assertThat(pessoa.getEnderecoPessoas()).containsOnly(enderecoPessoaBack);
        assertThat(enderecoPessoaBack.getPessoa()).isEqualTo(pessoa);

        pessoa.removeEnderecoPessoa(enderecoPessoaBack);
        assertThat(pessoa.getEnderecoPessoas()).doesNotContain(enderecoPessoaBack);
        assertThat(enderecoPessoaBack.getPessoa()).isNull();

        pessoa.enderecoPessoas(new HashSet<>(Set.of(enderecoPessoaBack)));
        assertThat(pessoa.getEnderecoPessoas()).containsOnly(enderecoPessoaBack);
        assertThat(enderecoPessoaBack.getPessoa()).isEqualTo(pessoa);

        pessoa.setEnderecoPessoas(new HashSet<>());
        assertThat(pessoa.getEnderecoPessoas()).doesNotContain(enderecoPessoaBack);
        assertThat(enderecoPessoaBack.getPessoa()).isNull();
    }

    @Test
    void anexoPessoaTest() {
        Pessoa pessoa = getPessoaRandomSampleGenerator();
        AnexoPessoa anexoPessoaBack = getAnexoPessoaRandomSampleGenerator();

        pessoa.addAnexoPessoa(anexoPessoaBack);
        assertThat(pessoa.getAnexoPessoas()).containsOnly(anexoPessoaBack);
        assertThat(anexoPessoaBack.getPessoa()).isEqualTo(pessoa);

        pessoa.removeAnexoPessoa(anexoPessoaBack);
        assertThat(pessoa.getAnexoPessoas()).doesNotContain(anexoPessoaBack);
        assertThat(anexoPessoaBack.getPessoa()).isNull();

        pessoa.anexoPessoas(new HashSet<>(Set.of(anexoPessoaBack)));
        assertThat(pessoa.getAnexoPessoas()).containsOnly(anexoPessoaBack);
        assertThat(anexoPessoaBack.getPessoa()).isEqualTo(pessoa);

        pessoa.setAnexoPessoas(new HashSet<>());
        assertThat(pessoa.getAnexoPessoas()).doesNotContain(anexoPessoaBack);
        assertThat(anexoPessoaBack.getPessoa()).isNull();
    }

    @Test
    void emailTest() {
        Pessoa pessoa = getPessoaRandomSampleGenerator();
        Email emailBack = getEmailRandomSampleGenerator();

        pessoa.addEmail(emailBack);
        assertThat(pessoa.getEmails()).containsOnly(emailBack);
        assertThat(emailBack.getPessoa()).isEqualTo(pessoa);

        pessoa.removeEmail(emailBack);
        assertThat(pessoa.getEmails()).doesNotContain(emailBack);
        assertThat(emailBack.getPessoa()).isNull();

        pessoa.emails(new HashSet<>(Set.of(emailBack)));
        assertThat(pessoa.getEmails()).containsOnly(emailBack);
        assertThat(emailBack.getPessoa()).isEqualTo(pessoa);

        pessoa.setEmails(new HashSet<>());
        assertThat(pessoa.getEmails()).doesNotContain(emailBack);
        assertThat(emailBack.getPessoa()).isNull();
    }

    @Test
    void telefoneTest() {
        Pessoa pessoa = getPessoaRandomSampleGenerator();
        Telefone telefoneBack = getTelefoneRandomSampleGenerator();

        pessoa.addTelefone(telefoneBack);
        assertThat(pessoa.getTelefones()).containsOnly(telefoneBack);
        assertThat(telefoneBack.getPessoa()).isEqualTo(pessoa);

        pessoa.removeTelefone(telefoneBack);
        assertThat(pessoa.getTelefones()).doesNotContain(telefoneBack);
        assertThat(telefoneBack.getPessoa()).isNull();

        pessoa.telefones(new HashSet<>(Set.of(telefoneBack)));
        assertThat(pessoa.getTelefones()).containsOnly(telefoneBack);
        assertThat(telefoneBack.getPessoa()).isEqualTo(pessoa);

        pessoa.setTelefones(new HashSet<>());
        assertThat(pessoa.getTelefones()).doesNotContain(telefoneBack);
        assertThat(telefoneBack.getPessoa()).isNull();
    }

    @Test
    void administradorTest() {
        Pessoa pessoa = getPessoaRandomSampleGenerator();
        Administrador administradorBack = getAdministradorRandomSampleGenerator();

        pessoa.setAdministrador(administradorBack);
        assertThat(pessoa.getAdministrador()).isEqualTo(administradorBack);
        assertThat(administradorBack.getPessoa()).isEqualTo(pessoa);

        pessoa.administrador(null);
        assertThat(pessoa.getAdministrador()).isNull();
        assertThat(administradorBack.getPessoa()).isNull();
    }

    @Test
    void contadorTest() {
        Pessoa pessoa = getPessoaRandomSampleGenerator();
        Contador contadorBack = getContadorRandomSampleGenerator();

        pessoa.setContador(contadorBack);
        assertThat(pessoa.getContador()).isEqualTo(contadorBack);
        assertThat(contadorBack.getPessoa()).isEqualTo(pessoa);

        pessoa.contador(null);
        assertThat(pessoa.getContador()).isNull();
        assertThat(contadorBack.getPessoa()).isNull();
    }

    @Test
    void funcionarioTest() {
        Pessoa pessoa = getPessoaRandomSampleGenerator();
        Funcionario funcionarioBack = getFuncionarioRandomSampleGenerator();

        pessoa.setFuncionario(funcionarioBack);
        assertThat(pessoa.getFuncionario()).isEqualTo(funcionarioBack);
        assertThat(funcionarioBack.getPessoa()).isEqualTo(pessoa);

        pessoa.funcionario(null);
        assertThat(pessoa.getFuncionario()).isNull();
        assertThat(funcionarioBack.getPessoa()).isNull();
    }

    @Test
    void socioTest() {
        Pessoa pessoa = getPessoaRandomSampleGenerator();
        Socio socioBack = getSocioRandomSampleGenerator();

        pessoa.setSocio(socioBack);
        assertThat(pessoa.getSocio()).isEqualTo(socioBack);
        assertThat(socioBack.getPessoa()).isEqualTo(pessoa);

        pessoa.socio(null);
        assertThat(pessoa.getSocio()).isNull();
        assertThat(socioBack.getPessoa()).isNull();
    }

    @Test
    void usuarioEmpresaTest() {
        Pessoa pessoa = getPessoaRandomSampleGenerator();
        UsuarioEmpresa usuarioEmpresaBack = getUsuarioEmpresaRandomSampleGenerator();

        pessoa.setUsuarioEmpresa(usuarioEmpresaBack);
        assertThat(pessoa.getUsuarioEmpresa()).isEqualTo(usuarioEmpresaBack);
        assertThat(usuarioEmpresaBack.getPessoa()).isEqualTo(pessoa);

        pessoa.usuarioEmpresa(null);
        assertThat(pessoa.getUsuarioEmpresa()).isNull();
        assertThat(usuarioEmpresaBack.getPessoa()).isNull();
    }
}
