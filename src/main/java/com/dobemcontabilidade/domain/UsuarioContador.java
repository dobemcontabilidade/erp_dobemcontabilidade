package com.dobemcontabilidade.domain;

import com.dobemcontabilidade.domain.enumeration.SituacaoUsuarioContadorEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
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

    @Column(name = "ativo")
    private Boolean ativo;

    @Column(name = "data_hora_ativacao")
    private Instant dataHoraAtivacao;

    @Column(name = "data_limite_acesso")
    private Instant dataLimiteAcesso;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao")
    private SituacaoUsuarioContadorEnum situacao;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuarioContador")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "usuarioContador", "permisao", "grupoAcessoEmpresa" }, allowSetters = true)
    private Set<GrupoAcessoEmpresaUsuarioContador> grupoAcessoEmpresaUsuarioContadors = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuarioContador")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "usuarioContador", "grupoAcessoPadrao" }, allowSetters = true)
    private Set<GrupoAcessoUsuarioContador> grupoAcessoUsuarioContadors = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuarioContador")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "usuarioEmpresa", "usuarioContador", "criterioAvaliacaoAtor", "ordemServico" }, allowSetters = true)
    private Set<FeedBackUsuarioParaContador> feedBackUsuarioParaContadors = new HashSet<>();

    @JsonIgnoreProperties(
        value = {
            "pessoa",
            "usuarioContador",
            "areaContabilAssinaturaEmpresas",
            "feedBackContadorParaUsuarios",
            "ordemServicos",
            "areaContabilContadors",
            "contadorResponsavelOrdemServicos",
            "contadorResponsavelTarefaRecorrentes",
            "departamentoEmpresas",
            "departamentoContadors",
            "termoAdesaoContadors",
            "avaliacaoContadors",
            "tarefaEmpresas",
            "perfilContador",
        },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "usuarioContador")
    private Contador contador;

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

    public Boolean getAtivo() {
        return this.ativo;
    }

    public UsuarioContador ativo(Boolean ativo) {
        this.setAtivo(ativo);
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
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

    public Set<GrupoAcessoEmpresaUsuarioContador> getGrupoAcessoEmpresaUsuarioContadors() {
        return this.grupoAcessoEmpresaUsuarioContadors;
    }

    public void setGrupoAcessoEmpresaUsuarioContadors(Set<GrupoAcessoEmpresaUsuarioContador> grupoAcessoEmpresaUsuarioContadors) {
        if (this.grupoAcessoEmpresaUsuarioContadors != null) {
            this.grupoAcessoEmpresaUsuarioContadors.forEach(i -> i.setUsuarioContador(null));
        }
        if (grupoAcessoEmpresaUsuarioContadors != null) {
            grupoAcessoEmpresaUsuarioContadors.forEach(i -> i.setUsuarioContador(this));
        }
        this.grupoAcessoEmpresaUsuarioContadors = grupoAcessoEmpresaUsuarioContadors;
    }

    public UsuarioContador grupoAcessoEmpresaUsuarioContadors(Set<GrupoAcessoEmpresaUsuarioContador> grupoAcessoEmpresaUsuarioContadors) {
        this.setGrupoAcessoEmpresaUsuarioContadors(grupoAcessoEmpresaUsuarioContadors);
        return this;
    }

    public UsuarioContador addGrupoAcessoEmpresaUsuarioContador(GrupoAcessoEmpresaUsuarioContador grupoAcessoEmpresaUsuarioContador) {
        this.grupoAcessoEmpresaUsuarioContadors.add(grupoAcessoEmpresaUsuarioContador);
        grupoAcessoEmpresaUsuarioContador.setUsuarioContador(this);
        return this;
    }

    public UsuarioContador removeGrupoAcessoEmpresaUsuarioContador(GrupoAcessoEmpresaUsuarioContador grupoAcessoEmpresaUsuarioContador) {
        this.grupoAcessoEmpresaUsuarioContadors.remove(grupoAcessoEmpresaUsuarioContador);
        grupoAcessoEmpresaUsuarioContador.setUsuarioContador(null);
        return this;
    }

    public Set<GrupoAcessoUsuarioContador> getGrupoAcessoUsuarioContadors() {
        return this.grupoAcessoUsuarioContadors;
    }

    public void setGrupoAcessoUsuarioContadors(Set<GrupoAcessoUsuarioContador> grupoAcessoUsuarioContadors) {
        if (this.grupoAcessoUsuarioContadors != null) {
            this.grupoAcessoUsuarioContadors.forEach(i -> i.setUsuarioContador(null));
        }
        if (grupoAcessoUsuarioContadors != null) {
            grupoAcessoUsuarioContadors.forEach(i -> i.setUsuarioContador(this));
        }
        this.grupoAcessoUsuarioContadors = grupoAcessoUsuarioContadors;
    }

    public UsuarioContador grupoAcessoUsuarioContadors(Set<GrupoAcessoUsuarioContador> grupoAcessoUsuarioContadors) {
        this.setGrupoAcessoUsuarioContadors(grupoAcessoUsuarioContadors);
        return this;
    }

    public UsuarioContador addGrupoAcessoUsuarioContador(GrupoAcessoUsuarioContador grupoAcessoUsuarioContador) {
        this.grupoAcessoUsuarioContadors.add(grupoAcessoUsuarioContador);
        grupoAcessoUsuarioContador.setUsuarioContador(this);
        return this;
    }

    public UsuarioContador removeGrupoAcessoUsuarioContador(GrupoAcessoUsuarioContador grupoAcessoUsuarioContador) {
        this.grupoAcessoUsuarioContadors.remove(grupoAcessoUsuarioContador);
        grupoAcessoUsuarioContador.setUsuarioContador(null);
        return this;
    }

    public Set<FeedBackUsuarioParaContador> getFeedBackUsuarioParaContadors() {
        return this.feedBackUsuarioParaContadors;
    }

    public void setFeedBackUsuarioParaContadors(Set<FeedBackUsuarioParaContador> feedBackUsuarioParaContadors) {
        if (this.feedBackUsuarioParaContadors != null) {
            this.feedBackUsuarioParaContadors.forEach(i -> i.setUsuarioContador(null));
        }
        if (feedBackUsuarioParaContadors != null) {
            feedBackUsuarioParaContadors.forEach(i -> i.setUsuarioContador(this));
        }
        this.feedBackUsuarioParaContadors = feedBackUsuarioParaContadors;
    }

    public UsuarioContador feedBackUsuarioParaContadors(Set<FeedBackUsuarioParaContador> feedBackUsuarioParaContadors) {
        this.setFeedBackUsuarioParaContadors(feedBackUsuarioParaContadors);
        return this;
    }

    public UsuarioContador addFeedBackUsuarioParaContador(FeedBackUsuarioParaContador feedBackUsuarioParaContador) {
        this.feedBackUsuarioParaContadors.add(feedBackUsuarioParaContador);
        feedBackUsuarioParaContador.setUsuarioContador(this);
        return this;
    }

    public UsuarioContador removeFeedBackUsuarioParaContador(FeedBackUsuarioParaContador feedBackUsuarioParaContador) {
        this.feedBackUsuarioParaContadors.remove(feedBackUsuarioParaContador);
        feedBackUsuarioParaContador.setUsuarioContador(null);
        return this;
    }

    public Contador getContador() {
        return this.contador;
    }

    public void setContador(Contador contador) {
        if (this.contador != null) {
            this.contador.setUsuarioContador(null);
        }
        if (contador != null) {
            contador.setUsuarioContador(this);
        }
        this.contador = contador;
    }

    public UsuarioContador contador(Contador contador) {
        this.setContador(contador);
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
            ", ativo='" + getAtivo() + "'" +
            ", dataHoraAtivacao='" + getDataHoraAtivacao() + "'" +
            ", dataLimiteAcesso='" + getDataLimiteAcesso() + "'" +
            ", situacao='" + getSituacao() + "'" +
            "}";
    }
}
