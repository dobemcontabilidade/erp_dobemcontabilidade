package com.dobemcontabilidade.domain;

import com.dobemcontabilidade.domain.enumeration.SituacaoPlanoContaAzul;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PlanoContaAzul.
 */
@Entity
@Table(name = "plano_conta_azul")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlanoContaAzul implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "valor_base")
    private Double valorBase;

    @Column(name = "usuarios")
    private Integer usuarios;

    @Column(name = "boletos")
    private Integer boletos;

    @Column(name = "nota_fiscal_produto")
    private Integer notaFiscalProduto;

    @Column(name = "nota_fiscal_servico")
    private Integer notaFiscalServico;

    @Column(name = "nota_fiscal_ce")
    private Integer notaFiscalCe;

    @Column(name = "suporte")
    private Boolean suporte;

    @Enumerated(EnumType.STRING)
    @Column(name = "situacao")
    private SituacaoPlanoContaAzul situacao;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "planoContaAzul")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "periodoPagamento",
            "planoContaAzul",
            "planoContabil",
            "ramo",
            "tributacao",
            "descontoPlanoContabil",
            "descontoPlanoContaAzul",
            "assinaturaEmpresa",
        },
        allowSetters = true
    )
    private Set<CalculoPlanoAssinatura> calculoPlanoAssinaturas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "planoContaAzul")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = {
            "grupoAcessoEmpresas",
            "areaContabilAssinaturaEmpresas",
            "servicoContabilAssinaturaEmpresas",
            "gatewayAssinaturaEmpresas",
            "calculoPlanoAssinaturas",
            "pagamentos",
            "cobrancaEmpresas",
            "usuarioEmpresas",
            "periodoPagamento",
            "formaDePagamento",
            "planoContaAzul",
            "planoContabil",
            "empresa",
        },
        allowSetters = true
    )
    private Set<AssinaturaEmpresa> assinaturaEmpresas = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "planoContaAzul")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "calculoPlanoAssinaturas", "planoContaAzul", "periodoPagamento" }, allowSetters = true)
    private Set<DescontoPlanoContaAzul> descontoPlanoContaAzuls = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PlanoContaAzul id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public PlanoContaAzul nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValorBase() {
        return this.valorBase;
    }

    public PlanoContaAzul valorBase(Double valorBase) {
        this.setValorBase(valorBase);
        return this;
    }

    public void setValorBase(Double valorBase) {
        this.valorBase = valorBase;
    }

    public Integer getUsuarios() {
        return this.usuarios;
    }

    public PlanoContaAzul usuarios(Integer usuarios) {
        this.setUsuarios(usuarios);
        return this;
    }

    public void setUsuarios(Integer usuarios) {
        this.usuarios = usuarios;
    }

    public Integer getBoletos() {
        return this.boletos;
    }

    public PlanoContaAzul boletos(Integer boletos) {
        this.setBoletos(boletos);
        return this;
    }

    public void setBoletos(Integer boletos) {
        this.boletos = boletos;
    }

    public Integer getNotaFiscalProduto() {
        return this.notaFiscalProduto;
    }

    public PlanoContaAzul notaFiscalProduto(Integer notaFiscalProduto) {
        this.setNotaFiscalProduto(notaFiscalProduto);
        return this;
    }

    public void setNotaFiscalProduto(Integer notaFiscalProduto) {
        this.notaFiscalProduto = notaFiscalProduto;
    }

    public Integer getNotaFiscalServico() {
        return this.notaFiscalServico;
    }

    public PlanoContaAzul notaFiscalServico(Integer notaFiscalServico) {
        this.setNotaFiscalServico(notaFiscalServico);
        return this;
    }

    public void setNotaFiscalServico(Integer notaFiscalServico) {
        this.notaFiscalServico = notaFiscalServico;
    }

    public Integer getNotaFiscalCe() {
        return this.notaFiscalCe;
    }

    public PlanoContaAzul notaFiscalCe(Integer notaFiscalCe) {
        this.setNotaFiscalCe(notaFiscalCe);
        return this;
    }

    public void setNotaFiscalCe(Integer notaFiscalCe) {
        this.notaFiscalCe = notaFiscalCe;
    }

    public Boolean getSuporte() {
        return this.suporte;
    }

    public PlanoContaAzul suporte(Boolean suporte) {
        this.setSuporte(suporte);
        return this;
    }

    public void setSuporte(Boolean suporte) {
        this.suporte = suporte;
    }

    public SituacaoPlanoContaAzul getSituacao() {
        return this.situacao;
    }

    public PlanoContaAzul situacao(SituacaoPlanoContaAzul situacao) {
        this.setSituacao(situacao);
        return this;
    }

    public void setSituacao(SituacaoPlanoContaAzul situacao) {
        this.situacao = situacao;
    }

    public Set<CalculoPlanoAssinatura> getCalculoPlanoAssinaturas() {
        return this.calculoPlanoAssinaturas;
    }

    public void setCalculoPlanoAssinaturas(Set<CalculoPlanoAssinatura> calculoPlanoAssinaturas) {
        if (this.calculoPlanoAssinaturas != null) {
            this.calculoPlanoAssinaturas.forEach(i -> i.setPlanoContaAzul(null));
        }
        if (calculoPlanoAssinaturas != null) {
            calculoPlanoAssinaturas.forEach(i -> i.setPlanoContaAzul(this));
        }
        this.calculoPlanoAssinaturas = calculoPlanoAssinaturas;
    }

    public PlanoContaAzul calculoPlanoAssinaturas(Set<CalculoPlanoAssinatura> calculoPlanoAssinaturas) {
        this.setCalculoPlanoAssinaturas(calculoPlanoAssinaturas);
        return this;
    }

    public PlanoContaAzul addCalculoPlanoAssinatura(CalculoPlanoAssinatura calculoPlanoAssinatura) {
        this.calculoPlanoAssinaturas.add(calculoPlanoAssinatura);
        calculoPlanoAssinatura.setPlanoContaAzul(this);
        return this;
    }

    public PlanoContaAzul removeCalculoPlanoAssinatura(CalculoPlanoAssinatura calculoPlanoAssinatura) {
        this.calculoPlanoAssinaturas.remove(calculoPlanoAssinatura);
        calculoPlanoAssinatura.setPlanoContaAzul(null);
        return this;
    }

    public Set<AssinaturaEmpresa> getAssinaturaEmpresas() {
        return this.assinaturaEmpresas;
    }

    public void setAssinaturaEmpresas(Set<AssinaturaEmpresa> assinaturaEmpresas) {
        if (this.assinaturaEmpresas != null) {
            this.assinaturaEmpresas.forEach(i -> i.setPlanoContaAzul(null));
        }
        if (assinaturaEmpresas != null) {
            assinaturaEmpresas.forEach(i -> i.setPlanoContaAzul(this));
        }
        this.assinaturaEmpresas = assinaturaEmpresas;
    }

    public PlanoContaAzul assinaturaEmpresas(Set<AssinaturaEmpresa> assinaturaEmpresas) {
        this.setAssinaturaEmpresas(assinaturaEmpresas);
        return this;
    }

    public PlanoContaAzul addAssinaturaEmpresa(AssinaturaEmpresa assinaturaEmpresa) {
        this.assinaturaEmpresas.add(assinaturaEmpresa);
        assinaturaEmpresa.setPlanoContaAzul(this);
        return this;
    }

    public PlanoContaAzul removeAssinaturaEmpresa(AssinaturaEmpresa assinaturaEmpresa) {
        this.assinaturaEmpresas.remove(assinaturaEmpresa);
        assinaturaEmpresa.setPlanoContaAzul(null);
        return this;
    }

    public Set<DescontoPlanoContaAzul> getDescontoPlanoContaAzuls() {
        return this.descontoPlanoContaAzuls;
    }

    public void setDescontoPlanoContaAzuls(Set<DescontoPlanoContaAzul> descontoPlanoContaAzuls) {
        if (this.descontoPlanoContaAzuls != null) {
            this.descontoPlanoContaAzuls.forEach(i -> i.setPlanoContaAzul(null));
        }
        if (descontoPlanoContaAzuls != null) {
            descontoPlanoContaAzuls.forEach(i -> i.setPlanoContaAzul(this));
        }
        this.descontoPlanoContaAzuls = descontoPlanoContaAzuls;
    }

    public PlanoContaAzul descontoPlanoContaAzuls(Set<DescontoPlanoContaAzul> descontoPlanoContaAzuls) {
        this.setDescontoPlanoContaAzuls(descontoPlanoContaAzuls);
        return this;
    }

    public PlanoContaAzul addDescontoPlanoContaAzul(DescontoPlanoContaAzul descontoPlanoContaAzul) {
        this.descontoPlanoContaAzuls.add(descontoPlanoContaAzul);
        descontoPlanoContaAzul.setPlanoContaAzul(this);
        return this;
    }

    public PlanoContaAzul removeDescontoPlanoContaAzul(DescontoPlanoContaAzul descontoPlanoContaAzul) {
        this.descontoPlanoContaAzuls.remove(descontoPlanoContaAzul);
        descontoPlanoContaAzul.setPlanoContaAzul(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlanoContaAzul)) {
            return false;
        }
        return getId() != null && getId().equals(((PlanoContaAzul) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlanoContaAzul{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", valorBase=" + getValorBase() +
            ", usuarios=" + getUsuarios() +
            ", boletos=" + getBoletos() +
            ", notaFiscalProduto=" + getNotaFiscalProduto() +
            ", notaFiscalServico=" + getNotaFiscalServico() +
            ", notaFiscalCe=" + getNotaFiscalCe() +
            ", suporte='" + getSuporte() + "'" +
            ", situacao='" + getSituacao() + "'" +
            "}";
    }
}
