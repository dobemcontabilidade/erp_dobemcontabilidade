package com.dobemcontabilidade.domain;

import com.dobemcontabilidade.domain.enumeration.TipoDependenteFuncionarioEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DependentesFuncionario.
 */
@Entity
@Table(name = "dependentes_funcionario")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DependentesFuncionario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "url_certidao_dependente")
    private String urlCertidaoDependente;

    @Column(name = "url_rg_dependente")
    private String urlRgDependente;

    @Column(name = "dependente_irrf")
    private Boolean dependenteIRRF;

    @Column(name = "dependente_salario_familia")
    private Boolean dependenteSalarioFamilia;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_dependente_funcionario_enum")
    private TipoDependenteFuncionarioEnum tipoDependenteFuncionarioEnum;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "funcionarios",
            "anexoPessoas",
            "escolaridadePessoas",
            "bancoPessoas",
            "dependentesFuncionarios",
            "enderecoPessoas",
            "emails",
            "telefones",
            "administrador",
            "contador",
            "socio",
        },
        allowSetters = true
    )
    private Pessoa pessoa;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "usuarioEmpresa",
            "estrangeiros",
            "contratoFuncionarios",
            "demissaoFuncionarios",
            "dependentesFuncionarios",
            "empresaVinculadas",
            "departamentoFuncionarios",
            "pessoa",
            "empresa",
            "profissao",
        },
        allowSetters = true
    )
    private Funcionario funcionario;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DependentesFuncionario id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrlCertidaoDependente() {
        return this.urlCertidaoDependente;
    }

    public DependentesFuncionario urlCertidaoDependente(String urlCertidaoDependente) {
        this.setUrlCertidaoDependente(urlCertidaoDependente);
        return this;
    }

    public void setUrlCertidaoDependente(String urlCertidaoDependente) {
        this.urlCertidaoDependente = urlCertidaoDependente;
    }

    public String getUrlRgDependente() {
        return this.urlRgDependente;
    }

    public DependentesFuncionario urlRgDependente(String urlRgDependente) {
        this.setUrlRgDependente(urlRgDependente);
        return this;
    }

    public void setUrlRgDependente(String urlRgDependente) {
        this.urlRgDependente = urlRgDependente;
    }

    public Boolean getDependenteIRRF() {
        return this.dependenteIRRF;
    }

    public DependentesFuncionario dependenteIRRF(Boolean dependenteIRRF) {
        this.setDependenteIRRF(dependenteIRRF);
        return this;
    }

    public void setDependenteIRRF(Boolean dependenteIRRF) {
        this.dependenteIRRF = dependenteIRRF;
    }

    public Boolean getDependenteSalarioFamilia() {
        return this.dependenteSalarioFamilia;
    }

    public DependentesFuncionario dependenteSalarioFamilia(Boolean dependenteSalarioFamilia) {
        this.setDependenteSalarioFamilia(dependenteSalarioFamilia);
        return this;
    }

    public void setDependenteSalarioFamilia(Boolean dependenteSalarioFamilia) {
        this.dependenteSalarioFamilia = dependenteSalarioFamilia;
    }

    public TipoDependenteFuncionarioEnum getTipoDependenteFuncionarioEnum() {
        return this.tipoDependenteFuncionarioEnum;
    }

    public DependentesFuncionario tipoDependenteFuncionarioEnum(TipoDependenteFuncionarioEnum tipoDependenteFuncionarioEnum) {
        this.setTipoDependenteFuncionarioEnum(tipoDependenteFuncionarioEnum);
        return this;
    }

    public void setTipoDependenteFuncionarioEnum(TipoDependenteFuncionarioEnum tipoDependenteFuncionarioEnum) {
        this.tipoDependenteFuncionarioEnum = tipoDependenteFuncionarioEnum;
    }

    public Pessoa getPessoa() {
        return this.pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public DependentesFuncionario pessoa(Pessoa pessoa) {
        this.setPessoa(pessoa);
        return this;
    }

    public Funcionario getFuncionario() {
        return this.funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public DependentesFuncionario funcionario(Funcionario funcionario) {
        this.setFuncionario(funcionario);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DependentesFuncionario)) {
            return false;
        }
        return getId() != null && getId().equals(((DependentesFuncionario) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DependentesFuncionario{" +
            "id=" + getId() +
            ", urlCertidaoDependente='" + getUrlCertidaoDependente() + "'" +
            ", urlRgDependente='" + getUrlRgDependente() + "'" +
            ", dependenteIRRF='" + getDependenteIRRF() + "'" +
            ", dependenteSalarioFamilia='" + getDependenteSalarioFamilia() + "'" +
            ", tipoDependenteFuncionarioEnum='" + getTipoDependenteFuncionarioEnum() + "'" +
            "}";
    }
}
