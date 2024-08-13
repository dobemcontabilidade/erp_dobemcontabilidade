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
 * A Imposto.
 */
@Entity
@Table(name = "imposto")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Imposto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "sigla")
    private String sigla;

    @Column(name = "descricao")
    private String descricao;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "imposto")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "impostoAPagarEmpresas", "empresa", "imposto" }, allowSetters = true)
    private Set<ImpostoEmpresa> impostoEmpresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "imposto")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "parcelaImpostoAPagars", "impostoParcelados", "imposto", "empresa" }, allowSetters = true)
    private Set<ParcelamentoImposto> parcelamentoImpostos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "imposto")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "empresaModelo", "imposto" }, allowSetters = true)
    private Set<ImpostoEmpresaModelo> impostoEmpresaModelos = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "servicoContabils", "impostos", "tarefas" }, allowSetters = true)
    private Esfera esfera;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Imposto id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Imposto nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return this.sigla;
    }

    public Imposto sigla(String sigla) {
        this.setSigla(sigla);
        return this;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Imposto descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<ImpostoEmpresa> getImpostoEmpresas() {
        return this.impostoEmpresas;
    }

    public void setImpostoEmpresas(Set<ImpostoEmpresa> impostoEmpresas) {
        if (this.impostoEmpresas != null) {
            this.impostoEmpresas.forEach(i -> i.setImposto(null));
        }
        if (impostoEmpresas != null) {
            impostoEmpresas.forEach(i -> i.setImposto(this));
        }
        this.impostoEmpresas = impostoEmpresas;
    }

    public Imposto impostoEmpresas(Set<ImpostoEmpresa> impostoEmpresas) {
        this.setImpostoEmpresas(impostoEmpresas);
        return this;
    }

    public Imposto addImpostoEmpresa(ImpostoEmpresa impostoEmpresa) {
        this.impostoEmpresas.add(impostoEmpresa);
        impostoEmpresa.setImposto(this);
        return this;
    }

    public Imposto removeImpostoEmpresa(ImpostoEmpresa impostoEmpresa) {
        this.impostoEmpresas.remove(impostoEmpresa);
        impostoEmpresa.setImposto(null);
        return this;
    }

    public Set<ParcelamentoImposto> getParcelamentoImpostos() {
        return this.parcelamentoImpostos;
    }

    public void setParcelamentoImpostos(Set<ParcelamentoImposto> parcelamentoImpostos) {
        if (this.parcelamentoImpostos != null) {
            this.parcelamentoImpostos.forEach(i -> i.setImposto(null));
        }
        if (parcelamentoImpostos != null) {
            parcelamentoImpostos.forEach(i -> i.setImposto(this));
        }
        this.parcelamentoImpostos = parcelamentoImpostos;
    }

    public Imposto parcelamentoImpostos(Set<ParcelamentoImposto> parcelamentoImpostos) {
        this.setParcelamentoImpostos(parcelamentoImpostos);
        return this;
    }

    public Imposto addParcelamentoImposto(ParcelamentoImposto parcelamentoImposto) {
        this.parcelamentoImpostos.add(parcelamentoImposto);
        parcelamentoImposto.setImposto(this);
        return this;
    }

    public Imposto removeParcelamentoImposto(ParcelamentoImposto parcelamentoImposto) {
        this.parcelamentoImpostos.remove(parcelamentoImposto);
        parcelamentoImposto.setImposto(null);
        return this;
    }

    public Set<ImpostoEmpresaModelo> getImpostoEmpresaModelos() {
        return this.impostoEmpresaModelos;
    }

    public void setImpostoEmpresaModelos(Set<ImpostoEmpresaModelo> impostoEmpresaModelos) {
        if (this.impostoEmpresaModelos != null) {
            this.impostoEmpresaModelos.forEach(i -> i.setImposto(null));
        }
        if (impostoEmpresaModelos != null) {
            impostoEmpresaModelos.forEach(i -> i.setImposto(this));
        }
        this.impostoEmpresaModelos = impostoEmpresaModelos;
    }

    public Imposto impostoEmpresaModelos(Set<ImpostoEmpresaModelo> impostoEmpresaModelos) {
        this.setImpostoEmpresaModelos(impostoEmpresaModelos);
        return this;
    }

    public Imposto addImpostoEmpresaModelo(ImpostoEmpresaModelo impostoEmpresaModelo) {
        this.impostoEmpresaModelos.add(impostoEmpresaModelo);
        impostoEmpresaModelo.setImposto(this);
        return this;
    }

    public Imposto removeImpostoEmpresaModelo(ImpostoEmpresaModelo impostoEmpresaModelo) {
        this.impostoEmpresaModelos.remove(impostoEmpresaModelo);
        impostoEmpresaModelo.setImposto(null);
        return this;
    }

    public Esfera getEsfera() {
        return this.esfera;
    }

    public void setEsfera(Esfera esfera) {
        this.esfera = esfera;
    }

    public Imposto esfera(Esfera esfera) {
        this.setEsfera(esfera);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Imposto)) {
            return false;
        }
        return getId() != null && getId().equals(((Imposto) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Imposto{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", sigla='" + getSigla() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
