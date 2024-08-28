package com.dobemcontabilidade.domain;

import static com.dobemcontabilidade.domain.DocsPessoaTestSamples.*;
import static com.dobemcontabilidade.domain.EmailTestSamples.*;
import static com.dobemcontabilidade.domain.EnderecoPessoaTestSamples.*;
import static com.dobemcontabilidade.domain.PessoaFisicaTestSamples.*;
import static com.dobemcontabilidade.domain.SocioTestSamples.*;
import static com.dobemcontabilidade.domain.TelefoneTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.dobemcontabilidade.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PessoaFisicaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PessoaFisica.class);
        PessoaFisica pessoaFisica1 = getPessoaFisicaSample1();
        PessoaFisica pessoaFisica2 = new PessoaFisica();
        assertThat(pessoaFisica1).isNotEqualTo(pessoaFisica2);

        pessoaFisica2.setId(pessoaFisica1.getId());
        assertThat(pessoaFisica1).isEqualTo(pessoaFisica2);

        pessoaFisica2 = getPessoaFisicaSample2();
        assertThat(pessoaFisica1).isNotEqualTo(pessoaFisica2);
    }

    @Test
    void enderecoPessoaTest() {
        PessoaFisica pessoaFisica = getPessoaFisicaRandomSampleGenerator();
        EnderecoPessoa enderecoPessoaBack = getEnderecoPessoaRandomSampleGenerator();

        pessoaFisica.addEnderecoPessoa(enderecoPessoaBack);
        assertThat(pessoaFisica.getEnderecoPessoas()).containsOnly(enderecoPessoaBack);
        assertThat(enderecoPessoaBack.getPessoa()).isEqualTo(pessoaFisica);

        pessoaFisica.removeEnderecoPessoa(enderecoPessoaBack);
        assertThat(pessoaFisica.getEnderecoPessoas()).doesNotContain(enderecoPessoaBack);
        assertThat(enderecoPessoaBack.getPessoa()).isNull();

        pessoaFisica.enderecoPessoas(new HashSet<>(Set.of(enderecoPessoaBack)));
        assertThat(pessoaFisica.getEnderecoPessoas()).containsOnly(enderecoPessoaBack);
        assertThat(enderecoPessoaBack.getPessoa()).isEqualTo(pessoaFisica);

        pessoaFisica.setEnderecoPessoas(new HashSet<>());
        assertThat(pessoaFisica.getEnderecoPessoas()).doesNotContain(enderecoPessoaBack);
        assertThat(enderecoPessoaBack.getPessoa()).isNull();
    }

    @Test
    void docsPessoaTest() {
        PessoaFisica pessoaFisica = getPessoaFisicaRandomSampleGenerator();
        DocsPessoa docsPessoaBack = getDocsPessoaRandomSampleGenerator();

        pessoaFisica.addDocsPessoa(docsPessoaBack);
        assertThat(pessoaFisica.getDocsPessoas()).containsOnly(docsPessoaBack);
        assertThat(docsPessoaBack.getPessoa()).isEqualTo(pessoaFisica);

        pessoaFisica.removeDocsPessoa(docsPessoaBack);
        assertThat(pessoaFisica.getDocsPessoas()).doesNotContain(docsPessoaBack);
        assertThat(docsPessoaBack.getPessoa()).isNull();

        pessoaFisica.docsPessoas(new HashSet<>(Set.of(docsPessoaBack)));
        assertThat(pessoaFisica.getDocsPessoas()).containsOnly(docsPessoaBack);
        assertThat(docsPessoaBack.getPessoa()).isEqualTo(pessoaFisica);

        pessoaFisica.setDocsPessoas(new HashSet<>());
        assertThat(pessoaFisica.getDocsPessoas()).doesNotContain(docsPessoaBack);
        assertThat(docsPessoaBack.getPessoa()).isNull();
    }

    @Test
    void emailTest() {
        PessoaFisica pessoaFisica = getPessoaFisicaRandomSampleGenerator();
        Email emailBack = getEmailRandomSampleGenerator();

        pessoaFisica.addEmail(emailBack);
        assertThat(pessoaFisica.getEmails()).containsOnly(emailBack);
        assertThat(emailBack.getPessoa()).isEqualTo(pessoaFisica);

        pessoaFisica.removeEmail(emailBack);
        assertThat(pessoaFisica.getEmails()).doesNotContain(emailBack);
        assertThat(emailBack.getPessoa()).isNull();

        pessoaFisica.emails(new HashSet<>(Set.of(emailBack)));
        assertThat(pessoaFisica.getEmails()).containsOnly(emailBack);
        assertThat(emailBack.getPessoa()).isEqualTo(pessoaFisica);

        pessoaFisica.setEmails(new HashSet<>());
        assertThat(pessoaFisica.getEmails()).doesNotContain(emailBack);
        assertThat(emailBack.getPessoa()).isNull();
    }

    @Test
    void telefoneTest() {
        PessoaFisica pessoaFisica = getPessoaFisicaRandomSampleGenerator();
        Telefone telefoneBack = getTelefoneRandomSampleGenerator();

        pessoaFisica.addTelefone(telefoneBack);
        assertThat(pessoaFisica.getTelefones()).containsOnly(telefoneBack);
        assertThat(telefoneBack.getPessoa()).isEqualTo(pessoaFisica);

        pessoaFisica.removeTelefone(telefoneBack);
        assertThat(pessoaFisica.getTelefones()).doesNotContain(telefoneBack);
        assertThat(telefoneBack.getPessoa()).isNull();

        pessoaFisica.telefones(new HashSet<>(Set.of(telefoneBack)));
        assertThat(pessoaFisica.getTelefones()).containsOnly(telefoneBack);
        assertThat(telefoneBack.getPessoa()).isEqualTo(pessoaFisica);

        pessoaFisica.setTelefones(new HashSet<>());
        assertThat(pessoaFisica.getTelefones()).doesNotContain(telefoneBack);
        assertThat(telefoneBack.getPessoa()).isNull();
    }

    @Test
    void socioTest() {
        PessoaFisica pessoaFisica = getPessoaFisicaRandomSampleGenerator();
        Socio socioBack = getSocioRandomSampleGenerator();

        pessoaFisica.setSocio(socioBack);
        assertThat(pessoaFisica.getSocio()).isEqualTo(socioBack);
        assertThat(socioBack.getPessoaFisica()).isEqualTo(pessoaFisica);

        pessoaFisica.socio(null);
        assertThat(pessoaFisica.getSocio()).isNull();
        assertThat(socioBack.getPessoaFisica()).isNull();
    }
}
