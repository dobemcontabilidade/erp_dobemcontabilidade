package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ContadorResponsavelOrdemServico.
 */
@Entity
@Table(name = "cont_respo_ordem_serv")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContadorResponsavelOrdemServico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "data_atribuicao")
    private Instant dataAtribuicao;

    @Column(name = "data_revogacao")
    private Instant dataRevogacao;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "agendaTarefaOrdemServicoExecucaos",
            "anexoOrdemServicoExecucaos",
            "subTarefaOrdemServicos",
            "contadorResponsavelOrdemServicos",
            "tarefaOrdemServico",
        },
        allowSetters = true
    )
    private TarefaOrdemServicoExecucao tarefaOrdemServicoExecucao;

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

    public ContadorResponsavelOrdemServico id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataAtribuicao() {
        return this.dataAtribuicao;
    }

    public ContadorResponsavelOrdemServico dataAtribuicao(Instant dataAtribuicao) {
        this.setDataAtribuicao(dataAtribuicao);
        return this;
    }

    public void setDataAtribuicao(Instant dataAtribuicao) {
        this.dataAtribuicao = dataAtribuicao;
    }

    public Instant getDataRevogacao() {
        return this.dataRevogacao;
    }

    public ContadorResponsavelOrdemServico dataRevogacao(Instant dataRevogacao) {
        this.setDataRevogacao(dataRevogacao);
        return this;
    }

    public void setDataRevogacao(Instant dataRevogacao) {
        this.dataRevogacao = dataRevogacao;
    }

    public TarefaOrdemServicoExecucao getTarefaOrdemServicoExecucao() {
        return this.tarefaOrdemServicoExecucao;
    }

    public void setTarefaOrdemServicoExecucao(TarefaOrdemServicoExecucao tarefaOrdemServicoExecucao) {
        this.tarefaOrdemServicoExecucao = tarefaOrdemServicoExecucao;
    }

    public ContadorResponsavelOrdemServico tarefaOrdemServicoExecucao(TarefaOrdemServicoExecucao tarefaOrdemServicoExecucao) {
        this.setTarefaOrdemServicoExecucao(tarefaOrdemServicoExecucao);
        return this;
    }

    public Contador getContador() {
        return this.contador;
    }

    public void setContador(Contador contador) {
        this.contador = contador;
    }

    public ContadorResponsavelOrdemServico contador(Contador contador) {
        this.setContador(contador);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContadorResponsavelOrdemServico)) {
            return false;
        }
        return getId() != null && getId().equals(((ContadorResponsavelOrdemServico) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContadorResponsavelOrdemServico{" +
            "id=" + getId() +
            ", dataAtribuicao='" + getDataAtribuicao() + "'" +
            ", dataRevogacao='" + getDataRevogacao() + "'" +
            "}";
    }
}
