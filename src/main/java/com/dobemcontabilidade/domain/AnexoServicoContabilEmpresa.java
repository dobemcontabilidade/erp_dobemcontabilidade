package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AnexoServicoContabilEmpresa.
 */
@Entity
@Table(name = "anexo_servico_contabil_empresa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AnexoServicoContabilEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "link")
    private String link;

    @Column(name = "data_hora_upload")
    private Instant dataHoraUpload;

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

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "anexoServicoContabilEmpresas", "tarefaRecorrentes", "servicoContabil", "assinaturaEmpresa" },
        allowSetters = true
    )
    private ServicoContabilAssinaturaEmpresa servicoContabilAssinaturaEmpresa;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AnexoServicoContabilEmpresa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLink() {
        return this.link;
    }

    public AnexoServicoContabilEmpresa link(String link) {
        this.setLink(link);
        return this;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Instant getDataHoraUpload() {
        return this.dataHoraUpload;
    }

    public AnexoServicoContabilEmpresa dataHoraUpload(Instant dataHoraUpload) {
        this.setDataHoraUpload(dataHoraUpload);
        return this;
    }

    public void setDataHoraUpload(Instant dataHoraUpload) {
        this.dataHoraUpload = dataHoraUpload;
    }

    public AnexoRequerido getAnexoRequerido() {
        return this.anexoRequerido;
    }

    public void setAnexoRequerido(AnexoRequerido anexoRequerido) {
        this.anexoRequerido = anexoRequerido;
    }

    public AnexoServicoContabilEmpresa anexoRequerido(AnexoRequerido anexoRequerido) {
        this.setAnexoRequerido(anexoRequerido);
        return this;
    }

    public ServicoContabilAssinaturaEmpresa getServicoContabilAssinaturaEmpresa() {
        return this.servicoContabilAssinaturaEmpresa;
    }

    public void setServicoContabilAssinaturaEmpresa(ServicoContabilAssinaturaEmpresa servicoContabilAssinaturaEmpresa) {
        this.servicoContabilAssinaturaEmpresa = servicoContabilAssinaturaEmpresa;
    }

    public AnexoServicoContabilEmpresa servicoContabilAssinaturaEmpresa(ServicoContabilAssinaturaEmpresa servicoContabilAssinaturaEmpresa) {
        this.setServicoContabilAssinaturaEmpresa(servicoContabilAssinaturaEmpresa);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnexoServicoContabilEmpresa)) {
            return false;
        }
        return getId() != null && getId().equals(((AnexoServicoContabilEmpresa) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnexoServicoContabilEmpresa{" +
            "id=" + getId() +
            ", link='" + getLink() + "'" +
            ", dataHoraUpload='" + getDataHoraUpload() + "'" +
            "}";
    }
}
