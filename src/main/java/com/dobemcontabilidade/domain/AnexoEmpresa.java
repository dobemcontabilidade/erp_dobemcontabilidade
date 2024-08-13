package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AnexoEmpresa.
 */
@Entity
@Table(name = "anexo_empresa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AnexoEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Lob
    @Column(name = "url_anexo", nullable = false)
    private String urlAnexo;

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
        value = { "anexoEmpresas", "anexoRequerido", "enquadramento", "tributacao", "ramo", "empresa", "empresaModelo" },
        allowSetters = true
    )
    private AnexoRequeridoEmpresa anexoRequeridoEmpresa;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AnexoEmpresa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrlAnexo() {
        return this.urlAnexo;
    }

    public AnexoEmpresa urlAnexo(String urlAnexo) {
        this.setUrlAnexo(urlAnexo);
        return this;
    }

    public void setUrlAnexo(String urlAnexo) {
        this.urlAnexo = urlAnexo;
    }

    public Empresa getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public AnexoEmpresa empresa(Empresa empresa) {
        this.setEmpresa(empresa);
        return this;
    }

    public AnexoRequeridoEmpresa getAnexoRequeridoEmpresa() {
        return this.anexoRequeridoEmpresa;
    }

    public void setAnexoRequeridoEmpresa(AnexoRequeridoEmpresa anexoRequeridoEmpresa) {
        this.anexoRequeridoEmpresa = anexoRequeridoEmpresa;
    }

    public AnexoEmpresa anexoRequeridoEmpresa(AnexoRequeridoEmpresa anexoRequeridoEmpresa) {
        this.setAnexoRequeridoEmpresa(anexoRequeridoEmpresa);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AnexoEmpresa)) {
            return false;
        }
        return getId() != null && getId().equals(((AnexoEmpresa) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AnexoEmpresa{" +
            "id=" + getId() +
            ", urlAnexo='" + getUrlAnexo() + "'" +
            "}";
    }
}
