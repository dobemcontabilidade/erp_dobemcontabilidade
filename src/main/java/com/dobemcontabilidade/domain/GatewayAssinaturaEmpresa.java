package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A GatewayAssinaturaEmpresa.
 */
@Entity
@Table(name = "gateway_assinatura_empresa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GatewayAssinaturaEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "ativo")
    private Boolean ativo;

    @Column(name = "codigo_assinatura")
    private String codigoAssinatura;

    @ManyToOne(optional = false)
    @NotNull
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
    private AssinaturaEmpresa assinaturaEmpresa;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "gatewayAssinaturaEmpresas" }, allowSetters = true)
    private GatewayPagamento gatewayPagamento;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public GatewayAssinaturaEmpresa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAtivo() {
        return this.ativo;
    }

    public GatewayAssinaturaEmpresa ativo(Boolean ativo) {
        this.setAtivo(ativo);
        return this;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getCodigoAssinatura() {
        return this.codigoAssinatura;
    }

    public GatewayAssinaturaEmpresa codigoAssinatura(String codigoAssinatura) {
        this.setCodigoAssinatura(codigoAssinatura);
        return this;
    }

    public void setCodigoAssinatura(String codigoAssinatura) {
        this.codigoAssinatura = codigoAssinatura;
    }

    public AssinaturaEmpresa getAssinaturaEmpresa() {
        return this.assinaturaEmpresa;
    }

    public void setAssinaturaEmpresa(AssinaturaEmpresa assinaturaEmpresa) {
        this.assinaturaEmpresa = assinaturaEmpresa;
    }

    public GatewayAssinaturaEmpresa assinaturaEmpresa(AssinaturaEmpresa assinaturaEmpresa) {
        this.setAssinaturaEmpresa(assinaturaEmpresa);
        return this;
    }

    public GatewayPagamento getGatewayPagamento() {
        return this.gatewayPagamento;
    }

    public void setGatewayPagamento(GatewayPagamento gatewayPagamento) {
        this.gatewayPagamento = gatewayPagamento;
    }

    public GatewayAssinaturaEmpresa gatewayPagamento(GatewayPagamento gatewayPagamento) {
        this.setGatewayPagamento(gatewayPagamento);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GatewayAssinaturaEmpresa)) {
            return false;
        }
        return getId() != null && getId().equals(((GatewayAssinaturaEmpresa) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GatewayAssinaturaEmpresa{" +
            "id=" + getId() +
            ", ativo='" + getAtivo() + "'" +
            ", codigoAssinatura='" + getCodigoAssinatura() + "'" +
            "}";
    }
}
