package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FeedBackUsuarioParaContador.
 */
@Entity
@Table(name = "feed_usuario_para_contador")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FeedBackUsuarioParaContador implements Serializable {

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
        value = { "grupoAcessoEmpresaUsuarioContadors", "grupoAcessoUsuarioContadors", "feedBackUsuarioParaContadors", "contador" },
        allowSetters = true
    )
    private UsuarioContador usuarioContador;

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

    public FeedBackUsuarioParaContador id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComentario() {
        return this.comentario;
    }

    public FeedBackUsuarioParaContador comentario(String comentario) {
        this.setComentario(comentario);
        return this;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Double getPontuacao() {
        return this.pontuacao;
    }

    public FeedBackUsuarioParaContador pontuacao(Double pontuacao) {
        this.setPontuacao(pontuacao);
        return this;
    }

    public void setPontuacao(Double pontuacao) {
        this.pontuacao = pontuacao;
    }

    public UsuarioEmpresa getUsuarioEmpresa() {
        return this.usuarioEmpresa;
    }

    public void setUsuarioEmpresa(UsuarioEmpresa usuarioEmpresa) {
        this.usuarioEmpresa = usuarioEmpresa;
    }

    public FeedBackUsuarioParaContador usuarioEmpresa(UsuarioEmpresa usuarioEmpresa) {
        this.setUsuarioEmpresa(usuarioEmpresa);
        return this;
    }

    public UsuarioContador getUsuarioContador() {
        return this.usuarioContador;
    }

    public void setUsuarioContador(UsuarioContador usuarioContador) {
        this.usuarioContador = usuarioContador;
    }

    public FeedBackUsuarioParaContador usuarioContador(UsuarioContador usuarioContador) {
        this.setUsuarioContador(usuarioContador);
        return this;
    }

    public CriterioAvaliacaoAtor getCriterioAvaliacaoAtor() {
        return this.criterioAvaliacaoAtor;
    }

    public void setCriterioAvaliacaoAtor(CriterioAvaliacaoAtor criterioAvaliacaoAtor) {
        this.criterioAvaliacaoAtor = criterioAvaliacaoAtor;
    }

    public FeedBackUsuarioParaContador criterioAvaliacaoAtor(CriterioAvaliacaoAtor criterioAvaliacaoAtor) {
        this.setCriterioAvaliacaoAtor(criterioAvaliacaoAtor);
        return this;
    }

    public OrdemServico getOrdemServico() {
        return this.ordemServico;
    }

    public void setOrdemServico(OrdemServico ordemServico) {
        this.ordemServico = ordemServico;
    }

    public FeedBackUsuarioParaContador ordemServico(OrdemServico ordemServico) {
        this.setOrdemServico(ordemServico);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FeedBackUsuarioParaContador)) {
            return false;
        }
        return getId() != null && getId().equals(((FeedBackUsuarioParaContador) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FeedBackUsuarioParaContador{" +
            "id=" + getId() +
            ", comentario='" + getComentario() + "'" +
            ", pontuacao=" + getPontuacao() +
            "}";
    }
}
