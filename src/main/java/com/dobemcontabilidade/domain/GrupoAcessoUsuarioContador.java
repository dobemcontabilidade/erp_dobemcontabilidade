package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A GrupoAcessoUsuarioContador.
 */
@Entity
@Table(name = "grou_acess_usua_cont")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GrupoAcessoUsuarioContador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "data_expiracao")
    private Instant dataExpiracao;

    @Column(name = "ilimitado")
    private Boolean ilimitado;

    @Column(name = "desabilitar")
    private Boolean desabilitar;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "grupoAcessoEmpresaUsuarioContadors", "grupoAcessoUsuarioContadors", "feedBackUsuarioParaContadors", "contador" },
        allowSetters = true
    )
    private UsuarioContador usuarioContador;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "funcionalidadeGrupoAcessoPadraos", "grupoAcessoUsuarioContadors" }, allowSetters = true)
    private GrupoAcessoPadrao grupoAcessoPadrao;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public GrupoAcessoUsuarioContador id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataExpiracao() {
        return this.dataExpiracao;
    }

    public GrupoAcessoUsuarioContador dataExpiracao(Instant dataExpiracao) {
        this.setDataExpiracao(dataExpiracao);
        return this;
    }

    public void setDataExpiracao(Instant dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    public Boolean getIlimitado() {
        return this.ilimitado;
    }

    public GrupoAcessoUsuarioContador ilimitado(Boolean ilimitado) {
        this.setIlimitado(ilimitado);
        return this;
    }

    public void setIlimitado(Boolean ilimitado) {
        this.ilimitado = ilimitado;
    }

    public Boolean getDesabilitar() {
        return this.desabilitar;
    }

    public GrupoAcessoUsuarioContador desabilitar(Boolean desabilitar) {
        this.setDesabilitar(desabilitar);
        return this;
    }

    public void setDesabilitar(Boolean desabilitar) {
        this.desabilitar = desabilitar;
    }

    public UsuarioContador getUsuarioContador() {
        return this.usuarioContador;
    }

    public void setUsuarioContador(UsuarioContador usuarioContador) {
        this.usuarioContador = usuarioContador;
    }

    public GrupoAcessoUsuarioContador usuarioContador(UsuarioContador usuarioContador) {
        this.setUsuarioContador(usuarioContador);
        return this;
    }

    public GrupoAcessoPadrao getGrupoAcessoPadrao() {
        return this.grupoAcessoPadrao;
    }

    public void setGrupoAcessoPadrao(GrupoAcessoPadrao grupoAcessoPadrao) {
        this.grupoAcessoPadrao = grupoAcessoPadrao;
    }

    public GrupoAcessoUsuarioContador grupoAcessoPadrao(GrupoAcessoPadrao grupoAcessoPadrao) {
        this.setGrupoAcessoPadrao(grupoAcessoPadrao);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GrupoAcessoUsuarioContador)) {
            return false;
        }
        return getId() != null && getId().equals(((GrupoAcessoUsuarioContador) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GrupoAcessoUsuarioContador{" +
            "id=" + getId() +
            ", dataExpiracao='" + getDataExpiracao() + "'" +
            ", ilimitado='" + getIlimitado() + "'" +
            ", desabilitar='" + getDesabilitar() + "'" +
            "}";
    }
}
