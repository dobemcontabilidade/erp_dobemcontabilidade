package com.dobemcontabilidade.domain;

import com.dobemcontabilidade.domain.enumeration.TipoRecorrenciaEnum;
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
 * A TarefaRecorrente.
 */
@Entity
@Table(name = "tarefa_recorrente")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TarefaRecorrente implements Serializable {

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

    @Column(name = "notificar_cliente")
    private Boolean notificarCliente;

    @Column(name = "notificar_contador")
    private Boolean notificarContador;

    @Column(name = "ano_referencia")
    private Integer anoReferencia;

    @Column(name = "data_admin")
    private Instant dataAdmin;

    @Enumerated(EnumType.STRING)
    @Column(name = "recorencia")
    private TipoRecorrenciaEnum recorencia;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tarefaRecorrente")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<TarefaRecorrenteExecucao> tarefaRecorrenteExecucaos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tarefaRecorrente")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "anexoRequerido", "tarefaRecorrente" }, allowSetters = true)
    private Set<AnexoRequeridoTarefaRecorrente> anexoRequeridoTarefaRecorrentes = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "anexoServicoContabilEmpresas", "tarefaRecorrentes", "servicoContabil", "assinaturaEmpresa" },
        allowSetters = true
    )
    private ServicoContabilAssinaturaEmpresa servicoContabilAssinaturaEmpresa;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TarefaRecorrente id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public TarefaRecorrente nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public TarefaRecorrente descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getNotificarCliente() {
        return this.notificarCliente;
    }

    public TarefaRecorrente notificarCliente(Boolean notificarCliente) {
        this.setNotificarCliente(notificarCliente);
        return this;
    }

    public void setNotificarCliente(Boolean notificarCliente) {
        this.notificarCliente = notificarCliente;
    }

    public Boolean getNotificarContador() {
        return this.notificarContador;
    }

    public TarefaRecorrente notificarContador(Boolean notificarContador) {
        this.setNotificarContador(notificarContador);
        return this;
    }

    public void setNotificarContador(Boolean notificarContador) {
        this.notificarContador = notificarContador;
    }

    public Integer getAnoReferencia() {
        return this.anoReferencia;
    }

    public TarefaRecorrente anoReferencia(Integer anoReferencia) {
        this.setAnoReferencia(anoReferencia);
        return this;
    }

    public void setAnoReferencia(Integer anoReferencia) {
        this.anoReferencia = anoReferencia;
    }

    public Instant getDataAdmin() {
        return this.dataAdmin;
    }

    public TarefaRecorrente dataAdmin(Instant dataAdmin) {
        this.setDataAdmin(dataAdmin);
        return this;
    }

    public void setDataAdmin(Instant dataAdmin) {
        this.dataAdmin = dataAdmin;
    }

    public TipoRecorrenciaEnum getRecorencia() {
        return this.recorencia;
    }

    public TarefaRecorrente recorencia(TipoRecorrenciaEnum recorencia) {
        this.setRecorencia(recorencia);
        return this;
    }

    public void setRecorencia(TipoRecorrenciaEnum recorencia) {
        this.recorencia = recorencia;
    }

    public Set<TarefaRecorrenteExecucao> getTarefaRecorrenteExecucaos() {
        return this.tarefaRecorrenteExecucaos;
    }

    public void setTarefaRecorrenteExecucaos(Set<TarefaRecorrenteExecucao> tarefaRecorrenteExecucaos) {
        if (this.tarefaRecorrenteExecucaos != null) {
            this.tarefaRecorrenteExecucaos.forEach(i -> i.setTarefaRecorrente(null));
        }
        if (tarefaRecorrenteExecucaos != null) {
            tarefaRecorrenteExecucaos.forEach(i -> i.setTarefaRecorrente(this));
        }
        this.tarefaRecorrenteExecucaos = tarefaRecorrenteExecucaos;
    }

    public TarefaRecorrente tarefaRecorrenteExecucaos(Set<TarefaRecorrenteExecucao> tarefaRecorrenteExecucaos) {
        this.setTarefaRecorrenteExecucaos(tarefaRecorrenteExecucaos);
        return this;
    }

    public TarefaRecorrente addTarefaRecorrenteExecucao(TarefaRecorrenteExecucao tarefaRecorrenteExecucao) {
        this.tarefaRecorrenteExecucaos.add(tarefaRecorrenteExecucao);
        tarefaRecorrenteExecucao.setTarefaRecorrente(this);
        return this;
    }

    public TarefaRecorrente removeTarefaRecorrenteExecucao(TarefaRecorrenteExecucao tarefaRecorrenteExecucao) {
        this.tarefaRecorrenteExecucaos.remove(tarefaRecorrenteExecucao);
        tarefaRecorrenteExecucao.setTarefaRecorrente(null);
        return this;
    }

    public Set<AnexoRequeridoTarefaRecorrente> getAnexoRequeridoTarefaRecorrentes() {
        return this.anexoRequeridoTarefaRecorrentes;
    }

    public void setAnexoRequeridoTarefaRecorrentes(Set<AnexoRequeridoTarefaRecorrente> anexoRequeridoTarefaRecorrentes) {
        if (this.anexoRequeridoTarefaRecorrentes != null) {
            this.anexoRequeridoTarefaRecorrentes.forEach(i -> i.setTarefaRecorrente(null));
        }
        if (anexoRequeridoTarefaRecorrentes != null) {
            anexoRequeridoTarefaRecorrentes.forEach(i -> i.setTarefaRecorrente(this));
        }
        this.anexoRequeridoTarefaRecorrentes = anexoRequeridoTarefaRecorrentes;
    }

    public TarefaRecorrente anexoRequeridoTarefaRecorrentes(Set<AnexoRequeridoTarefaRecorrente> anexoRequeridoTarefaRecorrentes) {
        this.setAnexoRequeridoTarefaRecorrentes(anexoRequeridoTarefaRecorrentes);
        return this;
    }

    public TarefaRecorrente addAnexoRequeridoTarefaRecorrente(AnexoRequeridoTarefaRecorrente anexoRequeridoTarefaRecorrente) {
        this.anexoRequeridoTarefaRecorrentes.add(anexoRequeridoTarefaRecorrente);
        anexoRequeridoTarefaRecorrente.setTarefaRecorrente(this);
        return this;
    }

    public TarefaRecorrente removeAnexoRequeridoTarefaRecorrente(AnexoRequeridoTarefaRecorrente anexoRequeridoTarefaRecorrente) {
        this.anexoRequeridoTarefaRecorrentes.remove(anexoRequeridoTarefaRecorrente);
        anexoRequeridoTarefaRecorrente.setTarefaRecorrente(null);
        return this;
    }

    public ServicoContabilAssinaturaEmpresa getServicoContabilAssinaturaEmpresa() {
        return this.servicoContabilAssinaturaEmpresa;
    }

    public void setServicoContabilAssinaturaEmpresa(ServicoContabilAssinaturaEmpresa servicoContabilAssinaturaEmpresa) {
        this.servicoContabilAssinaturaEmpresa = servicoContabilAssinaturaEmpresa;
    }

    public TarefaRecorrente servicoContabilAssinaturaEmpresa(ServicoContabilAssinaturaEmpresa servicoContabilAssinaturaEmpresa) {
        this.setServicoContabilAssinaturaEmpresa(servicoContabilAssinaturaEmpresa);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TarefaRecorrente)) {
            return false;
        }
        return getId() != null && getId().equals(((TarefaRecorrente) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TarefaRecorrente{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", notificarCliente='" + getNotificarCliente() + "'" +
            ", notificarContador='" + getNotificarContador() + "'" +
            ", anoReferencia=" + getAnoReferencia() +
            ", dataAdmin='" + getDataAdmin() + "'" +
            ", recorencia='" + getRecorencia() + "'" +
            "}";
    }
}
