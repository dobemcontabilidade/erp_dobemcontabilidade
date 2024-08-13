package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A GrupoAcessoUsuarioEmpresa.
 */
@Entity
@Table(name = "grup_acess_usua_empresa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GrupoAcessoUsuarioEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "data_expiracao")
    private Instant dataExpiracao;

    @Column(name = "ilimitado")
    private Boolean ilimitado;

    @Column(name = "desabilitar")
    private Boolean desabilitar;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "funcionalidadeGrupoAcessoEmpresas", "grupoAcessoUsuarioEmpresas", "grupoAcessoEmpresaUsuarioContadors", "assinaturaEmpresa",
        },
        allowSetters = true
    )
    private GrupoAcessoEmpresa grupoAcessoEmpresa;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "grupoAcessoUsuarioEmpresas",
            "feedBackUsuarioParaContadors",
            "feedBackContadorParaUsuarios",
            "assinaturaEmpresa",
            "funcionario",
            "socio",
        },
        allowSetters = true
    )
    private UsuarioEmpresa usuarioEmpresa;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public GrupoAcessoUsuarioEmpresa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public GrupoAcessoUsuarioEmpresa nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Instant getDataExpiracao() {
        return this.dataExpiracao;
    }

    public GrupoAcessoUsuarioEmpresa dataExpiracao(Instant dataExpiracao) {
        this.setDataExpiracao(dataExpiracao);
        return this;
    }

    public void setDataExpiracao(Instant dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    public Boolean getIlimitado() {
        return this.ilimitado;
    }

    public GrupoAcessoUsuarioEmpresa ilimitado(Boolean ilimitado) {
        this.setIlimitado(ilimitado);
        return this;
    }

    public void setIlimitado(Boolean ilimitado) {
        this.ilimitado = ilimitado;
    }

    public Boolean getDesabilitar() {
        return this.desabilitar;
    }

    public GrupoAcessoUsuarioEmpresa desabilitar(Boolean desabilitar) {
        this.setDesabilitar(desabilitar);
        return this;
    }

    public void setDesabilitar(Boolean desabilitar) {
        this.desabilitar = desabilitar;
    }

    public GrupoAcessoEmpresa getGrupoAcessoEmpresa() {
        return this.grupoAcessoEmpresa;
    }

    public void setGrupoAcessoEmpresa(GrupoAcessoEmpresa grupoAcessoEmpresa) {
        this.grupoAcessoEmpresa = grupoAcessoEmpresa;
    }

    public GrupoAcessoUsuarioEmpresa grupoAcessoEmpresa(GrupoAcessoEmpresa grupoAcessoEmpresa) {
        this.setGrupoAcessoEmpresa(grupoAcessoEmpresa);
        return this;
    }

    public UsuarioEmpresa getUsuarioEmpresa() {
        return this.usuarioEmpresa;
    }

    public void setUsuarioEmpresa(UsuarioEmpresa usuarioEmpresa) {
        this.usuarioEmpresa = usuarioEmpresa;
    }

    public GrupoAcessoUsuarioEmpresa usuarioEmpresa(UsuarioEmpresa usuarioEmpresa) {
        this.setUsuarioEmpresa(usuarioEmpresa);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GrupoAcessoUsuarioEmpresa)) {
            return false;
        }
        return getId() != null && getId().equals(((GrupoAcessoUsuarioEmpresa) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GrupoAcessoUsuarioEmpresa{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", dataExpiracao='" + getDataExpiracao() + "'" +
            ", ilimitado='" + getIlimitado() + "'" +
            ", desabilitar='" + getDesabilitar() + "'" +
            "}";
    }
}
