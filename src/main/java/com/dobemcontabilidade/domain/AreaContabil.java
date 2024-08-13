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
 * A AreaContabil.
 */
@Entity
@Table(name = "area_contabil")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AreaContabil implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Size(max = 200)
    @Column(name = "descricao", length = 200)
    private String descricao;

    @Column(name = "percentual")
    private Double percentual;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "areaContabil")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "areaContabil", "assinaturaEmpresa", "contador" }, allowSetters = true)
    private Set<AreaContabilAssinaturaEmpresa> areaContabilAssinaturaEmpresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "areaContabil")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "servicoContabilEmpresaModelos",
            "servicoContabilEtapaFluxoModelos",
            "servicoContabilEtapaFluxoExecucaos",
            "anexoRequeridoServicoContabils",
            "servicoContabilOrdemServicos",
            "servicoContabilAssinaturaEmpresas",
            "tarefaEmpresaModelos",
            "areaContabil",
            "esfera",
        },
        allowSetters = true
    )
    private Set<ServicoContabil> servicoContabils = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "areaContabil")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "contador", "areaContabil" }, allowSetters = true)
    private Set<AreaContabilContador> areaContabilContadors = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AreaContabil id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public AreaContabil nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public AreaContabil descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPercentual() {
        return this.percentual;
    }

    public AreaContabil percentual(Double percentual) {
        this.setPercentual(percentual);
        return this;
    }

    public void setPercentual(Double percentual) {
        this.percentual = percentual;
    }

    public Set<AreaContabilAssinaturaEmpresa> getAreaContabilAssinaturaEmpresas() {
        return this.areaContabilAssinaturaEmpresas;
    }

    public void setAreaContabilAssinaturaEmpresas(Set<AreaContabilAssinaturaEmpresa> areaContabilAssinaturaEmpresas) {
        if (this.areaContabilAssinaturaEmpresas != null) {
            this.areaContabilAssinaturaEmpresas.forEach(i -> i.setAreaContabil(null));
        }
        if (areaContabilAssinaturaEmpresas != null) {
            areaContabilAssinaturaEmpresas.forEach(i -> i.setAreaContabil(this));
        }
        this.areaContabilAssinaturaEmpresas = areaContabilAssinaturaEmpresas;
    }

    public AreaContabil areaContabilAssinaturaEmpresas(Set<AreaContabilAssinaturaEmpresa> areaContabilAssinaturaEmpresas) {
        this.setAreaContabilAssinaturaEmpresas(areaContabilAssinaturaEmpresas);
        return this;
    }

    public AreaContabil addAreaContabilAssinaturaEmpresa(AreaContabilAssinaturaEmpresa areaContabilAssinaturaEmpresa) {
        this.areaContabilAssinaturaEmpresas.add(areaContabilAssinaturaEmpresa);
        areaContabilAssinaturaEmpresa.setAreaContabil(this);
        return this;
    }

    public AreaContabil removeAreaContabilAssinaturaEmpresa(AreaContabilAssinaturaEmpresa areaContabilAssinaturaEmpresa) {
        this.areaContabilAssinaturaEmpresas.remove(areaContabilAssinaturaEmpresa);
        areaContabilAssinaturaEmpresa.setAreaContabil(null);
        return this;
    }

    public Set<ServicoContabil> getServicoContabils() {
        return this.servicoContabils;
    }

    public void setServicoContabils(Set<ServicoContabil> servicoContabils) {
        if (this.servicoContabils != null) {
            this.servicoContabils.forEach(i -> i.setAreaContabil(null));
        }
        if (servicoContabils != null) {
            servicoContabils.forEach(i -> i.setAreaContabil(this));
        }
        this.servicoContabils = servicoContabils;
    }

    public AreaContabil servicoContabils(Set<ServicoContabil> servicoContabils) {
        this.setServicoContabils(servicoContabils);
        return this;
    }

    public AreaContabil addServicoContabil(ServicoContabil servicoContabil) {
        this.servicoContabils.add(servicoContabil);
        servicoContabil.setAreaContabil(this);
        return this;
    }

    public AreaContabil removeServicoContabil(ServicoContabil servicoContabil) {
        this.servicoContabils.remove(servicoContabil);
        servicoContabil.setAreaContabil(null);
        return this;
    }

    public Set<AreaContabilContador> getAreaContabilContadors() {
        return this.areaContabilContadors;
    }

    public void setAreaContabilContadors(Set<AreaContabilContador> areaContabilContadors) {
        if (this.areaContabilContadors != null) {
            this.areaContabilContadors.forEach(i -> i.setAreaContabil(null));
        }
        if (areaContabilContadors != null) {
            areaContabilContadors.forEach(i -> i.setAreaContabil(this));
        }
        this.areaContabilContadors = areaContabilContadors;
    }

    public AreaContabil areaContabilContadors(Set<AreaContabilContador> areaContabilContadors) {
        this.setAreaContabilContadors(areaContabilContadors);
        return this;
    }

    public AreaContabil addAreaContabilContador(AreaContabilContador areaContabilContador) {
        this.areaContabilContadors.add(areaContabilContador);
        areaContabilContador.setAreaContabil(this);
        return this;
    }

    public AreaContabil removeAreaContabilContador(AreaContabilContador areaContabilContador) {
        this.areaContabilContadors.remove(areaContabilContador);
        areaContabilContador.setAreaContabil(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AreaContabil)) {
            return false;
        }
        return getId() != null && getId().equals(((AreaContabil) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AreaContabil{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", percentual=" + getPercentual() +
            "}";
    }
}
