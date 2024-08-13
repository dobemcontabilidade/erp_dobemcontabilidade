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
 * A EtapaFluxoModelo.
 */
@Entity
@Table(name = "etapa_fluxo_modelo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EtapaFluxoModelo implements Serializable {

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

    @Column(name = "ordem")
    private Integer ordem;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "etapaFluxoModelo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "etapaFluxoModelo", "servicoContabil" }, allowSetters = true)
    private Set<ServicoContabilEtapaFluxoModelo> servicoContabilEtapaFluxoModelos = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "ordemServicos", "etapaFluxoModelos", "cidade" }, allowSetters = true)
    private FluxoModelo fluxoModelo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EtapaFluxoModelo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public EtapaFluxoModelo nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public EtapaFluxoModelo descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getOrdem() {
        return this.ordem;
    }

    public EtapaFluxoModelo ordem(Integer ordem) {
        this.setOrdem(ordem);
        return this;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public Set<ServicoContabilEtapaFluxoModelo> getServicoContabilEtapaFluxoModelos() {
        return this.servicoContabilEtapaFluxoModelos;
    }

    public void setServicoContabilEtapaFluxoModelos(Set<ServicoContabilEtapaFluxoModelo> servicoContabilEtapaFluxoModelos) {
        if (this.servicoContabilEtapaFluxoModelos != null) {
            this.servicoContabilEtapaFluxoModelos.forEach(i -> i.setEtapaFluxoModelo(null));
        }
        if (servicoContabilEtapaFluxoModelos != null) {
            servicoContabilEtapaFluxoModelos.forEach(i -> i.setEtapaFluxoModelo(this));
        }
        this.servicoContabilEtapaFluxoModelos = servicoContabilEtapaFluxoModelos;
    }

    public EtapaFluxoModelo servicoContabilEtapaFluxoModelos(Set<ServicoContabilEtapaFluxoModelo> servicoContabilEtapaFluxoModelos) {
        this.setServicoContabilEtapaFluxoModelos(servicoContabilEtapaFluxoModelos);
        return this;
    }

    public EtapaFluxoModelo addServicoContabilEtapaFluxoModelo(ServicoContabilEtapaFluxoModelo servicoContabilEtapaFluxoModelo) {
        this.servicoContabilEtapaFluxoModelos.add(servicoContabilEtapaFluxoModelo);
        servicoContabilEtapaFluxoModelo.setEtapaFluxoModelo(this);
        return this;
    }

    public EtapaFluxoModelo removeServicoContabilEtapaFluxoModelo(ServicoContabilEtapaFluxoModelo servicoContabilEtapaFluxoModelo) {
        this.servicoContabilEtapaFluxoModelos.remove(servicoContabilEtapaFluxoModelo);
        servicoContabilEtapaFluxoModelo.setEtapaFluxoModelo(null);
        return this;
    }

    public FluxoModelo getFluxoModelo() {
        return this.fluxoModelo;
    }

    public void setFluxoModelo(FluxoModelo fluxoModelo) {
        this.fluxoModelo = fluxoModelo;
    }

    public EtapaFluxoModelo fluxoModelo(FluxoModelo fluxoModelo) {
        this.setFluxoModelo(fluxoModelo);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EtapaFluxoModelo)) {
            return false;
        }
        return getId() != null && getId().equals(((EtapaFluxoModelo) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EtapaFluxoModelo{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", ordem=" + getOrdem() +
            "}";
    }
}
