package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Competencia.
 */
@Entity
@Table(name = "competencia")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Competencia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "numero")
    private Integer numero;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "competencia")
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

    public Competencia id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Competencia nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNumero() {
        return this.numero;
    }

    public Competencia numero(Integer numero) {
        this.setNumero(numero);
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Set<Tarefa> getTarefas() {
        return this.tarefas;
    }

    public void setTarefas(Set<Tarefa> tarefas) {
        if (this.tarefas != null) {
            this.tarefas.forEach(i -> i.setCompetencia(null));
        }
        if (tarefas != null) {
            tarefas.forEach(i -> i.setCompetencia(this));
        }
        this.tarefas = tarefas;
    }

    public Competencia tarefas(Set<Tarefa> tarefas) {
        this.setTarefas(tarefas);
        return this;
    }

    public Competencia addTarefa(Tarefa tarefa) {
        this.tarefas.add(tarefa);
        tarefa.setCompetencia(this);
        return this;
    }

    public Competencia removeTarefa(Tarefa tarefa) {
        this.tarefas.remove(tarefa);
        tarefa.setCompetencia(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Competencia)) {
            return false;
        }
        return getId() != null && getId().equals(((Competencia) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Competencia{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", numero=" + getNumero() +
            "}";
    }
}
