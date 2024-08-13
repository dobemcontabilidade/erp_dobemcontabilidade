package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ContadorResponsavelTarefaRecorrente.
 */
@Entity
@Table(name = "cont_respo_tare_recorrente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContadorResponsavelTarefaRecorrente implements Serializable {

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

    @Column(name = "concluida")
    private Boolean concluida;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "agendaTarefaRecorrenteExecucaos",
            "anexoTarefaRecorrenteExecucaos",
            "subTarefaRecorrentes",
            "contadorResponsavelTarefaRecorrentes",
            "tarefaRecorrente",
        },
        allowSetters = true
    )
    private TarefaRecorrenteExecucao tarefaRecorrenteExecucao;

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

    public ContadorResponsavelTarefaRecorrente id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataAtribuicao() {
        return this.dataAtribuicao;
    }

    public ContadorResponsavelTarefaRecorrente dataAtribuicao(Instant dataAtribuicao) {
        this.setDataAtribuicao(dataAtribuicao);
        return this;
    }

    public void setDataAtribuicao(Instant dataAtribuicao) {
        this.dataAtribuicao = dataAtribuicao;
    }

    public Instant getDataRevogacao() {
        return this.dataRevogacao;
    }

    public ContadorResponsavelTarefaRecorrente dataRevogacao(Instant dataRevogacao) {
        this.setDataRevogacao(dataRevogacao);
        return this;
    }

    public void setDataRevogacao(Instant dataRevogacao) {
        this.dataRevogacao = dataRevogacao;
    }

    public Boolean getConcluida() {
        return this.concluida;
    }

    public ContadorResponsavelTarefaRecorrente concluida(Boolean concluida) {
        this.setConcluida(concluida);
        return this;
    }

    public void setConcluida(Boolean concluida) {
        this.concluida = concluida;
    }

    public TarefaRecorrenteExecucao getTarefaRecorrenteExecucao() {
        return this.tarefaRecorrenteExecucao;
    }

    public void setTarefaRecorrenteExecucao(TarefaRecorrenteExecucao tarefaRecorrenteExecucao) {
        this.tarefaRecorrenteExecucao = tarefaRecorrenteExecucao;
    }

    public ContadorResponsavelTarefaRecorrente tarefaRecorrenteExecucao(TarefaRecorrenteExecucao tarefaRecorrenteExecucao) {
        this.setTarefaRecorrenteExecucao(tarefaRecorrenteExecucao);
        return this;
    }

    public Contador getContador() {
        return this.contador;
    }

    public void setContador(Contador contador) {
        this.contador = contador;
    }

    public ContadorResponsavelTarefaRecorrente contador(Contador contador) {
        this.setContador(contador);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContadorResponsavelTarefaRecorrente)) {
            return false;
        }
        return getId() != null && getId().equals(((ContadorResponsavelTarefaRecorrente) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContadorResponsavelTarefaRecorrente{" +
            "id=" + getId() +
            ", dataAtribuicao='" + getDataAtribuicao() + "'" +
            ", dataRevogacao='" + getDataRevogacao() + "'" +
            ", concluida='" + getConcluida() + "'" +
            "}";
    }
}
