package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TarefaEmpresaModelo.
 */
@Entity
@Table(name = "tarefa_empresa_modelo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TarefaEmpresaModelo implements Serializable {

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

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "servicoContabilEmpresaModelos",
            "impostoEmpresaModelos",
            "anexoRequeridoEmpresas",
            "tarefaEmpresaModelos",
            "empresas",
            "segmentoCnaes",
            "ramo",
            "enquadramento",
            "tributacao",
            "cidade",
        },
        allowSetters = true
    )
    private EmpresaModelo empresaModelo;

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

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TarefaEmpresaModelo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataAdmin() {
        return this.dataAdmin;
    }

    public TarefaEmpresaModelo dataAdmin(Instant dataAdmin) {
        this.setDataAdmin(dataAdmin);
        return this;
    }

    public void setDataAdmin(Instant dataAdmin) {
        this.dataAdmin = dataAdmin;
    }

    public Instant getDataLegal() {
        return this.dataLegal;
    }

    public TarefaEmpresaModelo dataLegal(Instant dataLegal) {
        this.setDataLegal(dataLegal);
        return this;
    }

    public void setDataLegal(Instant dataLegal) {
        this.dataLegal = dataLegal;
    }

    public EmpresaModelo getEmpresaModelo() {
        return this.empresaModelo;
    }

    public void setEmpresaModelo(EmpresaModelo empresaModelo) {
        this.empresaModelo = empresaModelo;
    }

    public TarefaEmpresaModelo empresaModelo(EmpresaModelo empresaModelo) {
        this.setEmpresaModelo(empresaModelo);
        return this;
    }

    public ServicoContabil getServicoContabil() {
        return this.servicoContabil;
    }

    public void setServicoContabil(ServicoContabil servicoContabil) {
        this.servicoContabil = servicoContabil;
    }

    public TarefaEmpresaModelo servicoContabil(ServicoContabil servicoContabil) {
        this.setServicoContabil(servicoContabil);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TarefaEmpresaModelo)) {
            return false;
        }
        return getId() != null && getId().equals(((TarefaEmpresaModelo) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TarefaEmpresaModelo{" +
            "id=" + getId() +
            ", dataAdmin='" + getDataAdmin() + "'" +
            ", dataLegal='" + getDataLegal() + "'" +
            "}";
    }
}
