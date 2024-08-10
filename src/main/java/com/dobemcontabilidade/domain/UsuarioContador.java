package com.dobemcontabilidade.domain;

import com.dobemcontabilidade.domain.enumeration.SituacaoUsuarioContadorEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A UsuarioContador.
 */
@Entity
@Table(name = "usuario_contador")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UsuarioContador implements Serializable {

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

    @Lob
    @Column(name = "token")
    private String token;

    @Column(name = "data_hora_ativacao")
    private Instant dataHoraAtivacao;

    @Column(name = "data_limite_acesso")
    private Instant dataLimiteAcesso;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao")
    private SituacaoUsuarioContadorEnum situacao;

    @JsonIgnoreProperties(
        value = {
            "pessoa",
            "areaContabilEmpresas",
            "areaContabilContadors",
            "departamentoEmpresas",
            "departamentoContadors",
            "termoAdesaoContadors",
            "bancoContadors",
            "avaliacaoContadors",
            "tarefaEmpresas",
            "perfilContador",
            "usuarioContador",
        },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Contador contador;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "pessoa", "usuarioContadors", "usuarioErp", "usuarioGestao" }, allowSetters = true)
    private Administrador administrador;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UsuarioContador id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public UsuarioContador email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return this.senha;
    }

    public UsuarioContador senha(String senha) {
        this.setSenha(senha);
        return this;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getToken() {
        return this.token;
    }

    public UsuarioContador token(String token) {
        this.setToken(token);
        return this;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getDataHoraAtivacao() {
        return this.dataHoraAtivacao;
    }

    public UsuarioContador dataHoraAtivacao(Instant dataHoraAtivacao) {
        this.setDataHoraAtivacao(dataHoraAtivacao);
        return this;
    }

    public void setDataHoraAtivacao(Instant dataHoraAtivacao) {
        this.dataHoraAtivacao = dataHoraAtivacao;
    }

    public Instant getDataLimiteAcesso() {
        return this.dataLimiteAcesso;
    }

    public UsuarioContador dataLimiteAcesso(Instant dataLimiteAcesso) {
        this.setDataLimiteAcesso(dataLimiteAcesso);
        return this;
    }

    public void setDataLimiteAcesso(Instant dataLimiteAcesso) {
        this.dataLimiteAcesso = dataLimiteAcesso;
    }

    public SituacaoUsuarioContadorEnum getSituacao() {
        return this.situacao;
    }

    public UsuarioContador situacao(SituacaoUsuarioContadorEnum situacao) {
        this.setSituacao(situacao);
        return this;
    }

    public void setSituacao(SituacaoUsuarioContadorEnum situacao) {
        this.situacao = situacao;
    }

    public Contador getContador() {
        return this.contador;
    }

    public void setContador(Contador contador) {
        this.contador = contador;
    }

    public UsuarioContador contador(Contador contador) {
        this.setContador(contador);
        return this;
    }

    public Administrador getAdministrador() {
        return this.administrador;
    }

    public void setAdministrador(Administrador administrador) {
        this.administrador = administrador;
    }

    public UsuarioContador administrador(Administrador administrador) {
        this.setAdministrador(administrador);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UsuarioContador)) {
            return false;
        }
        return getId() != null && getId().equals(((UsuarioContador) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UsuarioContador{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", senha='" + getSenha() + "'" +
            ", token='" + getToken() + "'" +
            ", dataHoraAtivacao='" + getDataHoraAtivacao() + "'" +
            ", dataLimiteAcesso='" + getDataLimiteAcesso() + "'" +
            ", situacao='" + getSituacao() + "'" +
            "}";
    }
}
