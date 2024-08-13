package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AnexoRequeridoServicoContabil.
 */
@Entity
@Table(name = "anexo_req_serv_cont")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AnexoRequeridoServicoContabil implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "obrigatorio")
    private Boolean obrigatorio;

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
            "anexoRequeridoPessoas",
            "anexoRequeridoEmpresas",
            "anexoRequeridoServicoContabils",
            "anexoServicoContabilEmpresas",
            "anexoRequeridoTarefaOrdemServicos",
            "anexoRequeridoTarefaRecorrentes",
        },
        allowSetters = true
    )
    private AnexoRequerido anexoRequerido;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AnexoRequeridoServicoContabil id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getObrigatorio() {
        return this.obrigatorio;
    }

    public AnexoRequeridoServicoContabil obrigatorio(Boolean obrigatorio) {
        this.setObrigatorio(obrigatorio);
        return this;
    }

    public void setObrigatorio(Boolean obrigatorio) {
        this.obrigatorio = obrigatorio;
    }

    public ServicoContabil getServicoContabil() {
        return this.servicoContabil;
    }

    public void setServicoContabil(ServicoContabil servicoContabil) {
        this.servicoContabil = servicoContabil;
    }

    public AnexoRequeridoServicoContabil servicoContabil(ServicoContabil servicoContabil) {
        this.setServicoContabil(servicoContabil);
        return this;
    }

    public AnexoRequerido getAnexoRequerido() {
        return this.anexoRequerido;
    }

    public void setAnexoRequerido(AnexoRequerido anexoRequerido) {
        this.anexoRequerido = anexoRequerido;
    }

    public AnexoRequeridoServicoContabil anexoRequerido(AnexoRequerido anexoRequerido) {
        this.setAnexoRequerido(anexoRequerido);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnexoRequeridoServicoContabil)) {
            return false;
        }
        return getId() != null && getId().equals(((AnexoRequeridoServicoContabil) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnexoRequeridoServicoContabil{" +
            "id=" + getId() +
            ", obrigatorio='" + getObrigatorio() + "'" +
            "}";
    }
}
