package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AtividadeEmpresa.
 */
@Entity
@Table(name = "atividade_empresa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AtividadeEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "principal")
    private Boolean principal;

    @Column(name = "ordem")
    private Integer ordem;

    @Column(name = "descricao_atividade")
    private String descricaoAtividade;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "observacaoCnaes", "atividadeEmpresas", "classe", "segmentoCnaes" }, allowSetters = true)
    private SubclasseCnae cnae;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "assinaturaEmpresas",
            "funcionarios",
            "departamentoEmpresas",
            "tarefaEmpresas",
            "enderecoEmpresas",
            "atividadeEmpresas",
            "socios",
            "anexoEmpresas",
            "certificadoDigitals",
            "usuarioEmpresas",
            "opcaoRazaoSocialEmpresas",
            "opcaoNomeFantasiaEmpresas",
            "segmentoCnaes",
            "ramo",
            "tributacao",
            "enquadramento",
        },
        allowSetters = true
    )
    private Empresa empresa;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AtividadeEmpresa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getPrincipal() {
        return this.principal;
    }

    public AtividadeEmpresa principal(Boolean principal) {
        this.setPrincipal(principal);
        return this;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }

    public Integer getOrdem() {
        return this.ordem;
    }

    public AtividadeEmpresa ordem(Integer ordem) {
        this.setOrdem(ordem);
        return this;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public String getDescricaoAtividade() {
        return this.descricaoAtividade;
    }

    public AtividadeEmpresa descricaoAtividade(String descricaoAtividade) {
        this.setDescricaoAtividade(descricaoAtividade);
        return this;
    }

    public void setDescricaoAtividade(String descricaoAtividade) {
        this.descricaoAtividade = descricaoAtividade;
    }

    public SubclasseCnae getCnae() {
        return this.cnae;
    }

    public void setCnae(SubclasseCnae subclasseCnae) {
        this.cnae = subclasseCnae;
    }

    public AtividadeEmpresa cnae(SubclasseCnae subclasseCnae) {
        this.setCnae(subclasseCnae);
        return this;
    }

    public Empresa getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public AtividadeEmpresa empresa(Empresa empresa) {
        this.setEmpresa(empresa);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AtividadeEmpresa)) {
            return false;
        }
        return getId() != null && getId().equals(((AtividadeEmpresa) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AtividadeEmpresa{" +
            "id=" + getId() +
            ", principal='" + getPrincipal() + "'" +
            ", ordem=" + getOrdem() +
            ", descricaoAtividade='" + getDescricaoAtividade() + "'" +
            "}";
    }
}
