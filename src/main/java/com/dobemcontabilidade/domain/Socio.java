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
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Pessoa pessoa;

    @JsonIgnoreProperties(
        value = {
            "grupoAcessoUsuarioEmpresas",
            "feedBackUsuarioParaContadors",
            "feedBackContadorParaUsuarios",
            "assinaturaEmpresa",
            "funcionario",
            "socio",
        },
        allowSetters = true
    )
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private UsuarioEmpresa usuarioEmpresa;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "funcionarios", "socios" }, allowSetters = true)
    private Profissao profissao;

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

    public Pessoa getPessoa() {
        return this.pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Socio pessoa(Pessoa pessoa) {
        this.setPessoa(pessoa);
        return this;
    }

    public UsuarioEmpresa getUsuarioEmpresa() {
        return this.usuarioEmpresa;
    }

    public void setUsuarioEmpresa(UsuarioEmpresa usuarioEmpresa) {
        this.usuarioEmpresa = usuarioEmpresa;
    }

    public Socio usuarioEmpresa(UsuarioEmpresa usuarioEmpresa) {
        this.setUsuarioEmpresa(usuarioEmpresa);
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

    public Profissao getProfissao() {
        return this.profissao;
    }

    public void setProfissao(Profissao profissao) {
        this.profissao = profissao;
    }

    public Socio profissao(Profissao profissao) {
        this.setProfissao(profissao);
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
