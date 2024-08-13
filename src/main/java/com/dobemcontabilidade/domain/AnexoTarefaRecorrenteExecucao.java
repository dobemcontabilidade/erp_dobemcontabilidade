package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AnexoTarefaRecorrenteExecucao.
 */
@Entity
@Table(name = "anexo_taf_reco_exec")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AnexoTarefaRecorrenteExecucao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "url")
    private String url;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "data_hora_upload")
    private Instant dataHoraUpload;

    @ManyToOne(optional = false)
    @NotNull
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
    private TarefaRecorrenteExecucao tarefaRecorrenteExecucao;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AnexoTarefaRecorrenteExecucao id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return this.url;
    }

    public AnexoTarefaRecorrenteExecucao url(String url) {
        this.setUrl(url);
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public AnexoTarefaRecorrenteExecucao descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Instant getDataHoraUpload() {
        return this.dataHoraUpload;
    }

    public AnexoTarefaRecorrenteExecucao dataHoraUpload(Instant dataHoraUpload) {
        this.setDataHoraUpload(dataHoraUpload);
        return this;
    }

    public void setDataHoraUpload(Instant dataHoraUpload) {
        this.dataHoraUpload = dataHoraUpload;
    }

    public TarefaRecorrenteExecucao getTarefaRecorrenteExecucao() {
        return this.tarefaRecorrenteExecucao;
    }

    public void setTarefaRecorrenteExecucao(TarefaRecorrenteExecucao tarefaRecorrenteExecucao) {
        this.tarefaRecorrenteExecucao = tarefaRecorrenteExecucao;
    }

    public AnexoTarefaRecorrenteExecucao tarefaRecorrenteExecucao(TarefaRecorrenteExecucao tarefaRecorrenteExecucao) {
        this.setTarefaRecorrenteExecucao(tarefaRecorrenteExecucao);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnexoTarefaRecorrenteExecucao)) {
            return false;
        }
        return getId() != null && getId().equals(((AnexoTarefaRecorrenteExecucao) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnexoTarefaRecorrenteExecucao{" +
            "id=" + getId() +
            ", url='" + getUrl() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", dataHoraUpload='" + getDataHoraUpload() + "'" +
            "}";
    }
}
