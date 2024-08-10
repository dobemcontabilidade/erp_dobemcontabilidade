package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BancoContador.
 */
@Entity
@Table(name = "banco_contador")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BancoContador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 30)
    @Column(name = "agencia", length = 30, nullable = false)
    private String agencia;

    @NotNull
    @Size(max = 30)
    @Column(name = "conta", length = 30, nullable = false)
    private String conta;

    @Column(name = "digito_agencia")
    private String digitoAgencia;

    @Column(name = "digito_conta")
    private String digitoConta;

    @Column(name = "principal")
    private Boolean principal;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "pessoa",
            "areaContabilEmpresas",
            "areaContabilContadors",
            "departamentoEmpresas",
            "departamentoContadors",
            "termoAdesaoContadors",
            "bancoContadors",
            "avaliacaoContadors",
            "tarefaEmpresas",
            "perfilContador",
            "usuarioContador",
        },
        allowSetters = true
    )
    private Contador contador;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "bancoContadors" }, allowSetters = true)
    private Banco banco;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BancoContador id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgencia() {
        return this.agencia;
    }

    public BancoContador agencia(String agencia) {
        this.setAgencia(agencia);
        return this;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getConta() {
        return this.conta;
    }

    public BancoContador conta(String conta) {
        this.setConta(conta);
        return this;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public String getDigitoAgencia() {
        return this.digitoAgencia;
    }

    public BancoContador digitoAgencia(String digitoAgencia) {
        this.setDigitoAgencia(digitoAgencia);
        return this;
    }

    public void setDigitoAgencia(String digitoAgencia) {
        this.digitoAgencia = digitoAgencia;
    }

    public String getDigitoConta() {
        return this.digitoConta;
    }

    public BancoContador digitoConta(String digitoConta) {
        this.setDigitoConta(digitoConta);
        return this;
    }

    public void setDigitoConta(String digitoConta) {
        this.digitoConta = digitoConta;
    }

    public Boolean getPrincipal() {
        return this.principal;
    }

    public BancoContador principal(Boolean principal) {
        this.setPrincipal(principal);
        return this;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }

    public Contador getContador() {
        return this.contador;
    }

    public void setContador(Contador contador) {
        this.contador = contador;
    }

    public BancoContador contador(Contador contador) {
        this.setContador(contador);
        return this;
    }

    public Banco getBanco() {
        return this.banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public BancoContador banco(Banco banco) {
        this.setBanco(banco);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BancoContador)) {
            return false;
        }
        return getId() != null && getId().equals(((BancoContador) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BancoContador{" +
            "id=" + getId() +
            ", agencia='" + getAgencia() + "'" +
            ", conta='" + getConta() + "'" +
            ", digitoAgencia='" + getDigitoAgencia() + "'" +
            ", digitoConta='" + getDigitoConta() + "'" +
            ", principal='" + getPrincipal() + "'" +
            "}";
    }
}
