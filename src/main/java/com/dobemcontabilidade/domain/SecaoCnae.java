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
 * A SecaoCnae.
 */
@Entity
@Table(name = "secao_cnae")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SecaoCnae implements Serializable {

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "secao")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "grupoCnaes", "secao" }, allowSetters = true)
    private Set<DivisaoCnae> divisaoCnaes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SecaoCnae id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public SecaoCnae codigo(String codigo) {
        this.setCodigo(codigo);
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public SecaoCnae descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<DivisaoCnae> getDivisaoCnaes() {
        return this.divisaoCnaes;
    }

    public void setDivisaoCnaes(Set<DivisaoCnae> divisaoCnaes) {
        if (this.divisaoCnaes != null) {
            this.divisaoCnaes.forEach(i -> i.setSecao(null));
        }
        if (divisaoCnaes != null) {
            divisaoCnaes.forEach(i -> i.setSecao(this));
        }
        this.divisaoCnaes = divisaoCnaes;
    }

    public SecaoCnae divisaoCnaes(Set<DivisaoCnae> divisaoCnaes) {
        this.setDivisaoCnaes(divisaoCnaes);
        return this;
    }

    public SecaoCnae addDivisaoCnae(DivisaoCnae divisaoCnae) {
        this.divisaoCnaes.add(divisaoCnae);
        divisaoCnae.setSecao(this);
        return this;
    }

    public SecaoCnae removeDivisaoCnae(DivisaoCnae divisaoCnae) {
        this.divisaoCnaes.remove(divisaoCnae);
        divisaoCnae.setSecao(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SecaoCnae)) {
            return false;
        }
        return getId() != null && getId().equals(((SecaoCnae) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SecaoCnae{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
