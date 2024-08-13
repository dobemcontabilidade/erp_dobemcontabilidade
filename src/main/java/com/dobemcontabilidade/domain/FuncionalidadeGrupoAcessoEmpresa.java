package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FuncionalidadeGrupoAcessoEmpresa.
 */
@Entity
@Table(name = "func_grup_acess_empresa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FuncionalidadeGrupoAcessoEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "ativa")
    private String ativa;

    @Column(name = "data_expiracao")
    private Instant dataExpiracao;

    @Column(name = "ilimitado")
    private Boolean ilimitado;

    @Column(name = "desabilitar")
    private Boolean desabilitar;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "funcionalidadeGrupoAcessoPadraos", "funcionalidadeGrupoAcessoEmpresas", "modulo" },
        allowSetters = true
    )
    private Funcionalidade funcionalidade;

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
        value = { "funcionalidadeGrupoAcessoPadraos", "funcionalidadeGrupoAcessoEmpresas", "grupoAcessoEmpresaUsuarioContadors" },
        allowSetters = true
    )
    private Permisao permisao;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FuncionalidadeGrupoAcessoEmpresa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAtiva() {
        return this.ativa;
    }

    public FuncionalidadeGrupoAcessoEmpresa ativa(String ativa) {
        this.setAtiva(ativa);
        return this;
    }

    public void setAtiva(String ativa) {
        this.ativa = ativa;
    }

    public Instant getDataExpiracao() {
        return this.dataExpiracao;
    }

    public FuncionalidadeGrupoAcessoEmpresa dataExpiracao(Instant dataExpiracao) {
        this.setDataExpiracao(dataExpiracao);
        return this;
    }

    public void setDataExpiracao(Instant dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    public Boolean getIlimitado() {
        return this.ilimitado;
    }

    public FuncionalidadeGrupoAcessoEmpresa ilimitado(Boolean ilimitado) {
        this.setIlimitado(ilimitado);
        return this;
    }

    public void setIlimitado(Boolean ilimitado) {
        this.ilimitado = ilimitado;
    }

    public Boolean getDesabilitar() {
        return this.desabilitar;
    }

    public FuncionalidadeGrupoAcessoEmpresa desabilitar(Boolean desabilitar) {
        this.setDesabilitar(desabilitar);
        return this;
    }

    public void setDesabilitar(Boolean desabilitar) {
        this.desabilitar = desabilitar;
    }

    public Funcionalidade getFuncionalidade() {
        return this.funcionalidade;
    }

    public void setFuncionalidade(Funcionalidade funcionalidade) {
        this.funcionalidade = funcionalidade;
    }

    public FuncionalidadeGrupoAcessoEmpresa funcionalidade(Funcionalidade funcionalidade) {
        this.setFuncionalidade(funcionalidade);
        return this;
    }

    public GrupoAcessoEmpresa getGrupoAcessoEmpresa() {
        return this.grupoAcessoEmpresa;
    }

    public void setGrupoAcessoEmpresa(GrupoAcessoEmpresa grupoAcessoEmpresa) {
        this.grupoAcessoEmpresa = grupoAcessoEmpresa;
    }

    public FuncionalidadeGrupoAcessoEmpresa grupoAcessoEmpresa(GrupoAcessoEmpresa grupoAcessoEmpresa) {
        this.setGrupoAcessoEmpresa(grupoAcessoEmpresa);
        return this;
    }

    public Permisao getPermisao() {
        return this.permisao;
    }

    public void setPermisao(Permisao permisao) {
        this.permisao = permisao;
    }

    public FuncionalidadeGrupoAcessoEmpresa permisao(Permisao permisao) {
        this.setPermisao(permisao);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FuncionalidadeGrupoAcessoEmpresa)) {
            return false;
        }
        return getId() != null && getId().equals(((FuncionalidadeGrupoAcessoEmpresa) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FuncionalidadeGrupoAcessoEmpresa{" +
            "id=" + getId() +
            ", ativa='" + getAtiva() + "'" +
            ", dataExpiracao='" + getDataExpiracao() + "'" +
            ", ilimitado='" + getIlimitado() + "'" +
            ", desabilitar='" + getDesabilitar() + "'" +
            "}";
    }
}
