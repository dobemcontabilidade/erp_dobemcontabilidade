package com.dobemcontabilidade.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.BancoContador} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BancoContadorDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 30)
    private String agencia;

    @NotNull
    @Size(max = 30)
    private String conta;

    private String digitoAgencia;

    private String digitoConta;

    private Boolean principal;

    @NotNull
    private ContadorDTO contador;

    @NotNull
    private BancoDTO banco;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public String getDigitoAgencia() {
        return digitoAgencia;
    }

    public void setDigitoAgencia(String digitoAgencia) {
        this.digitoAgencia = digitoAgencia;
    }

    public String getDigitoConta() {
        return digitoConta;
    }

    public void setDigitoConta(String digitoConta) {
        this.digitoConta = digitoConta;
    }

    public Boolean getPrincipal() {
        return principal;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }

    public ContadorDTO getContador() {
        return contador;
    }

    public void setContador(ContadorDTO contador) {
        this.contador = contador;
    }

    public BancoDTO getBanco() {
        return banco;
    }

    public void setBanco(BancoDTO banco) {
        this.banco = banco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BancoContadorDTO)) {
            return false;
        }

        BancoContadorDTO bancoContadorDTO = (BancoContadorDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bancoContadorDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BancoContadorDTO{" +
            "id=" + getId() +
            ", agencia='" + getAgencia() + "'" +
            ", conta='" + getConta() + "'" +
            ", digitoAgencia='" + getDigitoAgencia() + "'" +
            ", digitoConta='" + getDigitoConta() + "'" +
            ", principal='" + getPrincipal() + "'" +
            ", contador=" + getContador() +
            ", banco=" + getBanco() +
            "}";
    }
}
