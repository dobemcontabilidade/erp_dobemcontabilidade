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
 * A ImpostoEmpresa.
 */
@Entity
@Table(name = "imposto_empresa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ImpostoEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "dia_vencimento")
    private Integer diaVencimento;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "imposto")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "impostoParcelados", "imposto" }, allowSetters = true)
    private Set<ImpostoAPagarEmpresa> impostoAPagarEmpresas = new HashSet<>();

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
    @JsonIgnoreProperties(value = { "impostoEmpresas", "parcelamentoImpostos", "impostoEmpresaModelos", "esfera" }, allowSetters = true)
    private Imposto imposto;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ImpostoEmpresa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDiaVencimento() {
        return this.diaVencimento;
    }

    public ImpostoEmpresa diaVencimento(Integer diaVencimento) {
        this.setDiaVencimento(diaVencimento);
        return this;
    }

    public void setDiaVencimento(Integer diaVencimento) {
        this.diaVencimento = diaVencimento;
    }

    public Set<ImpostoAPagarEmpresa> getImpostoAPagarEmpresas() {
        return this.impostoAPagarEmpresas;
    }

    public void setImpostoAPagarEmpresas(Set<ImpostoAPagarEmpresa> impostoAPagarEmpresas) {
        if (this.impostoAPagarEmpresas != null) {
            this.impostoAPagarEmpresas.forEach(i -> i.setImposto(null));
        }
        if (impostoAPagarEmpresas != null) {
            impostoAPagarEmpresas.forEach(i -> i.setImposto(this));
        }
        this.impostoAPagarEmpresas = impostoAPagarEmpresas;
    }

    public ImpostoEmpresa impostoAPagarEmpresas(Set<ImpostoAPagarEmpresa> impostoAPagarEmpresas) {
        this.setImpostoAPagarEmpresas(impostoAPagarEmpresas);
        return this;
    }

    public ImpostoEmpresa addImpostoAPagarEmpresa(ImpostoAPagarEmpresa impostoAPagarEmpresa) {
        this.impostoAPagarEmpresas.add(impostoAPagarEmpresa);
        impostoAPagarEmpresa.setImposto(this);
        return this;
    }

    public ImpostoEmpresa removeImpostoAPagarEmpresa(ImpostoAPagarEmpresa impostoAPagarEmpresa) {
        this.impostoAPagarEmpresas.remove(impostoAPagarEmpresa);
        impostoAPagarEmpresa.setImposto(null);
        return this;
    }

    public Empresa getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public ImpostoEmpresa empresa(Empresa empresa) {
        this.setEmpresa(empresa);
        return this;
    }

    public Imposto getImposto() {
        return this.imposto;
    }

    public void setImposto(Imposto imposto) {
        this.imposto = imposto;
    }

    public ImpostoEmpresa imposto(Imposto imposto) {
        this.setImposto(imposto);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ImpostoEmpresa)) {
            return false;
        }
        return getId() != null && getId().equals(((ImpostoEmpresa) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ImpostoEmpresa{" +
            "id=" + getId() +
            ", diaVencimento=" + getDiaVencimento() +
            "}";
    }
}
