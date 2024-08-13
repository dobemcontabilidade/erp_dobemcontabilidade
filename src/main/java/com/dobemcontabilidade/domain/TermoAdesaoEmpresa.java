package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TermoAdesaoEmpresa.
 */
@Entity
@Table(name = "termo_adesao_empresa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TermoAdesaoEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "data_adesao")
    private Instant dataAdesao;

    @Column(name = "checked")
    private Boolean checked;

    @Column(name = "url_doc")
    private String urlDoc;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "funcionarios",
            "anexoEmpresas",
            "ordemServicos",
            "anexoRequeridoEmpresas",
            "impostoEmpresas",
            "parcelamentoImpostos",
            "assinaturaEmpresas",
            "departamentoEmpresas",
            "tarefaEmpresas",
            "enderecoEmpresas",
            "atividadeEmpresas",
            "socios",
            "certificadoDigitals",
            "opcaoRazaoSocialEmpresas",
            "opcaoNomeFantasiaEmpresas",
            "termoAdesaoEmpresas",
            "segmentoCnaes",
            "empresaModelo",
        },
        allowSetters = true
    )
    private Empresa empresa;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "calculoPlanoAssinaturas",
            "assinaturaEmpresas",
            "descontoPlanoContabils",
            "adicionalRamos",
            "adicionalTributacaos",
            "termoContratoContabils",
            "adicionalEnquadramentos",
            "valorBaseRamos",
            "termoAdesaoEmpresas",
        },
        allowSetters = true
    )
    private PlanoContabil planoContabil;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TermoAdesaoEmpresa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDataAdesao() {
        return this.dataAdesao;
    }

    public TermoAdesaoEmpresa dataAdesao(Instant dataAdesao) {
        this.setDataAdesao(dataAdesao);
        return this;
    }

    public void setDataAdesao(Instant dataAdesao) {
        this.dataAdesao = dataAdesao;
    }

    public Boolean getChecked() {
        return this.checked;
    }

    public TermoAdesaoEmpresa checked(Boolean checked) {
        this.setChecked(checked);
        return this;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public String getUrlDoc() {
        return this.urlDoc;
    }

    public TermoAdesaoEmpresa urlDoc(String urlDoc) {
        this.setUrlDoc(urlDoc);
        return this;
    }

    public void setUrlDoc(String urlDoc) {
        this.urlDoc = urlDoc;
    }

    public Empresa getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public TermoAdesaoEmpresa empresa(Empresa empresa) {
        this.setEmpresa(empresa);
        return this;
    }

    public PlanoContabil getPlanoContabil() {
        return this.planoContabil;
    }

    public void setPlanoContabil(PlanoContabil planoContabil) {
        this.planoContabil = planoContabil;
    }

    public TermoAdesaoEmpresa planoContabil(PlanoContabil planoContabil) {
        this.setPlanoContabil(planoContabil);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TermoAdesaoEmpresa)) {
            return false;
        }
        return getId() != null && getId().equals(((TermoAdesaoEmpresa) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TermoAdesaoEmpresa{" +
            "id=" + getId() +
            ", dataAdesao='" + getDataAdesao() + "'" +
            ", checked='" + getChecked() + "'" +
            ", urlDoc='" + getUrlDoc() + "'" +
            "}";
    }
}
