package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SubTarefaRecorrente.
 */
@Entity
@Table(name = "sub_tarefa_recorrente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SubTarefaRecorrente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "ordem")
    private Integer ordem;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SubTarefaRecorrente id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public SubTarefaRecorrente nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public SubTarefaRecorrente descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getOrdem() {
        return this.ordem;
    }

    public SubTarefaRecorrente ordem(Integer ordem) {
        this.setOrdem(ordem);
        return this;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public Boolean getConcluida() {
        return this.concluida;
    }

    public SubTarefaRecorrente concluida(Boolean concluida) {
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

    public SubTarefaRecorrente tarefaRecorrenteExecucao(TarefaRecorrenteExecucao tarefaRecorrenteExecucao) {
        this.setTarefaRecorrenteExecucao(tarefaRecorrenteExecucao);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubTarefaRecorrente)) {
            return false;
        }
        return getId() != null && getId().equals(((SubTarefaRecorrente) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubTarefaRecorrente{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", ordem=" + getOrdem() +
            ", concluida='" + getConcluida() + "'" +
            "}";
    }
}
