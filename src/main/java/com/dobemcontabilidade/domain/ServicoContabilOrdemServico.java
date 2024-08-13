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
 * A ServicoContabilOrdemServico.
 */
@Entity
@Table(name = "servico_contabil_ordem_servico")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServicoContabilOrdemServico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "data_admin")
    private Instant dataAdmin;

    @Column(name = "data_legal")
    private Instant dataLegal;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "servicoContabilOrdemServico")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "anexoRequeridoTarefaOrdemServicos", "tarefaOrdemServicoExecucaos", "servicoContabilOrdemServico" },
        allowSetters = true
    )
    private Set<TarefaOrdemServico> tarefaOrdemServicos = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
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
    private ServicoContabil servicoContabil;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "feedBackUsuarioParaContadors",
            "feedBackContadorParaUsuarios",
            "etapaFluxoExecucaos",
            "servicoContabilOrdemServicos",
            "empresa",
            "contador",
            "fluxoModelo",
        },
        allowSetters = true
    )
    private OrdemServico ordemServico;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ServicoContabilOrdemServico id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataAdmin() {
        return this.dataAdmin;
    }

    public ServicoContabilOrdemServico dataAdmin(Instant dataAdmin) {
        this.setDataAdmin(dataAdmin);
        return this;
    }

    public void setDataAdmin(Instant dataAdmin) {
        this.dataAdmin = dataAdmin;
    }

    public Instant getDataLegal() {
        return this.dataLegal;
    }

    public ServicoContabilOrdemServico dataLegal(Instant dataLegal) {
        this.setDataLegal(dataLegal);
        return this;
    }

    public void setDataLegal(Instant dataLegal) {
        this.dataLegal = dataLegal;
    }

    public Set<TarefaOrdemServico> getTarefaOrdemServicos() {
        return this.tarefaOrdemServicos;
    }

    public void setTarefaOrdemServicos(Set<TarefaOrdemServico> tarefaOrdemServicos) {
        if (this.tarefaOrdemServicos != null) {
            this.tarefaOrdemServicos.forEach(i -> i.setServicoContabilOrdemServico(null));
        }
        if (tarefaOrdemServicos != null) {
            tarefaOrdemServicos.forEach(i -> i.setServicoContabilOrdemServico(this));
        }
        this.tarefaOrdemServicos = tarefaOrdemServicos;
    }

    public ServicoContabilOrdemServico tarefaOrdemServicos(Set<TarefaOrdemServico> tarefaOrdemServicos) {
        this.setTarefaOrdemServicos(tarefaOrdemServicos);
        return this;
    }

    public ServicoContabilOrdemServico addTarefaOrdemServico(TarefaOrdemServico tarefaOrdemServico) {
        this.tarefaOrdemServicos.add(tarefaOrdemServico);
        tarefaOrdemServico.setServicoContabilOrdemServico(this);
        return this;
    }

    public ServicoContabilOrdemServico removeTarefaOrdemServico(TarefaOrdemServico tarefaOrdemServico) {
        this.tarefaOrdemServicos.remove(tarefaOrdemServico);
        tarefaOrdemServico.setServicoContabilOrdemServico(null);
        return this;
    }

    public ServicoContabil getServicoContabil() {
        return this.servicoContabil;
    }

    public void setServicoContabil(ServicoContabil servicoContabil) {
        this.servicoContabil = servicoContabil;
    }

    public ServicoContabilOrdemServico servicoContabil(ServicoContabil servicoContabil) {
        this.setServicoContabil(servicoContabil);
        return this;
    }

    public OrdemServico getOrdemServico() {
        return this.ordemServico;
    }

    public void setOrdemServico(OrdemServico ordemServico) {
        this.ordemServico = ordemServico;
    }

    public ServicoContabilOrdemServico ordemServico(OrdemServico ordemServico) {
        this.setOrdemServico(ordemServico);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServicoContabilOrdemServico)) {
            return false;
        }
        return getId() != null && getId().equals(((ServicoContabilOrdemServico) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServicoContabilOrdemServico{" +
            "id=" + getId() +
            ", dataAdmin='" + getDataAdmin() + "'" +
            ", dataLegal='" + getDataLegal() + "'" +
            "}";
    }
}
