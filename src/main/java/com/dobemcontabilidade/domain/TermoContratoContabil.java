package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TermoContratoContabil.
 */
@Entity
@Table(name = "termo_contrato_contabil")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TermoContratoContabil implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "documento")
    private String documento;

    @Lob
    @Column(name = "descricao")
    private String descricao;

    @Column(name = "titulo")
    private String titulo;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "calculoPlanoAssinaturas",
            "assinaturaEmpresas",
            "descontoPlanoContabils",
            "adicionalRamos",
            "adicionalTributacaos",
            "termoContratoContabils",
            "adicionalEnquadramentos",
            "valorBaseRamos",
        },
        allowSetters = true
    )
    private PlanoContabil planoContabil;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TermoContratoContabil id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumento() {
        return this.documento;
    }

    public TermoContratoContabil documento(String documento) {
        this.setDocumento(documento);
        return this;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public TermoContratoContabil descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public TermoContratoContabil titulo(String titulo) {
        this.setTitulo(titulo);
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public PlanoContabil getPlanoContabil() {
        return this.planoContabil;
    }

    public void setPlanoContabil(PlanoContabil planoContabil) {
        this.planoContabil = planoContabil;
    }

    public TermoContratoContabil planoContabil(PlanoContabil planoContabil) {
        this.setPlanoContabil(planoContabil);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TermoContratoContabil)) {
            return false;
        }
        return getId() != null && getId().equals(((TermoContratoContabil) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TermoContratoContabil{" +
            "id=" + getId() +
            ", documento='" + getDocumento() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", titulo='" + getTitulo() + "'" +
            "}";
    }
}
