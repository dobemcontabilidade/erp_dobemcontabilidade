package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FeedBackContadorParaUsuario.
 */
@Entity
@Table(name = "feed_contador_para_usuario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FeedBackContadorParaUsuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "pontuacao")
    private Double pontuacao;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "feedBackUsuarioParaContadors", "feedBackContadorParaUsuarios", "criterioAvaliacao", "atorAvaliado" },
        allowSetters = true
    )
    private CriterioAvaliacaoAtor criterioAvaliacaoAtor;

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

    @ManyToOne(optional = false)
    @NotNull
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
    private Contador contador;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "feedBackUsuarioParaContadors",
            "feedBackContadorParaUsuarios",
            "etapaFluxoExecucaos",
            "servicoContabilOrdemServicos",
            "empresa",
            "contador",
            "fluxoModelo",
        },
        allowSetters = true
    )
    private OrdemServico ordemServico;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FeedBackContadorParaUsuario id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComentario() {
        return this.comentario;
    }

    public FeedBackContadorParaUsuario comentario(String comentario) {
        this.setComentario(comentario);
        return this;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Double getPontuacao() {
        return this.pontuacao;
    }

    public FeedBackContadorParaUsuario pontuacao(Double pontuacao) {
        this.setPontuacao(pontuacao);
        return this;
    }

    public void setPontuacao(Double pontuacao) {
        this.pontuacao = pontuacao;
    }

    public CriterioAvaliacaoAtor getCriterioAvaliacaoAtor() {
        return this.criterioAvaliacaoAtor;
    }

    public void setCriterioAvaliacaoAtor(CriterioAvaliacaoAtor criterioAvaliacaoAtor) {
        this.criterioAvaliacaoAtor = criterioAvaliacaoAtor;
    }

    public FeedBackContadorParaUsuario criterioAvaliacaoAtor(CriterioAvaliacaoAtor criterioAvaliacaoAtor) {
        this.setCriterioAvaliacaoAtor(criterioAvaliacaoAtor);
        return this;
    }

    public UsuarioEmpresa getUsuarioEmpresa() {
        return this.usuarioEmpresa;
    }

    public void setUsuarioEmpresa(UsuarioEmpresa usuarioEmpresa) {
        this.usuarioEmpresa = usuarioEmpresa;
    }

    public FeedBackContadorParaUsuario usuarioEmpresa(UsuarioEmpresa usuarioEmpresa) {
        this.setUsuarioEmpresa(usuarioEmpresa);
        return this;
    }

    public Contador getContador() {
        return this.contador;
    }

    public void setContador(Contador contador) {
        this.contador = contador;
    }

    public FeedBackContadorParaUsuario contador(Contador contador) {
        this.setContador(contador);
        return this;
    }

    public OrdemServico getOrdemServico() {
        return this.ordemServico;
    }

    public void setOrdemServico(OrdemServico ordemServico) {
        this.ordemServico = ordemServico;
    }

    public FeedBackContadorParaUsuario ordemServico(OrdemServico ordemServico) {
        this.setOrdemServico(ordemServico);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FeedBackContadorParaUsuario)) {
            return false;
        }
        return getId() != null && getId().equals(((FeedBackContadorParaUsuario) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FeedBackContadorParaUsuario{" +
            "id=" + getId() +
            ", comentario='" + getComentario() + "'" +
            ", pontuacao=" + getPontuacao() +
            "}";
    }
}
