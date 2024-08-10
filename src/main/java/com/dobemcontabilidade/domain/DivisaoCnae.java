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
 * A DivisaoCnae.
 */
@Entity
@Table(name = "divisao_cnae")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DivisaoCnae implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 15)
    @Column(name = "codigo", length = 15)
    private String codigo;

    @Lob
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "divisao")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "classeCnaes", "divisao" }, allowSetters = true)
    private Set<GrupoCnae> grupoCnaes = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "divisaoCnaes" }, allowSetters = true)
    private SecaoCnae secao;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DivisaoCnae id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public DivisaoCnae codigo(String codigo) {
        this.setCodigo(codigo);
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public DivisaoCnae descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<GrupoCnae> getGrupoCnaes() {
        return this.grupoCnaes;
    }

    public void setGrupoCnaes(Set<GrupoCnae> grupoCnaes) {
        if (this.grupoCnaes != null) {
            this.grupoCnaes.forEach(i -> i.setDivisao(null));
        }
        if (grupoCnaes != null) {
            grupoCnaes.forEach(i -> i.setDivisao(this));
        }
        this.grupoCnaes = grupoCnaes;
    }

    public DivisaoCnae grupoCnaes(Set<GrupoCnae> grupoCnaes) {
        this.setGrupoCnaes(grupoCnaes);
        return this;
    }

    public DivisaoCnae addGrupoCnae(GrupoCnae grupoCnae) {
        this.grupoCnaes.add(grupoCnae);
        grupoCnae.setDivisao(this);
        return this;
    }

    public DivisaoCnae removeGrupoCnae(GrupoCnae grupoCnae) {
        this.grupoCnaes.remove(grupoCnae);
        grupoCnae.setDivisao(null);
        return this;
    }

    public SecaoCnae getSecao() {
        return this.secao;
    }

    public void setSecao(SecaoCnae secaoCnae) {
        this.secao = secaoCnae;
    }

    public DivisaoCnae secao(SecaoCnae secaoCnae) {
        this.setSecao(secaoCnae);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DivisaoCnae)) {
            return false;
        }
        return getId() != null && getId().equals(((DivisaoCnae) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DivisaoCnae{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
