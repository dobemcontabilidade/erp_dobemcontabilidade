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
 * A FluxoModelo.
 */
@Entity
@Table(name = "fluxo_modelo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FluxoModelo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fluxoModelo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "feedBackUsuarioParaContadors",
            "feedBackContadorParaUsuarios",
            "etapaFluxoExecucaos",
            "servicoContabilOrdemServicos",
            "empresa",
            "contador",
            "fluxoModelo",
        },
        allowSetters = true
    )
    private Set<OrdemServico> ordemServicos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fluxoModelo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "servicoContabilEtapaFluxoModelos", "fluxoModelo" }, allowSetters = true)
    private Set<EtapaFluxoModelo> etapaFluxoModelos = new HashSet<>();

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

    public FluxoModelo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public FluxoModelo nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public FluxoModelo descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<OrdemServico> getOrdemServicos() {
        return this.ordemServicos;
    }

    public void setOrdemServicos(Set<OrdemServico> ordemServicos) {
        if (this.ordemServicos != null) {
            this.ordemServicos.forEach(i -> i.setFluxoModelo(null));
        }
        if (ordemServicos != null) {
            ordemServicos.forEach(i -> i.setFluxoModelo(this));
        }
        this.ordemServicos = ordemServicos;
    }

    public FluxoModelo ordemServicos(Set<OrdemServico> ordemServicos) {
        this.setOrdemServicos(ordemServicos);
        return this;
    }

    public FluxoModelo addOrdemServico(OrdemServico ordemServico) {
        this.ordemServicos.add(ordemServico);
        ordemServico.setFluxoModelo(this);
        return this;
    }

    public FluxoModelo removeOrdemServico(OrdemServico ordemServico) {
        this.ordemServicos.remove(ordemServico);
        ordemServico.setFluxoModelo(null);
        return this;
    }

    public Set<EtapaFluxoModelo> getEtapaFluxoModelos() {
        return this.etapaFluxoModelos;
    }

    public void setEtapaFluxoModelos(Set<EtapaFluxoModelo> etapaFluxoModelos) {
        if (this.etapaFluxoModelos != null) {
            this.etapaFluxoModelos.forEach(i -> i.setFluxoModelo(null));
        }
        if (etapaFluxoModelos != null) {
            etapaFluxoModelos.forEach(i -> i.setFluxoModelo(this));
        }
        this.etapaFluxoModelos = etapaFluxoModelos;
    }

    public FluxoModelo etapaFluxoModelos(Set<EtapaFluxoModelo> etapaFluxoModelos) {
        this.setEtapaFluxoModelos(etapaFluxoModelos);
        return this;
    }

    public FluxoModelo addEtapaFluxoModelo(EtapaFluxoModelo etapaFluxoModelo) {
        this.etapaFluxoModelos.add(etapaFluxoModelo);
        etapaFluxoModelo.setFluxoModelo(this);
        return this;
    }

    public FluxoModelo removeEtapaFluxoModelo(EtapaFluxoModelo etapaFluxoModelo) {
        this.etapaFluxoModelos.remove(etapaFluxoModelo);
        etapaFluxoModelo.setFluxoModelo(null);
        return this;
    }

    public Cidade getCidade() {
        return this.cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public FluxoModelo cidade(Cidade cidade) {
        this.setCidade(cidade);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FluxoModelo)) {
            return false;
        }
        return getId() != null && getId().equals(((FluxoModelo) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FluxoModelo{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
