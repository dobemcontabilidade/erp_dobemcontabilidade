package com.dobemcontabilidade.domain;

import com.dobemcontabilidade.domain.enumeration.SituacaoUsuarioErpEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A UsuarioErp.
 */
@Entity
@Table(name = "usuario_erp")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UsuarioErp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 200)
    @Column(name = "email", length = 200, nullable = false)
    private String email;

    @Lob
    @Column(name = "senha", nullable = false)
    private String senha;

    @Column(name = "data_hora_ativacao")
    private Instant dataHoraAtivacao;

    @Column(name = "data_limite_acesso")
    private Instant dataLimiteAcesso;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao")
    private SituacaoUsuarioErpEnum situacao;

    @JsonIgnoreProperties(value = { "pessoa", "usuarioContadors", "usuarioErp", "usuarioGestao" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Administrador administrador;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UsuarioErp id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public UsuarioErp email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return this.senha;
    }

    public UsuarioErp senha(String senha) {
        this.setSenha(senha);
        return this;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Instant getDataHoraAtivacao() {
        return this.dataHoraAtivacao;
    }

    public UsuarioErp dataHoraAtivacao(Instant dataHoraAtivacao) {
        this.setDataHoraAtivacao(dataHoraAtivacao);
        return this;
    }

    public void setDataHoraAtivacao(Instant dataHoraAtivacao) {
        this.dataHoraAtivacao = dataHoraAtivacao;
    }

    public Instant getDataLimiteAcesso() {
        return this.dataLimiteAcesso;
    }

    public UsuarioErp dataLimiteAcesso(Instant dataLimiteAcesso) {
        this.setDataLimiteAcesso(dataLimiteAcesso);
        return this;
    }

    public void setDataLimiteAcesso(Instant dataLimiteAcesso) {
        this.dataLimiteAcesso = dataLimiteAcesso;
    }

    public SituacaoUsuarioErpEnum getSituacao() {
        return this.situacao;
    }

    public UsuarioErp situacao(SituacaoUsuarioErpEnum situacao) {
        this.setSituacao(situacao);
        return this;
    }

    public void setSituacao(SituacaoUsuarioErpEnum situacao) {
        this.situacao = situacao;
    }

    public Administrador getAdministrador() {
        return this.administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    public UsuarioErp administrador(Administrador administrador) {
        this.setAdministrador(administrador);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UsuarioErp)) {
            return false;
        }
        return getId() != null && getId().equals(((UsuarioErp) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UsuarioErp{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", senha='" + getSenha() + "'" +
            ", dataHoraAtivacao='" + getDataHoraAtivacao() + "'" +
            ", dataLimiteAcesso='" + getDataLimiteAcesso() + "'" +
            ", situacao='" + getSituacao() + "'" +
            "}";
    }
}
