package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ObservacaoCnae.
 */
@Entity
@Table(name = "observacao_cnae")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ObservacaoCnae implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "observacaoCnaes", "atividadeEmpresas", "classe", "segmentoCnaes" }, allowSetters = true)
    private SubclasseCnae subclasse;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ObservacaoCnae id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public ObservacaoCnae descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public SubclasseCnae getSubclasse() {
        return this.subclasse;
    }

    public void setSubclasse(SubclasseCnae subclasseCnae) {
        this.subclasse = subclasseCnae;
    }

    public ObservacaoCnae subclasse(SubclasseCnae subclasseCnae) {
        this.setSubclasse(subclasseCnae);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ObservacaoCnae)) {
            return false;
        }
        return getId() != null && getId().equals(((ObservacaoCnae) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ObservacaoCnae{" +
            "id=" + getId() +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
