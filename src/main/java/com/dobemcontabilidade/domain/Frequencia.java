package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Frequencia.
 */
@Entity
@Table(name = "frequencia")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Frequencia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "prioridade")
    private String prioridade;

    @Lob
    @Column(name = "descricao")
    private String descricao;

    @Column(name = "numero_dias")
    private Integer numeroDias;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "frequencia")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "tarefaEmpresas", "subtarefas", "documentoTarefas", "esfera", "frequencia", "competencia" },
        allowSetters = true
    )
    private Set<Tarefa> tarefas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Frequencia id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Frequencia nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPrioridade() {
        return this.prioridade;
    }

    public Frequencia prioridade(String prioridade) {
        this.setPrioridade(prioridade);
        return this;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Frequencia descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getNumeroDias() {
        return this.numeroDias;
    }

    public Frequencia numeroDias(Integer numeroDias) {
        this.setNumeroDias(numeroDias);
        return this;
    }

    public void setNumeroDias(Integer numeroDias) {
        this.numeroDias = numeroDias;
    }

    public Set<Tarefa> getTarefas() {
        return this.tarefas;
    }

    public void setTarefas(Set<Tarefa> tarefas) {
        if (this.tarefas != null) {
            this.tarefas.forEach(i -> i.setFrequencia(null));
        }
        if (tarefas != null) {
            tarefas.forEach(i -> i.setFrequencia(this));
        }
        this.tarefas = tarefas;
    }

    public Frequencia tarefas(Set<Tarefa> tarefas) {
        this.setTarefas(tarefas);
        return this;
    }

    public Frequencia addTarefa(Tarefa tarefa) {
        this.tarefas.add(tarefa);
        tarefa.setFrequencia(this);
        return this;
    }

    public Frequencia removeTarefa(Tarefa tarefa) {
        this.tarefas.remove(tarefa);
        tarefa.setFrequencia(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Frequencia)) {
            return false;
        }
        return getId() != null && getId().equals(((Frequencia) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Frequencia{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", prioridade='" + getPrioridade() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", numeroDias=" + getNumeroDias() +
            "}";
    }
}
