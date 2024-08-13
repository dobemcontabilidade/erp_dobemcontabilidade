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
 * A GrupoCnae.
 */
@Entity
@Table(name = "grupo_cnae")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GrupoCnae implements Serializable {

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "grupo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "subclasseCnaes", "grupo" }, allowSetters = true)
    private Set<ClasseCnae> classeCnaes = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "grupoCnaes", "secao" }, allowSetters = true)
    private DivisaoCnae divisao;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public GrupoCnae id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public GrupoCnae codigo(String codigo) {
        this.setCodigo(codigo);
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public GrupoCnae descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<ClasseCnae> getClasseCnaes() {
        return this.classeCnaes;
    }

    public void setClasseCnaes(Set<ClasseCnae> classeCnaes) {
        if (this.classeCnaes != null) {
            this.classeCnaes.forEach(i -> i.setGrupo(null));
        }
        if (classeCnaes != null) {
            classeCnaes.forEach(i -> i.setGrupo(this));
        }
        this.classeCnaes = classeCnaes;
    }

    public GrupoCnae classeCnaes(Set<ClasseCnae> classeCnaes) {
        this.setClasseCnaes(classeCnaes);
        return this;
    }

    public GrupoCnae addClasseCnae(ClasseCnae classeCnae) {
        this.classeCnaes.add(classeCnae);
        classeCnae.setGrupo(this);
        return this;
    }

    public GrupoCnae removeClasseCnae(ClasseCnae classeCnae) {
        this.classeCnaes.remove(classeCnae);
        classeCnae.setGrupo(null);
        return this;
    }

    public DivisaoCnae getDivisao() {
        return this.divisao;
    }

    public void setDivisao(DivisaoCnae divisaoCnae) {
        this.divisao = divisaoCnae;
    }

    public GrupoCnae divisao(DivisaoCnae divisaoCnae) {
        this.setDivisao(divisaoCnae);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GrupoCnae)) {
            return false;
        }
        return getId() != null && getId().equals(((GrupoCnae) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GrupoCnae{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
