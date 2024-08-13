package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AreaContabilAssinaturaEmpresa.
 */
@Entity
@Table(name = "area_cont_assin_empresa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AreaContabilAssinaturaEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "ativo")
    private Boolean ativo;

    @Column(name = "data_atribuicao")
    private Instant dataAtribuicao;

    @Column(name = "data_revogacao")
    private Instant dataRevogacao;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "areaContabilAssinaturaEmpresas", "servicoContabils", "areaContabilContadors" }, allowSetters = true)
    private AreaContabil areaContabil;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AreaContabilAssinaturaEmpresa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAtivo() {
        return this.ativo;
    }

    public AreaContabilAssinaturaEmpresa ativo(Boolean ativo) {
        this.setAtivo(ativo);
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Instant getDataAtribuicao() {
        return this.dataAtribuicao;
    }

    public AreaContabilAssinaturaEmpresa dataAtribuicao(Instant dataAtribuicao) {
        this.setDataAtribuicao(dataAtribuicao);
        return this;
    }

    public void setDataAtribuicao(Instant dataAtribuicao) {
        this.dataAtribuicao = dataAtribuicao;
    }

    public Instant getDataRevogacao() {
        return this.dataRevogacao;
    }

    public AreaContabilAssinaturaEmpresa dataRevogacao(Instant dataRevogacao) {
        this.setDataRevogacao(dataRevogacao);
        return this;
    }

    public void setDataRevogacao(Instant dataRevogacao) {
        this.dataRevogacao = dataRevogacao;
    }

    public AreaContabil getAreaContabil() {
        return this.areaContabil;
    }

    public void setAreaContabil(AreaContabil areaContabil) {
        this.areaContabil = areaContabil;
    }

    public AreaContabilAssinaturaEmpresa areaContabil(AreaContabil areaContabil) {
        this.setAreaContabil(areaContabil);
        return this;
    }

    public AssinaturaEmpresa getAssinaturaEmpresa() {
        return this.assinaturaEmpresa;
    }

    public void setAssinaturaEmpresa(AssinaturaEmpresa assinaturaEmpresa) {
        this.assinaturaEmpresa = assinaturaEmpresa;
    }

    public AreaContabilAssinaturaEmpresa assinaturaEmpresa(AssinaturaEmpresa assinaturaEmpresa) {
        this.setAssinaturaEmpresa(assinaturaEmpresa);
        return this;
    }

    public Contador getContador() {
        return this.contador;
    }

    public void setContador(Contador contador) {
        this.contador = contador;
    }

    public AreaContabilAssinaturaEmpresa contador(Contador contador) {
        this.setContador(contador);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AreaContabilAssinaturaEmpresa)) {
            return false;
        }
        return getId() != null && getId().equals(((AreaContabilAssinaturaEmpresa) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AreaContabilAssinaturaEmpresa{" +
            "id=" + getId() +
            ", ativo='" + getAtivo() + "'" +
            ", dataAtribuicao='" + getDataAtribuicao() + "'" +
            ", dataRevogacao='" + getDataRevogacao() + "'" +
            "}";
    }
}
