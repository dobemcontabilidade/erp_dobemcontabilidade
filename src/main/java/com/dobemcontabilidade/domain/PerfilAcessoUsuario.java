package com.dobemcontabilidade.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PerfilAcessoUsuario.
 */
@Entity
@Table(name = "perfil_acesso_usuario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PerfilAcessoUsuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "autorizado")
    private Boolean autorizado;

    @Column(name = "data_expiracao")
    private Instant dataExpiracao;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PerfilAcessoUsuario id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public PerfilAcessoUsuario nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getAutorizado() {
        return this.autorizado;
    }

    public PerfilAcessoUsuario autorizado(Boolean autorizado) {
        this.setAutorizado(autorizado);
        return this;
    }

    public void setAutorizado(Boolean autorizado) {
        this.autorizado = autorizado;
    }

    public Instant getDataExpiracao() {
        return this.dataExpiracao;
    }

    public PerfilAcessoUsuario dataExpiracao(Instant dataExpiracao) {
        this.setDataExpiracao(dataExpiracao);
        return this;
    }

    public void setDataExpiracao(Instant dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PerfilAcessoUsuario)) {
            return false;
        }
        return getId() != null && getId().equals(((PerfilAcessoUsuario) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PerfilAcessoUsuario{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", autorizado='" + getAutorizado() + "'" +
            ", dataExpiracao='" + getDataExpiracao() + "'" +
            "}";
    }
}
