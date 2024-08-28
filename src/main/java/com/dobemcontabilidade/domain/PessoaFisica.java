package com.dobemcontabilidade.domain;

import com.dobemcontabilidade.domain.enumeration.EstadoCivilEnum;
import com.dobemcontabilidade.domain.enumeration.SexoEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PessoaFisica.
 */
@Entity
@Table(name = "pessoa_fisica")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PessoaFisica implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 200)
    @Column(name = "nome", length = 200, nullable = false)
    private String nome;

    @NotNull
    @Column(name = "cpf", nullable = false)
    private String cpf;

    @Column(name = "data_nascimento")
    private Instant dataNascimento;

    @Column(name = "titulo_eleitor")
    private String tituloEleitor;

    @NotNull
    @Column(name = "rg", nullable = false)
    private String rg;

    @Column(name = "rg_orgao_expditor")
    private String rgOrgaoExpditor;

    @Column(name = "rg_uf_expedicao")
    private String rgUfExpedicao;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_civil")
    private EstadoCivilEnum estadoCivil;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "sexo", nullable = false)
    private SexoEnum sexo;

    @Column(name = "url_foto_perfil")
    private String urlFotoPerfil;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pessoa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pessoa", "cidade" }, allowSetters = true)
    private Set<EnderecoPessoa> enderecoPessoas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pessoa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pessoa" }, allowSetters = true)
    private Set<DocsPessoa> docsPessoas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pessoa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pessoa" }, allowSetters = true)
    private Set<Email> emails = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pessoa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "pessoa" }, allowSetters = true)
    private Set<Telefone> telefones = new HashSet<>();

    @JsonIgnoreProperties(value = { "pessoaFisica", "empresa" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "pessoaFisica")
    private Socio socio;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PessoaFisica id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public PessoaFisica nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return this.cpf;
    }

    public PessoaFisica cpf(String cpf) {
        this.setCpf(cpf);
        return this;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Instant getDataNascimento() {
        return this.dataNascimento;
    }

    public PessoaFisica dataNascimento(Instant dataNascimento) {
        this.setDataNascimento(dataNascimento);
        return this;
    }

    public void setDataNascimento(Instant dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTituloEleitor() {
        return this.tituloEleitor;
    }

    public PessoaFisica tituloEleitor(String tituloEleitor) {
        this.setTituloEleitor(tituloEleitor);
        return this;
    }

    public void setTituloEleitor(String tituloEleitor) {
        this.tituloEleitor = tituloEleitor;
    }

    public String getRg() {
        return this.rg;
    }

    public PessoaFisica rg(String rg) {
        this.setRg(rg);
        return this;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getRgOrgaoExpditor() {
        return this.rgOrgaoExpditor;
    }

    public PessoaFisica rgOrgaoExpditor(String rgOrgaoExpditor) {
        this.setRgOrgaoExpditor(rgOrgaoExpditor);
        return this;
    }

    public void setRgOrgaoExpditor(String rgOrgaoExpditor) {
        this.rgOrgaoExpditor = rgOrgaoExpditor;
    }

    public String getRgUfExpedicao() {
        return this.rgUfExpedicao;
    }

    public PessoaFisica rgUfExpedicao(String rgUfExpedicao) {
        this.setRgUfExpedicao(rgUfExpedicao);
        return this;
    }

    public void setRgUfExpedicao(String rgUfExpedicao) {
        this.rgUfExpedicao = rgUfExpedicao;
    }

    public EstadoCivilEnum getEstadoCivil() {
        return this.estadoCivil;
    }

    public PessoaFisica estadoCivil(EstadoCivilEnum estadoCivil) {
        this.setEstadoCivil(estadoCivil);
        return this;
    }

    public void setEstadoCivil(EstadoCivilEnum estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public SexoEnum getSexo() {
        return this.sexo;
    }

    public PessoaFisica sexo(SexoEnum sexo) {
        this.setSexo(sexo);
        return this;
    }

    public void setSexo(SexoEnum sexo) {
        this.sexo = sexo;
    }

    public String getUrlFotoPerfil() {
        return this.urlFotoPerfil;
    }

    public PessoaFisica urlFotoPerfil(String urlFotoPerfil) {
        this.setUrlFotoPerfil(urlFotoPerfil);
        return this;
    }

    public void setUrlFotoPerfil(String urlFotoPerfil) {
        this.urlFotoPerfil = urlFotoPerfil;
    }

    public Set<EnderecoPessoa> getEnderecoPessoas() {
        return this.enderecoPessoas;
    }

    public void setEnderecoPessoas(Set<EnderecoPessoa> enderecoPessoas) {
        if (this.enderecoPessoas != null) {
            this.enderecoPessoas.forEach(i -> i.setPessoa(null));
        }
        if (enderecoPessoas != null) {
            enderecoPessoas.forEach(i -> i.setPessoa(this));
        }
        this.enderecoPessoas = enderecoPessoas;
    }

    public PessoaFisica enderecoPessoas(Set<EnderecoPessoa> enderecoPessoas) {
        this.setEnderecoPessoas(enderecoPessoas);
        return this;
    }

    public PessoaFisica addEnderecoPessoa(EnderecoPessoa enderecoPessoa) {
        this.enderecoPessoas.add(enderecoPessoa);
        enderecoPessoa.setPessoa(this);
        return this;
    }

    public PessoaFisica removeEnderecoPessoa(EnderecoPessoa enderecoPessoa) {
        this.enderecoPessoas.remove(enderecoPessoa);
        enderecoPessoa.setPessoa(null);
        return this;
    }

    public Set<DocsPessoa> getDocsPessoas() {
        return this.docsPessoas;
    }

    public void setDocsPessoas(Set<DocsPessoa> docsPessoas) {
        if (this.docsPessoas != null) {
            this.docsPessoas.forEach(i -> i.setPessoa(null));
        }
        if (docsPessoas != null) {
            docsPessoas.forEach(i -> i.setPessoa(this));
        }
        this.docsPessoas = docsPessoas;
    }

    public PessoaFisica docsPessoas(Set<DocsPessoa> docsPessoas) {
        this.setDocsPessoas(docsPessoas);
        return this;
    }

    public PessoaFisica addDocsPessoa(DocsPessoa docsPessoa) {
        this.docsPessoas.add(docsPessoa);
        docsPessoa.setPessoa(this);
        return this;
    }

    public PessoaFisica removeDocsPessoa(DocsPessoa docsPessoa) {
        this.docsPessoas.remove(docsPessoa);
        docsPessoa.setPessoa(null);
        return this;
    }

    public Set<Email> getEmails() {
        return this.emails;
    }

    public void setEmails(Set<Email> emails) {
        if (this.emails != null) {
            this.emails.forEach(i -> i.setPessoa(null));
        }
        if (emails != null) {
            emails.forEach(i -> i.setPessoa(this));
        }
        this.emails = emails;
    }

    public PessoaFisica emails(Set<Email> emails) {
        this.setEmails(emails);
        return this;
    }

    public PessoaFisica addEmail(Email email) {
        this.emails.add(email);
        email.setPessoa(this);
        return this;
    }

    public PessoaFisica removeEmail(Email email) {
        this.emails.remove(email);
        email.setPessoa(null);
        return this;
    }

    public Set<Telefone> getTelefones() {
        return this.telefones;
    }

    public void setTelefones(Set<Telefone> telefones) {
        if (this.telefones != null) {
            this.telefones.forEach(i -> i.setPessoa(null));
        }
        if (telefones != null) {
            telefones.forEach(i -> i.setPessoa(this));
        }
        this.telefones = telefones;
    }

    public PessoaFisica telefones(Set<Telefone> telefones) {
        this.setTelefones(telefones);
        return this;
    }

    public PessoaFisica addTelefone(Telefone telefone) {
        this.telefones.add(telefone);
        telefone.setPessoa(this);
        return this;
    }

    public PessoaFisica removeTelefone(Telefone telefone) {
        this.telefones.remove(telefone);
        telefone.setPessoa(null);
        return this;
    }

    public Socio getSocio() {
        return this.socio;
    }

    public void setSocio(Socio socio) {
        if (this.socio != null) {
            this.socio.setPessoaFisica(null);
        }
        if (socio != null) {
            socio.setPessoaFisica(this);
        }
        this.socio = socio;
    }

    public PessoaFisica socio(Socio socio) {
        this.setSocio(socio);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PessoaFisica)) {
            return false;
        }
        return getId() != null && getId().equals(((PessoaFisica) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PessoaFisica{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", cpf='" + getCpf() + "'" +
            ", dataNascimento='" + getDataNascimento() + "'" +
            ", tituloEleitor='" + getTituloEleitor() + "'" +
            ", rg='" + getRg() + "'" +
            ", rgOrgaoExpditor='" + getRgOrgaoExpditor() + "'" +
            ", rgUfExpedicao='" + getRgUfExpedicao() + "'" +
            ", estadoCivil='" + getEstadoCivil() + "'" +
            ", sexo='" + getSexo() + "'" +
            ", urlFotoPerfil='" + getUrlFotoPerfil() + "'" +
            "}";
    }
}
