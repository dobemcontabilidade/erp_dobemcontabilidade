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
 * A ServicoContabilAssinaturaEmpresa.
 */
@Entity
@Table(name = "serv_cont_assinatura_empresa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServicoContabilAssinaturaEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "data_legal", nullable = false)
    private Instant dataLegal;

    @NotNull
    @Column(name = "data_admin", nullable = false)
    private Instant dataAdmin;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "servicoContabilAssinaturaEmpresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "anexoRequerido", "servicoContabilAssinaturaEmpresa" }, allowSetters = true)
    private Set<AnexoServicoContabilEmpresa> anexoServicoContabilEmpresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "servicoContabilAssinaturaEmpresa")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "tarefaRecorrenteExecucaos", "anexoRequeridoTarefaRecorrentes", "servicoContabilAssinaturaEmpresa" },
        allowSetters = true
    )
    private Set<TarefaRecorrente> tarefaRecorrentes = new HashSet<>();

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
            "grupoAcessoEmpresas",
            "areaContabilAssinaturaEmpresas",
            "servicoContabilAssinaturaEmpresas",
            "gatewayAssinaturaEmpresas",
            "calculoPlanoAssinaturas",
            "pagamentos",
            "cobrancaEmpresas",
            "usuarioEmpresas",
            "periodoPagamento",
            "formaDePagamento",
            "planoContaAzul",
            "planoContabil",
            "empresa",
        },
        allowSetters = true
    )
    private AssinaturaEmpresa assinaturaEmpresa;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ServicoContabilAssinaturaEmpresa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataLegal() {
        return this.dataLegal;
    }

    public ServicoContabilAssinaturaEmpresa dataLegal(Instant dataLegal) {
        this.setDataLegal(dataLegal);
        return this;
    }

    public void setDataLegal(Instant dataLegal) {
        this.dataLegal = dataLegal;
    }

    public Instant getDataAdmin() {
        return this.dataAdmin;
    }

    public ServicoContabilAssinaturaEmpresa dataAdmin(Instant dataAdmin) {
        this.setDataAdmin(dataAdmin);
        return this;
    }

    public void setDataAdmin(Instant dataAdmin) {
        this.dataAdmin = dataAdmin;
    }

    public Set<AnexoServicoContabilEmpresa> getAnexoServicoContabilEmpresas() {
        return this.anexoServicoContabilEmpresas;
    }

    public void setAnexoServicoContabilEmpresas(Set<AnexoServicoContabilEmpresa> anexoServicoContabilEmpresas) {
        if (this.anexoServicoContabilEmpresas != null) {
            this.anexoServicoContabilEmpresas.forEach(i -> i.setServicoContabilAssinaturaEmpresa(null));
        }
        if (anexoServicoContabilEmpresas != null) {
            anexoServicoContabilEmpresas.forEach(i -> i.setServicoContabilAssinaturaEmpresa(this));
        }
        this.anexoServicoContabilEmpresas = anexoServicoContabilEmpresas;
    }

    public ServicoContabilAssinaturaEmpresa anexoServicoContabilEmpresas(Set<AnexoServicoContabilEmpresa> anexoServicoContabilEmpresas) {
        this.setAnexoServicoContabilEmpresas(anexoServicoContabilEmpresas);
        return this;
    }

    public ServicoContabilAssinaturaEmpresa addAnexoServicoContabilEmpresa(AnexoServicoContabilEmpresa anexoServicoContabilEmpresa) {
        this.anexoServicoContabilEmpresas.add(anexoServicoContabilEmpresa);
        anexoServicoContabilEmpresa.setServicoContabilAssinaturaEmpresa(this);
        return this;
    }

    public ServicoContabilAssinaturaEmpresa removeAnexoServicoContabilEmpresa(AnexoServicoContabilEmpresa anexoServicoContabilEmpresa) {
        this.anexoServicoContabilEmpresas.remove(anexoServicoContabilEmpresa);
        anexoServicoContabilEmpresa.setServicoContabilAssinaturaEmpresa(null);
        return this;
    }

    public Set<TarefaRecorrente> getTarefaRecorrentes() {
        return this.tarefaRecorrentes;
    }

    public void setTarefaRecorrentes(Set<TarefaRecorrente> tarefaRecorrentes) {
        if (this.tarefaRecorrentes != null) {
            this.tarefaRecorrentes.forEach(i -> i.setServicoContabilAssinaturaEmpresa(null));
        }
        if (tarefaRecorrentes != null) {
            tarefaRecorrentes.forEach(i -> i.setServicoContabilAssinaturaEmpresa(this));
        }
        this.tarefaRecorrentes = tarefaRecorrentes;
    }

    public ServicoContabilAssinaturaEmpresa tarefaRecorrentes(Set<TarefaRecorrente> tarefaRecorrentes) {
        this.setTarefaRecorrentes(tarefaRecorrentes);
        return this;
    }

    public ServicoContabilAssinaturaEmpresa addTarefaRecorrente(TarefaRecorrente tarefaRecorrente) {
        this.tarefaRecorrentes.add(tarefaRecorrente);
        tarefaRecorrente.setServicoContabilAssinaturaEmpresa(this);
        return this;
    }

    public ServicoContabilAssinaturaEmpresa removeTarefaRecorrente(TarefaRecorrente tarefaRecorrente) {
        this.tarefaRecorrentes.remove(tarefaRecorrente);
        tarefaRecorrente.setServicoContabilAssinaturaEmpresa(null);
        return this;
    }

    public ServicoContabil getServicoContabil() {
        return this.servicoContabil;
    }

    public void setServicoContabil(ServicoContabil servicoContabil) {
        this.servicoContabil = servicoContabil;
    }

    public ServicoContabilAssinaturaEmpresa servicoContabil(ServicoContabil servicoContabil) {
        this.setServicoContabil(servicoContabil);
        return this;
    }

    public AssinaturaEmpresa getAssinaturaEmpresa() {
        return this.assinaturaEmpresa;
    }

    public void setAssinaturaEmpresa(AssinaturaEmpresa assinaturaEmpresa) {
        this.assinaturaEmpresa = assinaturaEmpresa;
    }

    public ServicoContabilAssinaturaEmpresa assinaturaEmpresa(AssinaturaEmpresa assinaturaEmpresa) {
        this.setAssinaturaEmpresa(assinaturaEmpresa);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServicoContabilAssinaturaEmpresa)) {
            return false;
        }
        return getId() != null && getId().equals(((ServicoContabilAssinaturaEmpresa) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServicoContabilAssinaturaEmpresa{" +
            "id=" + getId() +
            ", dataLegal='" + getDataLegal() + "'" +
            ", dataAdmin='" + getDataAdmin() + "'" +
            "}";
    }
}
