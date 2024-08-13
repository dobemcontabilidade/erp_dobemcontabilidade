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
 * A EtapaFluxoExecucao.
 */
@Entity
@Table(name = "etapa_fluxo_execucao")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EtapaFluxoExecucao implements Serializable {

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

    @Column(name = "feito")
    private Boolean feito;

    @Column(name = "ordem")
    private Integer ordem;

    @Column(name = "agendada")
    private Boolean agendada;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "etapaFluxoExecucao")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "servicoContabil", "etapaFluxoExecucao" }, allowSetters = true)
    private Set<ServicoContabilEtapaFluxoExecucao> servicoContabilEtapaFluxoExecucaos = new HashSet<>();

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

    public EtapaFluxoExecucao id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public EtapaFluxoExecucao nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public EtapaFluxoExecucao descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getFeito() {
        return this.feito;
    }

    public EtapaFluxoExecucao feito(Boolean feito) {
        this.setFeito(feito);
        return this;
    }

    public void setFeito(Boolean feito) {
        this.feito = feito;
    }

    public Integer getOrdem() {
        return this.ordem;
    }

    public EtapaFluxoExecucao ordem(Integer ordem) {
        this.setOrdem(ordem);
        return this;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public Boolean getAgendada() {
        return this.agendada;
    }

    public EtapaFluxoExecucao agendada(Boolean agendada) {
        this.setAgendada(agendada);
        return this;
    }

    public void setAgendada(Boolean agendada) {
        this.agendada = agendada;
    }

    public Set<ServicoContabilEtapaFluxoExecucao> getServicoContabilEtapaFluxoExecucaos() {
        return this.servicoContabilEtapaFluxoExecucaos;
    }

    public void setServicoContabilEtapaFluxoExecucaos(Set<ServicoContabilEtapaFluxoExecucao> servicoContabilEtapaFluxoExecucaos) {
        if (this.servicoContabilEtapaFluxoExecucaos != null) {
            this.servicoContabilEtapaFluxoExecucaos.forEach(i -> i.setEtapaFluxoExecucao(null));
        }
        if (servicoContabilEtapaFluxoExecucaos != null) {
            servicoContabilEtapaFluxoExecucaos.forEach(i -> i.setEtapaFluxoExecucao(this));
        }
        this.servicoContabilEtapaFluxoExecucaos = servicoContabilEtapaFluxoExecucaos;
    }

    public EtapaFluxoExecucao servicoContabilEtapaFluxoExecucaos(
        Set<ServicoContabilEtapaFluxoExecucao> servicoContabilEtapaFluxoExecucaos
    ) {
        this.setServicoContabilEtapaFluxoExecucaos(servicoContabilEtapaFluxoExecucaos);
        return this;
    }

    public EtapaFluxoExecucao addServicoContabilEtapaFluxoExecucao(ServicoContabilEtapaFluxoExecucao servicoContabilEtapaFluxoExecucao) {
        this.servicoContabilEtapaFluxoExecucaos.add(servicoContabilEtapaFluxoExecucao);
        servicoContabilEtapaFluxoExecucao.setEtapaFluxoExecucao(this);
        return this;
    }

    public EtapaFluxoExecucao removeServicoContabilEtapaFluxoExecucao(ServicoContabilEtapaFluxoExecucao servicoContabilEtapaFluxoExecucao) {
        this.servicoContabilEtapaFluxoExecucaos.remove(servicoContabilEtapaFluxoExecucao);
        servicoContabilEtapaFluxoExecucao.setEtapaFluxoExecucao(null);
        return this;
    }

    public OrdemServico getOrdemServico() {
        return this.ordemServico;
    }

    public void setOrdemServico(OrdemServico ordemServico) {
        this.ordemServico = ordemServico;
    }

    public EtapaFluxoExecucao ordemServico(OrdemServico ordemServico) {
        this.setOrdemServico(ordemServico);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EtapaFluxoExecucao)) {
            return false;
        }
        return getId() != null && getId().equals(((EtapaFluxoExecucao) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EtapaFluxoExecucao{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", feito='" + getFeito() + "'" +
            ", ordem=" + getOrdem() +
            ", agendada='" + getAgendada() + "'" +
            "}";
    }
}
