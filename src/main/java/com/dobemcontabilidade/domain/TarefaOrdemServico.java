package com.dobemcontabilidade.domain;

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
 * A TarefaOrdemServico.
 */
@Entity
@Table(name = "tarefa_ordem_servico")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TarefaOrdemServico implements Serializable {

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tarefaOrdemServico")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "anexoRequerido", "tarefaOrdemServico" }, allowSetters = true)
    private Set<AnexoRequeridoTarefaOrdemServico> anexoRequeridoTarefaOrdemServicos = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tarefaOrdemServico")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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
    private Set<TarefaOrdemServicoExecucao> tarefaOrdemServicoExecucaos = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "tarefaOrdemServicos", "servicoContabil", "ordemServico" }, allowSetters = true)
    private ServicoContabilOrdemServico servicoContabilOrdemServico;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TarefaOrdemServico id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public TarefaOrdemServico nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public TarefaOrdemServico descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getNotificarCliente() {
        return this.notificarCliente;
    }

    public TarefaOrdemServico notificarCliente(Boolean notificarCliente) {
        this.setNotificarCliente(notificarCliente);
        return this;
    }

    public void setNotificarCliente(Boolean notificarCliente) {
        this.notificarCliente = notificarCliente;
    }

    public Boolean getNotificarContador() {
        return this.notificarContador;
    }

    public TarefaOrdemServico notificarContador(Boolean notificarContador) {
        this.setNotificarContador(notificarContador);
        return this;
    }

    public void setNotificarContador(Boolean notificarContador) {
        this.notificarContador = notificarContador;
    }

    public Integer getAnoReferencia() {
        return this.anoReferencia;
    }

    public TarefaOrdemServico anoReferencia(Integer anoReferencia) {
        this.setAnoReferencia(anoReferencia);
        return this;
    }

    public void setAnoReferencia(Integer anoReferencia) {
        this.anoReferencia = anoReferencia;
    }

    public Instant getDataAdmin() {
        return this.dataAdmin;
    }

    public TarefaOrdemServico dataAdmin(Instant dataAdmin) {
        this.setDataAdmin(dataAdmin);
        return this;
    }

    public void setDataAdmin(Instant dataAdmin) {
        this.dataAdmin = dataAdmin;
    }

    public Set<AnexoRequeridoTarefaOrdemServico> getAnexoRequeridoTarefaOrdemServicos() {
        return this.anexoRequeridoTarefaOrdemServicos;
    }

    public void setAnexoRequeridoTarefaOrdemServicos(Set<AnexoRequeridoTarefaOrdemServico> anexoRequeridoTarefaOrdemServicos) {
        if (this.anexoRequeridoTarefaOrdemServicos != null) {
            this.anexoRequeridoTarefaOrdemServicos.forEach(i -> i.setTarefaOrdemServico(null));
        }
        if (anexoRequeridoTarefaOrdemServicos != null) {
            anexoRequeridoTarefaOrdemServicos.forEach(i -> i.setTarefaOrdemServico(this));
        }
        this.anexoRequeridoTarefaOrdemServicos = anexoRequeridoTarefaOrdemServicos;
    }

    public TarefaOrdemServico anexoRequeridoTarefaOrdemServicos(Set<AnexoRequeridoTarefaOrdemServico> anexoRequeridoTarefaOrdemServicos) {
        this.setAnexoRequeridoTarefaOrdemServicos(anexoRequeridoTarefaOrdemServicos);
        return this;
    }

    public TarefaOrdemServico addAnexoRequeridoTarefaOrdemServico(AnexoRequeridoTarefaOrdemServico anexoRequeridoTarefaOrdemServico) {
        this.anexoRequeridoTarefaOrdemServicos.add(anexoRequeridoTarefaOrdemServico);
        anexoRequeridoTarefaOrdemServico.setTarefaOrdemServico(this);
        return this;
    }

    public TarefaOrdemServico removeAnexoRequeridoTarefaOrdemServico(AnexoRequeridoTarefaOrdemServico anexoRequeridoTarefaOrdemServico) {
        this.anexoRequeridoTarefaOrdemServicos.remove(anexoRequeridoTarefaOrdemServico);
        anexoRequeridoTarefaOrdemServico.setTarefaOrdemServico(null);
        return this;
    }

    public Set<TarefaOrdemServicoExecucao> getTarefaOrdemServicoExecucaos() {
        return this.tarefaOrdemServicoExecucaos;
    }

    public void setTarefaOrdemServicoExecucaos(Set<TarefaOrdemServicoExecucao> tarefaOrdemServicoExecucaos) {
        if (this.tarefaOrdemServicoExecucaos != null) {
            this.tarefaOrdemServicoExecucaos.forEach(i -> i.setTarefaOrdemServico(null));
        }
        if (tarefaOrdemServicoExecucaos != null) {
            tarefaOrdemServicoExecucaos.forEach(i -> i.setTarefaOrdemServico(this));
        }
        this.tarefaOrdemServicoExecucaos = tarefaOrdemServicoExecucaos;
    }

    public TarefaOrdemServico tarefaOrdemServicoExecucaos(Set<TarefaOrdemServicoExecucao> tarefaOrdemServicoExecucaos) {
        this.setTarefaOrdemServicoExecucaos(tarefaOrdemServicoExecucaos);
        return this;
    }

    public TarefaOrdemServico addTarefaOrdemServicoExecucao(TarefaOrdemServicoExecucao tarefaOrdemServicoExecucao) {
        this.tarefaOrdemServicoExecucaos.add(tarefaOrdemServicoExecucao);
        tarefaOrdemServicoExecucao.setTarefaOrdemServico(this);
        return this;
    }

    public TarefaOrdemServico removeTarefaOrdemServicoExecucao(TarefaOrdemServicoExecucao tarefaOrdemServicoExecucao) {
        this.tarefaOrdemServicoExecucaos.remove(tarefaOrdemServicoExecucao);
        tarefaOrdemServicoExecucao.setTarefaOrdemServico(null);
        return this;
    }

    public ServicoContabilOrdemServico getServicoContabilOrdemServico() {
        return this.servicoContabilOrdemServico;
    }

    public void setServicoContabilOrdemServico(ServicoContabilOrdemServico servicoContabilOrdemServico) {
        this.servicoContabilOrdemServico = servicoContabilOrdemServico;
    }

    public TarefaOrdemServico servicoContabilOrdemServico(ServicoContabilOrdemServico servicoContabilOrdemServico) {
        this.setServicoContabilOrdemServico(servicoContabilOrdemServico);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TarefaOrdemServico)) {
            return false;
        }
        return getId() != null && getId().equals(((TarefaOrdemServico) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TarefaOrdemServico{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", notificarCliente='" + getNotificarCliente() + "'" +
            ", notificarContador='" + getNotificarContador() + "'" +
            ", anoReferencia=" + getAnoReferencia() +
            ", dataAdmin='" + getDataAdmin() + "'" +
            "}";
    }
}
