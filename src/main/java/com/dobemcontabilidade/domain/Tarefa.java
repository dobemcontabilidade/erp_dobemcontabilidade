package com.dobemcontabilidade.domain;

import com.dobemcontabilidade.domain.enumeration.TipoTarefaEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Tarefa.
 */
@Entity
@Table(name = "tarefa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Tarefa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "numero_dias")
    private Integer numeroDias;

    @Column(name = "dia_util")
    private Boolean diaUtil;

    @Column(name = "valor")
    private Double valor;

    @Column(name = "notificar_cliente")
    private Boolean notificarCliente;

    @Column(name = "gera_multa")
    private Boolean geraMulta;

    @Column(name = "exibir_empresa")
    private Boolean exibirEmpresa;

    @Column(name = "data_legal")
    private Instant dataLegal;

    @Column(name = "pontos")
    private Integer pontos;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_tarefa")
    private TipoTarefaEnum tipoTarefa;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tarefa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "empresa", "contador", "tarefa" }, allowSetters = true)
    private Set<TarefaEmpresa> tarefaEmpresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tarefa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tarefa" }, allowSetters = true)
    private Set<Subtarefa> subtarefas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tarefa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tarefa" }, allowSetters = true)
    private Set<DocumentoTarefa> documentoTarefas = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "tarefas" }, allowSetters = true)
    private Esfera esfera;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "tarefas" }, allowSetters = true)
    private Frequencia frequencia;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "tarefas" }, allowSetters = true)
    private Competencia competencia;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Tarefa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public Tarefa titulo(String titulo) {
        this.setTitulo(titulo);
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getNumeroDias() {
        return this.numeroDias;
    }

    public Tarefa numeroDias(Integer numeroDias) {
        this.setNumeroDias(numeroDias);
        return this;
    }

    public void setNumeroDias(Integer numeroDias) {
        this.numeroDias = numeroDias;
    }

    public Boolean getDiaUtil() {
        return this.diaUtil;
    }

    public Tarefa diaUtil(Boolean diaUtil) {
        this.setDiaUtil(diaUtil);
        return this;
    }

    public void setDiaUtil(Boolean diaUtil) {
        this.diaUtil = diaUtil;
    }

    public Double getValor() {
        return this.valor;
    }

    public Tarefa valor(Double valor) {
        this.setValor(valor);
        return this;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Boolean getNotificarCliente() {
        return this.notificarCliente;
    }

    public Tarefa notificarCliente(Boolean notificarCliente) {
        this.setNotificarCliente(notificarCliente);
        return this;
    }

    public void setNotificarCliente(Boolean notificarCliente) {
        this.notificarCliente = notificarCliente;
    }

    public Boolean getGeraMulta() {
        return this.geraMulta;
    }

    public Tarefa geraMulta(Boolean geraMulta) {
        this.setGeraMulta(geraMulta);
        return this;
    }

    public void setGeraMulta(Boolean geraMulta) {
        this.geraMulta = geraMulta;
    }

    public Boolean getExibirEmpresa() {
        return this.exibirEmpresa;
    }

    public Tarefa exibirEmpresa(Boolean exibirEmpresa) {
        this.setExibirEmpresa(exibirEmpresa);
        return this;
    }

    public void setExibirEmpresa(Boolean exibirEmpresa) {
        this.exibirEmpresa = exibirEmpresa;
    }

    public Instant getDataLegal() {
        return this.dataLegal;
    }

    public Tarefa dataLegal(Instant dataLegal) {
        this.setDataLegal(dataLegal);
        return this;
    }

    public void setDataLegal(Instant dataLegal) {
        this.dataLegal = dataLegal;
    }

    public Integer getPontos() {
        return this.pontos;
    }

    public Tarefa pontos(Integer pontos) {
        this.setPontos(pontos);
        return this;
    }

    public void setPontos(Integer pontos) {
        this.pontos = pontos;
    }

    public TipoTarefaEnum getTipoTarefa() {
        return this.tipoTarefa;
    }

    public Tarefa tipoTarefa(TipoTarefaEnum tipoTarefa) {
        this.setTipoTarefa(tipoTarefa);
        return this;
    }

    public void setTipoTarefa(TipoTarefaEnum tipoTarefa) {
        this.tipoTarefa = tipoTarefa;
    }

    public Set<TarefaEmpresa> getTarefaEmpresas() {
        return this.tarefaEmpresas;
    }

    public void setTarefaEmpresas(Set<TarefaEmpresa> tarefaEmpresas) {
        if (this.tarefaEmpresas != null) {
            this.tarefaEmpresas.forEach(i -> i.setTarefa(null));
        }
        if (tarefaEmpresas != null) {
            tarefaEmpresas.forEach(i -> i.setTarefa(this));
        }
        this.tarefaEmpresas = tarefaEmpresas;
    }

    public Tarefa tarefaEmpresas(Set<TarefaEmpresa> tarefaEmpresas) {
        this.setTarefaEmpresas(tarefaEmpresas);
        return this;
    }

    public Tarefa addTarefaEmpresa(TarefaEmpresa tarefaEmpresa) {
        this.tarefaEmpresas.add(tarefaEmpresa);
        tarefaEmpresa.setTarefa(this);
        return this;
    }

    public Tarefa removeTarefaEmpresa(TarefaEmpresa tarefaEmpresa) {
        this.tarefaEmpresas.remove(tarefaEmpresa);
        tarefaEmpresa.setTarefa(null);
        return this;
    }

    public Set<Subtarefa> getSubtarefas() {
        return this.subtarefas;
    }

    public void setSubtarefas(Set<Subtarefa> subtarefas) {
        if (this.subtarefas != null) {
            this.subtarefas.forEach(i -> i.setTarefa(null));
        }
        if (subtarefas != null) {
            subtarefas.forEach(i -> i.setTarefa(this));
        }
        this.subtarefas = subtarefas;
    }

    public Tarefa subtarefas(Set<Subtarefa> subtarefas) {
        this.setSubtarefas(subtarefas);
        return this;
    }

    public Tarefa addSubtarefa(Subtarefa subtarefa) {
        this.subtarefas.add(subtarefa);
        subtarefa.setTarefa(this);
        return this;
    }

    public Tarefa removeSubtarefa(Subtarefa subtarefa) {
        this.subtarefas.remove(subtarefa);
        subtarefa.setTarefa(null);
        return this;
    }

    public Set<DocumentoTarefa> getDocumentoTarefas() {
        return this.documentoTarefas;
    }

    public void setDocumentoTarefas(Set<DocumentoTarefa> documentoTarefas) {
        if (this.documentoTarefas != null) {
            this.documentoTarefas.forEach(i -> i.setTarefa(null));
        }
        if (documentoTarefas != null) {
            documentoTarefas.forEach(i -> i.setTarefa(this));
        }
        this.documentoTarefas = documentoTarefas;
    }

    public Tarefa documentoTarefas(Set<DocumentoTarefa> documentoTarefas) {
        this.setDocumentoTarefas(documentoTarefas);
        return this;
    }

    public Tarefa addDocumentoTarefa(DocumentoTarefa documentoTarefa) {
        this.documentoTarefas.add(documentoTarefa);
        documentoTarefa.setTarefa(this);
        return this;
    }

    public Tarefa removeDocumentoTarefa(DocumentoTarefa documentoTarefa) {
        this.documentoTarefas.remove(documentoTarefa);
        documentoTarefa.setTarefa(null);
        return this;
    }

    public Esfera getEsfera() {
        return this.esfera;
    }

    public void setEsfera(Esfera esfera) {
        this.esfera = esfera;
    }

    public Tarefa esfera(Esfera esfera) {
        this.setEsfera(esfera);
        return this;
    }

    public Frequencia getFrequencia() {
        return this.frequencia;
    }

    public void setFrequencia(Frequencia frequencia) {
        this.frequencia = frequencia;
    }

    public Tarefa frequencia(Frequencia frequencia) {
        this.setFrequencia(frequencia);
        return this;
    }

    public Competencia getCompetencia() {
        return this.competencia;
    }

    public void setCompetencia(Competencia competencia) {
        this.competencia = competencia;
    }

    public Tarefa competencia(Competencia competencia) {
        this.setCompetencia(competencia);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tarefa)) {
            return false;
        }
        return getId() != null && getId().equals(((Tarefa) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tarefa{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", numeroDias=" + getNumeroDias() +
            ", diaUtil='" + getDiaUtil() + "'" +
            ", valor=" + getValor() +
            ", notificarCliente='" + getNotificarCliente() + "'" +
            ", geraMulta='" + getGeraMulta() + "'" +
            ", exibirEmpresa='" + getExibirEmpresa() + "'" +
            ", dataLegal='" + getDataLegal() + "'" +
            ", pontos=" + getPontos() +
            ", tipoTarefa='" + getTipoTarefa() + "'" +
            "}";
    }
}
