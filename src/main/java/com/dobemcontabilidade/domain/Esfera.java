package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Esfera.
 */
@Entity
@Table(name = "esfera")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Esfera implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Lob
    @Column(name = "descricao")
    private String descricao;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "esfera")
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

    public Esfera id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Esfera nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public Esfera descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Set<Tarefa> getTarefas() {
        return this.tarefas;
    }

    public void setTarefas(Set<Tarefa> tarefas) {
        if (this.tarefas != null) {
            this.tarefas.forEach(i -> i.setEsfera(null));
        }
        if (tarefas != null) {
            tarefas.forEach(i -> i.setEsfera(this));
        }
        this.tarefas = tarefas;
    }

    public Esfera tarefas(Set<Tarefa> tarefas) {
        this.setTarefas(tarefas);
        return this;
    }

    public Esfera addTarefa(Tarefa tarefa) {
        this.tarefas.add(tarefa);
        tarefa.setEsfera(this);
        return this;
    }

    public Esfera removeTarefa(Tarefa tarefa) {
        this.tarefas.remove(tarefa);
        tarefa.setEsfera(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Esfera)) {
            return false;
        }
        return getId() != null && getId().equals(((Esfera) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Esfera{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            "}";
    }
}
