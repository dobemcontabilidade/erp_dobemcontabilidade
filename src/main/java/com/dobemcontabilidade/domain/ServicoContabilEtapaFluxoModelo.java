package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ServicoContabilEtapaFluxoModelo.
 */
@Entity
@Table(name = "serv_cont_etapa_fluxo_modelo")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServicoContabilEtapaFluxoModelo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "ordem")
    private Integer ordem;

    @Column(name = "prazo")
    private Integer prazo;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "servicoContabilEtapaFluxoModelos", "fluxoModelo" }, allowSetters = true)
    private EtapaFluxoModelo etapaFluxoModelo;

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

    public ServicoContabilEtapaFluxoModelo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrdem() {
        return this.ordem;
    }

    public ServicoContabilEtapaFluxoModelo ordem(Integer ordem) {
        this.setOrdem(ordem);
        return this;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public Integer getPrazo() {
        return this.prazo;
    }

    public ServicoContabilEtapaFluxoModelo prazo(Integer prazo) {
        this.setPrazo(prazo);
        return this;
    }

    public void setPrazo(Integer prazo) {
        this.prazo = prazo;
    }

    public EtapaFluxoModelo getEtapaFluxoModelo() {
        return this.etapaFluxoModelo;
    }

    public void setEtapaFluxoModelo(EtapaFluxoModelo etapaFluxoModelo) {
        this.etapaFluxoModelo = etapaFluxoModelo;
    }

    public ServicoContabilEtapaFluxoModelo etapaFluxoModelo(EtapaFluxoModelo etapaFluxoModelo) {
        this.setEtapaFluxoModelo(etapaFluxoModelo);
        return this;
    }

    public ServicoContabil getServicoContabil() {
        return this.servicoContabil;
    }

    public void setServicoContabil(ServicoContabil servicoContabil) {
        this.servicoContabil = servicoContabil;
    }

    public ServicoContabilEtapaFluxoModelo servicoContabil(ServicoContabil servicoContabil) {
        this.setServicoContabil(servicoContabil);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServicoContabilEtapaFluxoModelo)) {
            return false;
        }
        return getId() != null && getId().equals(((ServicoContabilEtapaFluxoModelo) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServicoContabilEtapaFluxoModelo{" +
            "id=" + getId() +
            ", ordem=" + getOrdem() +
            ", prazo=" + getPrazo() +
            "}";
    }
}
