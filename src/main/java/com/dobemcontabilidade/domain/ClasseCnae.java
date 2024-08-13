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
 * A ClasseCnae.
 */
@Entity
@Table(name = "classe_cnae")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ClasseCnae implements Serializable {

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "classe")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "observacaoCnaes", "atividadeEmpresas", "classe", "segmentoCnaes" }, allowSetters = true)
    private Set<SubclasseCnae> subclasseCnaes = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "classeCnaes", "divisao" }, allowSetters = true)
    private GrupoCnae grupo;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ClasseCnae id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public ClasseCnae codigo(String codigo) {
        this.setCodigo(codigo);
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public ClasseCnae descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<SubclasseCnae> getSubclasseCnaes() {
        return this.subclasseCnaes;
    }

    public void setSubclasseCnaes(Set<SubclasseCnae> subclasseCnaes) {
        if (this.subclasseCnaes != null) {
            this.subclasseCnaes.forEach(i -> i.setClasse(null));
        }
        if (subclasseCnaes != null) {
            subclasseCnaes.forEach(i -> i.setClasse(this));
        }
        this.subclasseCnaes = subclasseCnaes;
    }

    public ClasseCnae subclasseCnaes(Set<SubclasseCnae> subclasseCnaes) {
        this.setSubclasseCnaes(subclasseCnaes);
        return this;
    }

    public ClasseCnae addSubclasseCnae(SubclasseCnae subclasseCnae) {
        this.subclasseCnaes.add(subclasseCnae);
        subclasseCnae.setClasse(this);
        return this;
    }

    public ClasseCnae removeSubclasseCnae(SubclasseCnae subclasseCnae) {
        this.subclasseCnaes.remove(subclasseCnae);
        subclasseCnae.setClasse(null);
        return this;
    }

    public GrupoCnae getGrupo() {
        return this.grupo;
    }

    public void setGrupo(GrupoCnae grupoCnae) {
        this.grupo = grupoCnae;
    }

    public ClasseCnae grupo(GrupoCnae grupoCnae) {
        this.setGrupo(grupoCnae);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClasseCnae)) {
            return false;
        }
        return getId() != null && getId().equals(((ClasseCnae) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClasseCnae{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
