package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A GrupoAcessoEmpresaUsuarioContador.
 */
@Entity
@Table(name = "grup_acess_empresa_usua_cont")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GrupoAcessoEmpresaUsuarioContador implements Serializable {

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
        value = { "grupoAcessoEmpresaUsuarioContadors", "grupoAcessoUsuarioContadors", "feedBackUsuarioParaContadors", "contador" },
        allowSetters = true
    )
    private UsuarioContador usuarioContador;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "funcionalidadeGrupoAcessoPadraos", "funcionalidadeGrupoAcessoEmpresas", "grupoAcessoEmpresaUsuarioContadors" },
        allowSetters = true
    )
    private Permisao permisao;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "funcionalidadeGrupoAcessoEmpresas", "grupoAcessoUsuarioEmpresas", "grupoAcessoEmpresaUsuarioContadors", "assinaturaEmpresa",
        },
        allowSetters = true
    )
    private GrupoAcessoEmpresa grupoAcessoEmpresa;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public GrupoAcessoEmpresaUsuarioContador id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public GrupoAcessoEmpresaUsuarioContador nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Instant getDataExpiracao() {
        return this.dataExpiracao;
    }

    public GrupoAcessoEmpresaUsuarioContador dataExpiracao(Instant dataExpiracao) {
        this.setDataExpiracao(dataExpiracao);
        return this;
    }

    public void setDataExpiracao(Instant dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    public Boolean getIlimitado() {
        return this.ilimitado;
    }

    public GrupoAcessoEmpresaUsuarioContador ilimitado(Boolean ilimitado) {
        this.setIlimitado(ilimitado);
        return this;
    }

    public void setIlimitado(Boolean ilimitado) {
        this.ilimitado = ilimitado;
    }

    public Boolean getDesabilitar() {
        return this.desabilitar;
    }

    public GrupoAcessoEmpresaUsuarioContador desabilitar(Boolean desabilitar) {
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

    public GrupoAcessoEmpresaUsuarioContador usuarioContador(UsuarioContador usuarioContador) {
        this.setUsuarioContador(usuarioContador);
        return this;
    }

    public Permisao getPermisao() {
        return this.permisao;
    }

    public void setPermisao(Permisao permisao) {
        this.permisao = permisao;
    }

    public GrupoAcessoEmpresaUsuarioContador permisao(Permisao permisao) {
        this.setPermisao(permisao);
        return this;
    }

    public GrupoAcessoEmpresa getGrupoAcessoEmpresa() {
        return this.grupoAcessoEmpresa;
    }

    public void setGrupoAcessoEmpresa(GrupoAcessoEmpresa grupoAcessoEmpresa) {
        this.grupoAcessoEmpresa = grupoAcessoEmpresa;
    }

    public GrupoAcessoEmpresaUsuarioContador grupoAcessoEmpresa(GrupoAcessoEmpresa grupoAcessoEmpresa) {
        this.setGrupoAcessoEmpresa(grupoAcessoEmpresa);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GrupoAcessoEmpresaUsuarioContador)) {
            return false;
        }
        return getId() != null && getId().equals(((GrupoAcessoEmpresaUsuarioContador) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GrupoAcessoEmpresaUsuarioContador{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", dataExpiracao='" + getDataExpiracao() + "'" +
            ", ilimitado='" + getIlimitado() + "'" +
            ", desabilitar='" + getDesabilitar() + "'" +
            "}";
    }
}
