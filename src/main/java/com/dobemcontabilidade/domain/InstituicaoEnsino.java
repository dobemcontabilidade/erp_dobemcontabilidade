package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A InstituicaoEnsino.
 */
@Entity
@Table(name = "instituicao_ensino")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class InstituicaoEnsino implements Serializable {

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

    @Column(name = "cnpj")
    private String cnpj;

    @Column(name = "logradouro")
    private String logradouro;

    @Size(max = 5)
    @Column(name = "numero", length = 5)
    private String numero;

    @Size(max = 100)
    @Column(name = "complemento", length = 100)
    private String complemento;

    @Column(name = "bairro")
    private String bairro;

    @Size(max = 9)
    @Column(name = "cep", length = 9)
    private String cep;

    @Column(name = "principal")
    private Boolean principal;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "instituicaoEnsino")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "funcionario", "agenteIntegracaoEstagio", "instituicaoEnsino" }, allowSetters = true)
    private Set<ContratoFuncionario> contratoFuncionarios = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "instituicaoEnsinos",
            "agenteIntegracaoEstagios",
            "empresaModelos",
            "fluxoModelos",
            "enderecoPessoas",
            "enderecoEmpresas",
            "estado",
        },
        allowSetters = true
    )
    private Cidade cidade;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public InstituicaoEnsino id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public InstituicaoEnsino nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return this.cnpj;
    }

    public InstituicaoEnsino cnpj(String cnpj) {
        this.setCnpj(cnpj);
        return this;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getLogradouro() {
        return this.logradouro;
    }

    public InstituicaoEnsino logradouro(String logradouro) {
        this.setLogradouro(logradouro);
        return this;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return this.numero;
    }

    public InstituicaoEnsino numero(String numero) {
        this.setNumero(numero);
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return this.complemento;
    }

    public InstituicaoEnsino complemento(String complemento) {
        this.setComplemento(complemento);
        return this;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return this.bairro;
    }

    public InstituicaoEnsino bairro(String bairro) {
        this.setBairro(bairro);
        return this;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return this.cep;
    }

    public InstituicaoEnsino cep(String cep) {
        this.setCep(cep);
        return this;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Boolean getPrincipal() {
        return this.principal;
    }

    public InstituicaoEnsino principal(Boolean principal) {
        this.setPrincipal(principal);
        return this;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }

    public Set<ContratoFuncionario> getContratoFuncionarios() {
        return this.contratoFuncionarios;
    }

    public void setContratoFuncionarios(Set<ContratoFuncionario> contratoFuncionarios) {
        if (this.contratoFuncionarios != null) {
            this.contratoFuncionarios.forEach(i -> i.setInstituicaoEnsino(null));
        }
        if (contratoFuncionarios != null) {
            contratoFuncionarios.forEach(i -> i.setInstituicaoEnsino(this));
        }
        this.contratoFuncionarios = contratoFuncionarios;
    }

    public InstituicaoEnsino contratoFuncionarios(Set<ContratoFuncionario> contratoFuncionarios) {
        this.setContratoFuncionarios(contratoFuncionarios);
        return this;
    }

    public InstituicaoEnsino addContratoFuncionario(ContratoFuncionario contratoFuncionario) {
        this.contratoFuncionarios.add(contratoFuncionario);
        contratoFuncionario.setInstituicaoEnsino(this);
        return this;
    }

    public InstituicaoEnsino removeContratoFuncionario(ContratoFuncionario contratoFuncionario) {
        this.contratoFuncionarios.remove(contratoFuncionario);
        contratoFuncionario.setInstituicaoEnsino(null);
        return this;
    }

    public Cidade getCidade() {
        return this.cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public InstituicaoEnsino cidade(Cidade cidade) {
        this.setCidade(cidade);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InstituicaoEnsino)) {
            return false;
        }
        return getId() != null && getId().equals(((InstituicaoEnsino) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "InstituicaoEnsino{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", cnpj='" + getCnpj() + "'" +
            ", logradouro='" + getLogradouro() + "'" +
            ", numero='" + getNumero() + "'" +
            ", complemento='" + getComplemento() + "'" +
            ", bairro='" + getBairro() + "'" +
            ", cep='" + getCep() + "'" +
            ", principal='" + getPrincipal() + "'" +
            "}";
    }
}
