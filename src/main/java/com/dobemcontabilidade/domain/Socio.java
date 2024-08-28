package com.dobemcontabilidade.domain;

import com.dobemcontabilidade.domain.enumeration.FuncaoSocioEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Socio.
 */
@Entity
@Table(name = "socio")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Socio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 200)
    @Column(name = "nome", length = 200)
    private String nome;

    @Column(name = "prolabore")
    private Boolean prolabore;

    @Column(name = "percentual_sociedade")
    private Double percentualSociedade;

    @NotNull
    @Column(name = "adminstrador", nullable = false)
    private Boolean adminstrador;

    @Column(name = "distribuicao_lucro")
    private Boolean distribuicaoLucro;

    @Column(name = "responsavel_receita")
    private Boolean responsavelReceita;

    @Column(name = "percentual_distribuicao_lucro")
    private Double percentualDistribuicaoLucro;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "funcao_socio", nullable = false)
    private FuncaoSocioEnum funcaoSocio;

    @JsonIgnoreProperties(value = { "enderecoPessoas", "docsPessoas", "emails", "telefones", "socio" }, allowSetters = true)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private PessoaFisica pessoaFisica;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "pessoaJuridica", "socios", "assinaturaEmpresas", "empresa", "ramo", "enquadramento" },
        allowSetters = true
    )
    private Empresa empresa;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Socio id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Socio nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getProlabore() {
        return this.prolabore;
    }

    public Socio prolabore(Boolean prolabore) {
        this.setProlabore(prolabore);
        return this;
    }

    public void setProlabore(Boolean prolabore) {
        this.prolabore = prolabore;
    }

    public Double getPercentualSociedade() {
        return this.percentualSociedade;
    }

    public Socio percentualSociedade(Double percentualSociedade) {
        this.setPercentualSociedade(percentualSociedade);
        return this;
    }

    public void setPercentualSociedade(Double percentualSociedade) {
        this.percentualSociedade = percentualSociedade;
    }

    public Boolean getAdminstrador() {
        return this.adminstrador;
    }

    public Socio adminstrador(Boolean adminstrador) {
        this.setAdminstrador(adminstrador);
        return this;
    }

    public void setAdminstrador(Boolean adminstrador) {
        this.adminstrador = adminstrador;
    }

    public Boolean getDistribuicaoLucro() {
        return this.distribuicaoLucro;
    }

    public Socio distribuicaoLucro(Boolean distribuicaoLucro) {
        this.setDistribuicaoLucro(distribuicaoLucro);
        return this;
    }

    public void setDistribuicaoLucro(Boolean distribuicaoLucro) {
        this.distribuicaoLucro = distribuicaoLucro;
    }

    public Boolean getResponsavelReceita() {
        return this.responsavelReceita;
    }

    public Socio responsavelReceita(Boolean responsavelReceita) {
        this.setResponsavelReceita(responsavelReceita);
        return this;
    }

    public void setResponsavelReceita(Boolean responsavelReceita) {
        this.responsavelReceita = responsavelReceita;
    }

    public Double getPercentualDistribuicaoLucro() {
        return this.percentualDistribuicaoLucro;
    }

    public Socio percentualDistribuicaoLucro(Double percentualDistribuicaoLucro) {
        this.setPercentualDistribuicaoLucro(percentualDistribuicaoLucro);
        return this;
    }

    public void setPercentualDistribuicaoLucro(Double percentualDistribuicaoLucro) {
        this.percentualDistribuicaoLucro = percentualDistribuicaoLucro;
    }

    public FuncaoSocioEnum getFuncaoSocio() {
        return this.funcaoSocio;
    }

    public Socio funcaoSocio(FuncaoSocioEnum funcaoSocio) {
        this.setFuncaoSocio(funcaoSocio);
        return this;
    }

    public void setFuncaoSocio(FuncaoSocioEnum funcaoSocio) {
        this.funcaoSocio = funcaoSocio;
    }

    public PessoaFisica getPessoaFisica() {
        return this.pessoaFisica;
    }

    public void setPessoaFisica(PessoaFisica pessoaFisica) {
        this.pessoaFisica = pessoaFisica;
    }

    public Socio pessoaFisica(PessoaFisica pessoaFisica) {
        this.setPessoaFisica(pessoaFisica);
        return this;
    }

    public Empresa getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Socio empresa(Empresa empresa) {
        this.setEmpresa(empresa);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Socio)) {
            return false;
        }
        return getId() != null && getId().equals(((Socio) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Socio{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", prolabore='" + getProlabore() + "'" +
            ", percentualSociedade=" + getPercentualSociedade() +
            ", adminstrador='" + getAdminstrador() + "'" +
            ", distribuicaoLucro='" + getDistribuicaoLucro() + "'" +
            ", responsavelReceita='" + getResponsavelReceita() + "'" +
            ", percentualDistribuicaoLucro=" + getPercentualDistribuicaoLucro() +
            ", funcaoSocio='" + getFuncaoSocio() + "'" +
            "}";
    }
}
