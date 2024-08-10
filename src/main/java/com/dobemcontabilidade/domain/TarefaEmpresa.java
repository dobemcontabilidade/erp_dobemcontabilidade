package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TarefaEmpresa.
 */
@Entity
@Table(name = "tarefa_empresa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TarefaEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "data_hora")
    private Instant dataHora;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "assinaturaEmpresas",
            "funcionarios",
            "departamentoEmpresas",
            "tarefaEmpresas",
            "enderecoEmpresas",
            "atividadeEmpresas",
            "socios",
            "anexoEmpresas",
            "certificadoDigitals",
            "usuarioEmpresas",
            "opcaoRazaoSocialEmpresas",
            "opcaoNomeFantasiaEmpresas",
            "ramo",
            "tributacao",
            "enquadramento",
        },
        allowSetters = true
    )
    private Empresa empresa;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "pessoa",
            "areaContabilEmpresas",
            "areaContabilContadors",
            "departamentoEmpresas",
            "departamentoContadors",
            "termoAdesaoContadors",
            "bancoContadors",
            "avaliacaoContadors",
            "tarefaEmpresas",
            "perfilContador",
            "usuarioContador",
        },
        allowSetters = true
    )
    private Contador contador;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "tarefaEmpresas", "subtarefas", "documentoTarefas", "esfera", "frequencia", "competencia" },
        allowSetters = true
    )
    private Tarefa tarefa;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TarefaEmpresa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataHora() {
        return this.dataHora;
    }

    public TarefaEmpresa dataHora(Instant dataHora) {
        this.setDataHora(dataHora);
        return this;
    }

    public void setDataHora(Instant dataHora) {
        this.dataHora = dataHora;
    }

    public Empresa getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public TarefaEmpresa empresa(Empresa empresa) {
        this.setEmpresa(empresa);
        return this;
    }

    public Contador getContador() {
        return this.contador;
    }

    public void setContador(Contador contador) {
        this.contador = contador;
    }

    public TarefaEmpresa contador(Contador contador) {
        this.setContador(contador);
        return this;
    }

    public Tarefa getTarefa() {
        return this.tarefa;
    }

    public void setTarefa(Tarefa tarefa) {
        this.tarefa = tarefa;
    }

    public TarefaEmpresa tarefa(Tarefa tarefa) {
        this.setTarefa(tarefa);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TarefaEmpresa)) {
            return false;
        }
        return getId() != null && getId().equals(((TarefaEmpresa) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TarefaEmpresa{" +
            "id=" + getId() +
            ", dataHora='" + getDataHora() + "'" +
            "}";
    }
}
