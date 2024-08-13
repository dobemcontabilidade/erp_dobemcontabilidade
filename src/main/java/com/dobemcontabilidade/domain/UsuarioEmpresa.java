package com.dobemcontabilidade.domain;

import com.dobemcontabilidade.domain.enumeration.SituacaoUsuarioEmpresaEnum;
import com.dobemcontabilidade.domain.enumeration.TipoUsuarioEmpresaEnum;
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
 * A UsuarioEmpresa.
 */
@Entity
@Table(name = "usuario_empresa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UsuarioEmpresa implements Serializable {

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
    @Column(name = "situacao_usuario_empresa")
    private SituacaoUsuarioEmpresaEnum situacaoUsuarioEmpresa;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario_empresa_enum")
    private TipoUsuarioEmpresaEnum tipoUsuarioEmpresaEnum;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuarioEmpresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "grupoAcessoEmpresa", "usuarioEmpresa" }, allowSetters = true)
    private Set<GrupoAcessoUsuarioEmpresa> grupoAcessoUsuarioEmpresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuarioEmpresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "usuarioEmpresa", "usuarioContador", "criterioAvaliacaoAtor", "ordemServico" }, allowSetters = true)
    private Set<FeedBackUsuarioParaContador> feedBackUsuarioParaContadors = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuarioEmpresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "criterioAvaliacaoAtor", "usuarioEmpresa", "contador", "ordemServico" }, allowSetters = true)
    private Set<FeedBackContadorParaUsuario> feedBackContadorParaUsuarios = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "grupoAcessoEmpresas",
            "areaContabilAssinaturaEmpresas",
            "servicoContabilAssinaturaEmpresas",
            "gatewayAssinaturaEmpresas",
            "calculoPlanoAssinaturas",
            "pagamentos",
            "cobrancaEmpresas",
            "usuarioEmpresas",
            "periodoPagamento",
            "formaDePagamento",
            "planoContaAzul",
            "planoContabil",
            "empresa",
        },
        allowSetters = true
    )
    private AssinaturaEmpresa assinaturaEmpresa;

    @JsonIgnoreProperties(
        value = {
            "usuarioEmpresa",
            "estrangeiros",
            "contratoFuncionarios",
            "demissaoFuncionarios",
            "dependentesFuncionarios",
            "empresaVinculadas",
            "departamentoFuncionarios",
            "pessoa",
            "empresa",
            "profissao",
        },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "usuarioEmpresa")
    private Funcionario funcionario;

    @JsonIgnoreProperties(value = { "pessoa", "usuarioEmpresa", "empresa", "profissao" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "usuarioEmpresa")
    private Socio socio;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UsuarioEmpresa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public UsuarioEmpresa email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return this.senha;
    }

    public UsuarioEmpresa senha(String senha) {
        this.setSenha(senha);
        return this;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getToken() {
        return this.token;
    }

    public UsuarioEmpresa token(String token) {
        this.setToken(token);
        return this;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getAtivo() {
        return this.ativo;
    }

    public UsuarioEmpresa ativo(Boolean ativo) {
        this.setAtivo(ativo);
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Instant getDataHoraAtivacao() {
        return this.dataHoraAtivacao;
    }

    public UsuarioEmpresa dataHoraAtivacao(Instant dataHoraAtivacao) {
        this.setDataHoraAtivacao(dataHoraAtivacao);
        return this;
    }

    public void setDataHoraAtivacao(Instant dataHoraAtivacao) {
        this.dataHoraAtivacao = dataHoraAtivacao;
    }

    public Instant getDataLimiteAcesso() {
        return this.dataLimiteAcesso;
    }

    public UsuarioEmpresa dataLimiteAcesso(Instant dataLimiteAcesso) {
        this.setDataLimiteAcesso(dataLimiteAcesso);
        return this;
    }

    public void setDataLimiteAcesso(Instant dataLimiteAcesso) {
        this.dataLimiteAcesso = dataLimiteAcesso;
    }

    public SituacaoUsuarioEmpresaEnum getSituacaoUsuarioEmpresa() {
        return this.situacaoUsuarioEmpresa;
    }

    public UsuarioEmpresa situacaoUsuarioEmpresa(SituacaoUsuarioEmpresaEnum situacaoUsuarioEmpresa) {
        this.setSituacaoUsuarioEmpresa(situacaoUsuarioEmpresa);
        return this;
    }

    public void setSituacaoUsuarioEmpresa(SituacaoUsuarioEmpresaEnum situacaoUsuarioEmpresa) {
        this.situacaoUsuarioEmpresa = situacaoUsuarioEmpresa;
    }

    public TipoUsuarioEmpresaEnum getTipoUsuarioEmpresaEnum() {
        return this.tipoUsuarioEmpresaEnum;
    }

    public UsuarioEmpresa tipoUsuarioEmpresaEnum(TipoUsuarioEmpresaEnum tipoUsuarioEmpresaEnum) {
        this.setTipoUsuarioEmpresaEnum(tipoUsuarioEmpresaEnum);
        return this;
    }

    public void setTipoUsuarioEmpresaEnum(TipoUsuarioEmpresaEnum tipoUsuarioEmpresaEnum) {
        this.tipoUsuarioEmpresaEnum = tipoUsuarioEmpresaEnum;
    }

    public Set<GrupoAcessoUsuarioEmpresa> getGrupoAcessoUsuarioEmpresas() {
        return this.grupoAcessoUsuarioEmpresas;
    }

    public void setGrupoAcessoUsuarioEmpresas(Set<GrupoAcessoUsuarioEmpresa> grupoAcessoUsuarioEmpresas) {
        if (this.grupoAcessoUsuarioEmpresas != null) {
            this.grupoAcessoUsuarioEmpresas.forEach(i -> i.setUsuarioEmpresa(null));
        }
        if (grupoAcessoUsuarioEmpresas != null) {
            grupoAcessoUsuarioEmpresas.forEach(i -> i.setUsuarioEmpresa(this));
        }
        this.grupoAcessoUsuarioEmpresas = grupoAcessoUsuarioEmpresas;
    }

    public UsuarioEmpresa grupoAcessoUsuarioEmpresas(Set<GrupoAcessoUsuarioEmpresa> grupoAcessoUsuarioEmpresas) {
        this.setGrupoAcessoUsuarioEmpresas(grupoAcessoUsuarioEmpresas);
        return this;
    }

    public UsuarioEmpresa addGrupoAcessoUsuarioEmpresa(GrupoAcessoUsuarioEmpresa grupoAcessoUsuarioEmpresa) {
        this.grupoAcessoUsuarioEmpresas.add(grupoAcessoUsuarioEmpresa);
        grupoAcessoUsuarioEmpresa.setUsuarioEmpresa(this);
        return this;
    }

    public UsuarioEmpresa removeGrupoAcessoUsuarioEmpresa(GrupoAcessoUsuarioEmpresa grupoAcessoUsuarioEmpresa) {
        this.grupoAcessoUsuarioEmpresas.remove(grupoAcessoUsuarioEmpresa);
        grupoAcessoUsuarioEmpresa.setUsuarioEmpresa(null);
        return this;
    }

    public Set<FeedBackUsuarioParaContador> getFeedBackUsuarioParaContadors() {
        return this.feedBackUsuarioParaContadors;
    }

    public void setFeedBackUsuarioParaContadors(Set<FeedBackUsuarioParaContador> feedBackUsuarioParaContadors) {
        if (this.feedBackUsuarioParaContadors != null) {
            this.feedBackUsuarioParaContadors.forEach(i -> i.setUsuarioEmpresa(null));
        }
        if (feedBackUsuarioParaContadors != null) {
            feedBackUsuarioParaContadors.forEach(i -> i.setUsuarioEmpresa(this));
        }
        this.feedBackUsuarioParaContadors = feedBackUsuarioParaContadors;
    }

    public UsuarioEmpresa feedBackUsuarioParaContadors(Set<FeedBackUsuarioParaContador> feedBackUsuarioParaContadors) {
        this.setFeedBackUsuarioParaContadors(feedBackUsuarioParaContadors);
        return this;
    }

    public UsuarioEmpresa addFeedBackUsuarioParaContador(FeedBackUsuarioParaContador feedBackUsuarioParaContador) {
        this.feedBackUsuarioParaContadors.add(feedBackUsuarioParaContador);
        feedBackUsuarioParaContador.setUsuarioEmpresa(this);
        return this;
    }

    public UsuarioEmpresa removeFeedBackUsuarioParaContador(FeedBackUsuarioParaContador feedBackUsuarioParaContador) {
        this.feedBackUsuarioParaContadors.remove(feedBackUsuarioParaContador);
        feedBackUsuarioParaContador.setUsuarioEmpresa(null);
        return this;
    }

    public Set<FeedBackContadorParaUsuario> getFeedBackContadorParaUsuarios() {
        return this.feedBackContadorParaUsuarios;
    }

    public void setFeedBackContadorParaUsuarios(Set<FeedBackContadorParaUsuario> feedBackContadorParaUsuarios) {
        if (this.feedBackContadorParaUsuarios != null) {
            this.feedBackContadorParaUsuarios.forEach(i -> i.setUsuarioEmpresa(null));
        }
        if (feedBackContadorParaUsuarios != null) {
            feedBackContadorParaUsuarios.forEach(i -> i.setUsuarioEmpresa(this));
        }
        this.feedBackContadorParaUsuarios = feedBackContadorParaUsuarios;
    }

    public UsuarioEmpresa feedBackContadorParaUsuarios(Set<FeedBackContadorParaUsuario> feedBackContadorParaUsuarios) {
        this.setFeedBackContadorParaUsuarios(feedBackContadorParaUsuarios);
        return this;
    }

    public UsuarioEmpresa addFeedBackContadorParaUsuario(FeedBackContadorParaUsuario feedBackContadorParaUsuario) {
        this.feedBackContadorParaUsuarios.add(feedBackContadorParaUsuario);
        feedBackContadorParaUsuario.setUsuarioEmpresa(this);
        return this;
    }

    public UsuarioEmpresa removeFeedBackContadorParaUsuario(FeedBackContadorParaUsuario feedBackContadorParaUsuario) {
        this.feedBackContadorParaUsuarios.remove(feedBackContadorParaUsuario);
        feedBackContadorParaUsuario.setUsuarioEmpresa(null);
        return this;
    }

    public AssinaturaEmpresa getAssinaturaEmpresa() {
        return this.assinaturaEmpresa;
    }

    public void setAssinaturaEmpresa(AssinaturaEmpresa assinaturaEmpresa) {
        this.assinaturaEmpresa = assinaturaEmpresa;
    }

    public UsuarioEmpresa assinaturaEmpresa(AssinaturaEmpresa assinaturaEmpresa) {
        this.setAssinaturaEmpresa(assinaturaEmpresa);
        return this;
    }

    public Funcionario getFuncionario() {
        return this.funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        if (this.funcionario != null) {
            this.funcionario.setUsuarioEmpresa(null);
        }
        if (funcionario != null) {
            funcionario.setUsuarioEmpresa(this);
        }
        this.funcionario = funcionario;
    }

    public UsuarioEmpresa funcionario(Funcionario funcionario) {
        this.setFuncionario(funcionario);
        return this;
    }

    public Socio getSocio() {
        return this.socio;
    }

    public void setSocio(Socio socio) {
        if (this.socio != null) {
            this.socio.setUsuarioEmpresa(null);
        }
        if (socio != null) {
            socio.setUsuarioEmpresa(this);
        }
        this.socio = socio;
    }

    public UsuarioEmpresa socio(Socio socio) {
        this.setSocio(socio);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UsuarioEmpresa)) {
            return false;
        }
        return getId() != null && getId().equals(((UsuarioEmpresa) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UsuarioEmpresa{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", senha='" + getSenha() + "'" +
            ", token='" + getToken() + "'" +
            ", ativo='" + getAtivo() + "'" +
            ", dataHoraAtivacao='" + getDataHoraAtivacao() + "'" +
            ", dataLimiteAcesso='" + getDataLimiteAcesso() + "'" +
            ", situacaoUsuarioEmpresa='" + getSituacaoUsuarioEmpresa() + "'" +
            ", tipoUsuarioEmpresaEnum='" + getTipoUsuarioEmpresaEnum() + "'" +
            "}";
    }
}
