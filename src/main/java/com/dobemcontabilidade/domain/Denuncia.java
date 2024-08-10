package com.dobemcontabilidade.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Denuncia.
 */
@Entity
@Table(name = "denuncia")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Denuncia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "titulo", nullable = false)
    private String titulo;

    @NotNull
    @Column(name = "mensagem", nullable = false)
    private String mensagem;

    @Lob
    @Column(name = "descricao")
    private String descricao;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Denuncia id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public Denuncia titulo(String titulo) {
        this.setTitulo(titulo);
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return this.mensagem;
    }

    public Denuncia mensagem(String mensagem) {
        this.setMensagem(mensagem);
        return this;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Denuncia descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Denuncia)) {
            return false;
        }
        return getId() != null && getId().equals(((Denuncia) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Denuncia{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", mensagem='" + getMensagem() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
