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
 * A ServicoContabilEmpresaModelo.
 */
@Entity
@Table(name = "serv_cont_empresa_modelo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServicoContabilEmpresaModelo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "obrigatorio")
    private Boolean obrigatorio;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "servicoContabilEmpresaModelo")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "servicoContabilEmpresaModelo" }, allowSetters = true)
    private Set<TarefaRecorrenteEmpresaModelo> tarefaRecorrenteEmpresaModelos = new HashSet<>();

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

    public ServicoContabilEmpresaModelo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getObrigatorio() {
        return this.obrigatorio;
    }

    public ServicoContabilEmpresaModelo obrigatorio(Boolean obrigatorio) {
        this.setObrigatorio(obrigatorio);
        return this;
    }

    public void setObrigatorio(Boolean obrigatorio) {
        this.obrigatorio = obrigatorio;
    }

    public Set<TarefaRecorrenteEmpresaModelo> getTarefaRecorrenteEmpresaModelos() {
        return this.tarefaRecorrenteEmpresaModelos;
    }

    public void setTarefaRecorrenteEmpresaModelos(Set<TarefaRecorrenteEmpresaModelo> tarefaRecorrenteEmpresaModelos) {
        if (this.tarefaRecorrenteEmpresaModelos != null) {
            this.tarefaRecorrenteEmpresaModelos.forEach(i -> i.setServicoContabilEmpresaModelo(null));
        }
        if (tarefaRecorrenteEmpresaModelos != null) {
            tarefaRecorrenteEmpresaModelos.forEach(i -> i.setServicoContabilEmpresaModelo(this));
        }
        this.tarefaRecorrenteEmpresaModelos = tarefaRecorrenteEmpresaModelos;
    }

    public ServicoContabilEmpresaModelo tarefaRecorrenteEmpresaModelos(Set<TarefaRecorrenteEmpresaModelo> tarefaRecorrenteEmpresaModelos) {
        this.setTarefaRecorrenteEmpresaModelos(tarefaRecorrenteEmpresaModelos);
        return this;
    }

    public ServicoContabilEmpresaModelo addTarefaRecorrenteEmpresaModelo(TarefaRecorrenteEmpresaModelo tarefaRecorrenteEmpresaModelo) {
        this.tarefaRecorrenteEmpresaModelos.add(tarefaRecorrenteEmpresaModelo);
        tarefaRecorrenteEmpresaModelo.setServicoContabilEmpresaModelo(this);
        return this;
    }

    public ServicoContabilEmpresaModelo removeTarefaRecorrenteEmpresaModelo(TarefaRecorrenteEmpresaModelo tarefaRecorrenteEmpresaModelo) {
        this.tarefaRecorrenteEmpresaModelos.remove(tarefaRecorrenteEmpresaModelo);
        tarefaRecorrenteEmpresaModelo.setServicoContabilEmpresaModelo(null);
        return this;
    }

    public EmpresaModelo getEmpresaModelo() {
        return this.empresaModelo;
    }

    public void setEmpresaModelo(EmpresaModelo empresaModelo) {
        this.empresaModelo = empresaModelo;
    }

    public ServicoContabilEmpresaModelo empresaModelo(EmpresaModelo empresaModelo) {
        this.setEmpresaModelo(empresaModelo);
        return this;
    }

    public ServicoContabil getServicoContabil() {
        return this.servicoContabil;
    }

    public void setServicoContabil(ServicoContabil servicoContabil) {
        this.servicoContabil = servicoContabil;
    }

    public ServicoContabilEmpresaModelo servicoContabil(ServicoContabil servicoContabil) {
        this.setServicoContabil(servicoContabil);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServicoContabilEmpresaModelo)) {
            return false;
        }
        return getId() != null && getId().equals(((ServicoContabilEmpresaModelo) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServicoContabilEmpresaModelo{" +
            "id=" + getId() +
            ", obrigatorio='" + getObrigatorio() + "'" +
            "}";
    }
}
