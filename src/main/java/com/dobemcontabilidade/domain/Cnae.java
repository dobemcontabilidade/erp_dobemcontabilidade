package com.dobemcontabilidade.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Cnae.
 */
@Entity
@Table(name = "cnae")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Cnae implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "codigo", nullable = false)
    private String codigo;

    @Lob
    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "anexo")
    private Integer anexo;

    @Column(name = "atendido_freemium")
    private Boolean atendidoFreemium;

    @Column(name = "atendido")
    private Boolean atendido;

    @Column(name = "optante_simples")
    private Boolean optanteSimples;

    @Column(name = "categoria")
    private String categoria;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Cnae id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public Cnae codigo(String codigo) {
        this.setCodigo(codigo);
        return this;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Cnae descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getAnexo() {
        return this.anexo;
    }

    public Cnae anexo(Integer anexo) {
        this.setAnexo(anexo);
        return this;
    }

    public void setAnexo(Integer anexo) {
        this.anexo = anexo;
    }

    public Boolean getAtendidoFreemium() {
        return this.atendidoFreemium;
    }

    public Cnae atendidoFreemium(Boolean atendidoFreemium) {
        this.setAtendidoFreemium(atendidoFreemium);
        return this;
    }

    public void setAtendidoFreemium(Boolean atendidoFreemium) {
        this.atendidoFreemium = atendidoFreemium;
    }

    public Boolean getAtendido() {
        return this.atendido;
    }

    public Cnae atendido(Boolean atendido) {
        this.setAtendido(atendido);
        return this;
    }

    public void setAtendido(Boolean atendido) {
        this.atendido = atendido;
    }

    public Boolean getOptanteSimples() {
        return this.optanteSimples;
    }

    public Cnae optanteSimples(Boolean optanteSimples) {
        this.setOptanteSimples(optanteSimples);
        return this;
    }

    public void setOptanteSimples(Boolean optanteSimples) {
        this.optanteSimples = optanteSimples;
    }

    public String getCategoria() {
        return this.categoria;
    }

    public Cnae categoria(String categoria) {
        this.setCategoria(categoria);
        return this;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cnae)) {
            return false;
        }
        return getId() != null && getId().equals(((Cnae) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cnae{" +
            "id=" + getId() +
            ", codigo='" + getCodigo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", anexo=" + getAnexo() +
            ", atendidoFreemium='" + getAtendidoFreemium() + "'" +
            ", atendido='" + getAtendido() + "'" +
            ", optanteSimples='" + getOptanteSimples() + "'" +
            ", categoria='" + getCategoria() + "'" +
            "}";
    }
}
