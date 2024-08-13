package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @Size(max = 50)
    @Column(name = "nome", length = 50)
    private String nome;

    @Lob
    @Column(name = "descricao")
    private String descricao;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "esfera")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "servicoContabilEmpresaModelos",
            "servicoContabilEtapaFluxoModelos",
            "servicoContabilEtapaFluxoExecucaos",
            "anexoRequeridoServicoContabils",
            "servicoContabilOrdemServicos",
            "servicoContabilAssinaturaEmpresas",
            "tarefaEmpresaModelos",
            "areaContabil",
            "esfera",
        },
        allowSetters = true
    )
    private Set<ServicoContabil> servicoContabils = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "esfera")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "impostoEmpresas", "parcelamentoImpostos", "impostoEmpresaModelos", "esfera" }, allowSetters = true)
    private Set<Imposto> impostos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "esfera")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tarefaEmpresas", "subtarefas", "esfera", "frequencia", "competencia" }, allowSetters = true)
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

    public Set<ServicoContabil> getServicoContabils() {
        return this.servicoContabils;
    }

    public void setServicoContabils(Set<ServicoContabil> servicoContabils) {
        if (this.servicoContabils != null) {
            this.servicoContabils.forEach(i -> i.setEsfera(null));
        }
        if (servicoContabils != null) {
            servicoContabils.forEach(i -> i.setEsfera(this));
        }
        this.servicoContabils = servicoContabils;
    }

    public Esfera servicoContabils(Set<ServicoContabil> servicoContabils) {
        this.setServicoContabils(servicoContabils);
        return this;
    }

    public Esfera addServicoContabil(ServicoContabil servicoContabil) {
        this.servicoContabils.add(servicoContabil);
        servicoContabil.setEsfera(this);
        return this;
    }

    public Esfera removeServicoContabil(ServicoContabil servicoContabil) {
        this.servicoContabils.remove(servicoContabil);
        servicoContabil.setEsfera(null);
        return this;
    }

    public Set<Imposto> getImpostos() {
        return this.impostos;
    }

    public void setImpostos(Set<Imposto> impostos) {
        if (this.impostos != null) {
            this.impostos.forEach(i -> i.setEsfera(null));
        }
        if (impostos != null) {
            impostos.forEach(i -> i.setEsfera(this));
        }
        this.impostos = impostos;
    }

    public Esfera impostos(Set<Imposto> impostos) {
        this.setImpostos(impostos);
        return this;
    }

    public Esfera addImposto(Imposto imposto) {
        this.impostos.add(imposto);
        imposto.setEsfera(this);
        return this;
    }

    public Esfera removeImposto(Imposto imposto) {
        this.impostos.remove(imposto);
        imposto.setEsfera(null);
        return this;
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
