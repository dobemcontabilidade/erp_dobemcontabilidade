package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ServicoContabilEtapaFluxoExecucao.
 */
@Entity
@Table(name = "serv_cont_etapa_fluxo_execucao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ServicoContabilEtapaFluxoExecucao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "ordem")
    private Integer ordem;

    @Column(name = "feito")
    private Boolean feito;

    @Column(name = "prazo")
    private Integer prazo;

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
    @JsonIgnoreProperties(value = { "servicoContabilEtapaFluxoExecucaos", "ordemServico" }, allowSetters = true)
    private EtapaFluxoExecucao etapaFluxoExecucao;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ServicoContabilEtapaFluxoExecucao id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrdem() {
        return this.ordem;
    }

    public ServicoContabilEtapaFluxoExecucao ordem(Integer ordem) {
        this.setOrdem(ordem);
        return this;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public Boolean getFeito() {
        return this.feito;
    }

    public ServicoContabilEtapaFluxoExecucao feito(Boolean feito) {
        this.setFeito(feito);
        return this;
    }

    public void setFeito(Boolean feito) {
        this.feito = feito;
    }

    public Integer getPrazo() {
        return this.prazo;
    }

    public ServicoContabilEtapaFluxoExecucao prazo(Integer prazo) {
        this.setPrazo(prazo);
        return this;
    }

    public void setPrazo(Integer prazo) {
        this.prazo = prazo;
    }

    public ServicoContabil getServicoContabil() {
        return this.servicoContabil;
    }

    public void setServicoContabil(ServicoContabil servicoContabil) {
        this.servicoContabil = servicoContabil;
    }

    public ServicoContabilEtapaFluxoExecucao servicoContabil(ServicoContabil servicoContabil) {
        this.setServicoContabil(servicoContabil);
        return this;
    }

    public EtapaFluxoExecucao getEtapaFluxoExecucao() {
        return this.etapaFluxoExecucao;
    }

    public void setEtapaFluxoExecucao(EtapaFluxoExecucao etapaFluxoExecucao) {
        this.etapaFluxoExecucao = etapaFluxoExecucao;
    }

    public ServicoContabilEtapaFluxoExecucao etapaFluxoExecucao(EtapaFluxoExecucao etapaFluxoExecucao) {
        this.setEtapaFluxoExecucao(etapaFluxoExecucao);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServicoContabilEtapaFluxoExecucao)) {
            return false;
        }
        return getId() != null && getId().equals(((ServicoContabilEtapaFluxoExecucao) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ServicoContabilEtapaFluxoExecucao{" +
            "id=" + getId() +
            ", ordem=" + getOrdem() +
            ", feito='" + getFeito() + "'" +
            ", prazo=" + getPrazo() +
            "}";
    }
}
