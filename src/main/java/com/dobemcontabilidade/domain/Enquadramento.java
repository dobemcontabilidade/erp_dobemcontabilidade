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
 * A Enquadramento.
 */
@Entity
@Table(name = "enquadramento")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Enquadramento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 100)
    @Column(name = "nome", length = 100)
    private String nome;

    @Column(name = "sigla")
    private String sigla;

    @Column(name = "limite_inicial")
    private Double limiteInicial;

    @Column(name = "limite_final")
    private Double limiteFinal;

    @Lob
    @Column(name = "descricao")
    private String descricao;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "enquadramento")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "anexoEmpresas", "anexoRequerido", "enquadramento", "tributacao", "ramo", "empresa", "empresaModelo" },
        allowSetters = true
    )
    private Set<AnexoRequeridoEmpresa> anexoRequeridoEmpresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "enquadramento")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "servicoContabilEmpresaModelos",
            "impostoEmpresaModelos",
            "anexoRequeridoEmpresas",
            "tarefaEmpresaModelos",
            "empresas",
            "segmentoCnaes",
            "ramo",
            "enquadramento",
            "tributacao",
            "cidade",
        },
        allowSetters = true
    )
    private Set<EmpresaModelo> empresaModelos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "enquadramento")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "enquadramento", "planoContabil" }, allowSetters = true)
    private Set<AdicionalEnquadramento> adicionalEnquadramentos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Enquadramento id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Enquadramento nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return this.sigla;
    }

    public Enquadramento sigla(String sigla) {
        this.setSigla(sigla);
        return this;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Double getLimiteInicial() {
        return this.limiteInicial;
    }

    public Enquadramento limiteInicial(Double limiteInicial) {
        this.setLimiteInicial(limiteInicial);
        return this;
    }

    public void setLimiteInicial(Double limiteInicial) {
        this.limiteInicial = limiteInicial;
    }

    public Double getLimiteFinal() {
        return this.limiteFinal;
    }

    public Enquadramento limiteFinal(Double limiteFinal) {
        this.setLimiteFinal(limiteFinal);
        return this;
    }

    public void setLimiteFinal(Double limiteFinal) {
        this.limiteFinal = limiteFinal;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Enquadramento descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<AnexoRequeridoEmpresa> getAnexoRequeridoEmpresas() {
        return this.anexoRequeridoEmpresas;
    }

    public void setAnexoRequeridoEmpresas(Set<AnexoRequeridoEmpresa> anexoRequeridoEmpresas) {
        if (this.anexoRequeridoEmpresas != null) {
            this.anexoRequeridoEmpresas.forEach(i -> i.setEnquadramento(null));
        }
        if (anexoRequeridoEmpresas != null) {
            anexoRequeridoEmpresas.forEach(i -> i.setEnquadramento(this));
        }
        this.anexoRequeridoEmpresas = anexoRequeridoEmpresas;
    }

    public Enquadramento anexoRequeridoEmpresas(Set<AnexoRequeridoEmpresa> anexoRequeridoEmpresas) {
        this.setAnexoRequeridoEmpresas(anexoRequeridoEmpresas);
        return this;
    }

    public Enquadramento addAnexoRequeridoEmpresa(AnexoRequeridoEmpresa anexoRequeridoEmpresa) {
        this.anexoRequeridoEmpresas.add(anexoRequeridoEmpresa);
        anexoRequeridoEmpresa.setEnquadramento(this);
        return this;
    }

    public Enquadramento removeAnexoRequeridoEmpresa(AnexoRequeridoEmpresa anexoRequeridoEmpresa) {
        this.anexoRequeridoEmpresas.remove(anexoRequeridoEmpresa);
        anexoRequeridoEmpresa.setEnquadramento(null);
        return this;
    }

    public Set<EmpresaModelo> getEmpresaModelos() {
        return this.empresaModelos;
    }

    public void setEmpresaModelos(Set<EmpresaModelo> empresaModelos) {
        if (this.empresaModelos != null) {
            this.empresaModelos.forEach(i -> i.setEnquadramento(null));
        }
        if (empresaModelos != null) {
            empresaModelos.forEach(i -> i.setEnquadramento(this));
        }
        this.empresaModelos = empresaModelos;
    }

    public Enquadramento empresaModelos(Set<EmpresaModelo> empresaModelos) {
        this.setEmpresaModelos(empresaModelos);
        return this;
    }

    public Enquadramento addEmpresaModelo(EmpresaModelo empresaModelo) {
        this.empresaModelos.add(empresaModelo);
        empresaModelo.setEnquadramento(this);
        return this;
    }

    public Enquadramento removeEmpresaModelo(EmpresaModelo empresaModelo) {
        this.empresaModelos.remove(empresaModelo);
        empresaModelo.setEnquadramento(null);
        return this;
    }

    public Set<AdicionalEnquadramento> getAdicionalEnquadramentos() {
        return this.adicionalEnquadramentos;
    }

    public void setAdicionalEnquadramentos(Set<AdicionalEnquadramento> adicionalEnquadramentos) {
        if (this.adicionalEnquadramentos != null) {
            this.adicionalEnquadramentos.forEach(i -> i.setEnquadramento(null));
        }
        if (adicionalEnquadramentos != null) {
            adicionalEnquadramentos.forEach(i -> i.setEnquadramento(this));
        }
        this.adicionalEnquadramentos = adicionalEnquadramentos;
    }

    public Enquadramento adicionalEnquadramentos(Set<AdicionalEnquadramento> adicionalEnquadramentos) {
        this.setAdicionalEnquadramentos(adicionalEnquadramentos);
        return this;
    }

    public Enquadramento addAdicionalEnquadramento(AdicionalEnquadramento adicionalEnquadramento) {
        this.adicionalEnquadramentos.add(adicionalEnquadramento);
        adicionalEnquadramento.setEnquadramento(this);
        return this;
    }

    public Enquadramento removeAdicionalEnquadramento(AdicionalEnquadramento adicionalEnquadramento) {
        this.adicionalEnquadramentos.remove(adicionalEnquadramento);
        adicionalEnquadramento.setEnquadramento(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Enquadramento)) {
            return false;
        }
        return getId() != null && getId().equals(((Enquadramento) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Enquadramento{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", sigla='" + getSigla() + "'" +
            ", limiteInicial=" + getLimiteInicial() +
            ", limiteFinal=" + getLimiteFinal() +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
