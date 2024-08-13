package com.dobemcontabilidade.domain;

import com.dobemcontabilidade.domain.enumeration.TipoContaBancoEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BancoPessoa.
 */
@Entity
@Table(name = "banco_pessoa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BancoPessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "agencia", length = 100, nullable = false)
    private String agencia;

    @NotNull
    @Size(max = 30)
    @Column(name = "conta", length = 30, nullable = false)
    private String conta;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_conta")
    private TipoContaBancoEnum tipoConta;

    @Column(name = "principal")
    private Boolean principal;

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
    @JsonIgnoreProperties(value = { "bancoPessoas" }, allowSetters = true)
    private Banco banco;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BancoPessoa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgencia() {
        return this.agencia;
    }

    public BancoPessoa agencia(String agencia) {
        this.setAgencia(agencia);
        return this;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getConta() {
        return this.conta;
    }

    public BancoPessoa conta(String conta) {
        this.setConta(conta);
        return this;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public TipoContaBancoEnum getTipoConta() {
        return this.tipoConta;
    }

    public BancoPessoa tipoConta(TipoContaBancoEnum tipoConta) {
        this.setTipoConta(tipoConta);
        return this;
    }

    public void setTipoConta(TipoContaBancoEnum tipoConta) {
        this.tipoConta = tipoConta;
    }

    public Boolean getPrincipal() {
        return this.principal;
    }

    public BancoPessoa principal(Boolean principal) {
        this.setPrincipal(principal);
        return this;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }

    public Pessoa getPessoa() {
        return this.pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public BancoPessoa pessoa(Pessoa pessoa) {
        this.setPessoa(pessoa);
        return this;
    }

    public Banco getBanco() {
        return this.banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public BancoPessoa banco(Banco banco) {
        this.setBanco(banco);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BancoPessoa)) {
            return false;
        }
        return getId() != null && getId().equals(((BancoPessoa) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BancoPessoa{" +
            "id=" + getId() +
            ", agencia='" + getAgencia() + "'" +
            ", conta='" + getConta() + "'" +
            ", tipoConta='" + getTipoConta() + "'" +
            ", principal='" + getPrincipal() + "'" +
            "}";
    }
}
