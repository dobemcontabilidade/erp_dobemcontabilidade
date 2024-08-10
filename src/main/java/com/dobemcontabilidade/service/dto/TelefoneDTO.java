package com.dobemcontabilidade.service.dto;

import com.dobemcontabilidade.domain.enumeration.TipoTelefoneEnum;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.Telefone} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TelefoneDTO implements Serializable {

    private Long id;

    @NotNull
    private String codigoArea;

    @NotNull
    private String telefone;

    private Boolean principla;

    private TipoTelefoneEnum tipoTelefone;

    @NotNull
    private PessoaDTO pessoa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoArea() {
        return codigoArea;
    }

    public void setCodigoArea(String codigoArea) {
        this.codigoArea = codigoArea;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Boolean getPrincipla() {
        return principla;
    }

    public void setPrincipla(Boolean principla) {
        this.principla = principla;
    }

    public TipoTelefoneEnum getTipoTelefone() {
        return tipoTelefone;
    }

    public void setTipoTelefone(TipoTelefoneEnum tipoTelefone) {
        this.tipoTelefone = tipoTelefone;
    }

    public PessoaDTO getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaDTO pessoa) {
        this.pessoa = pessoa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TelefoneDTO)) {
            return false;
        }

        TelefoneDTO telefoneDTO = (TelefoneDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, telefoneDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TelefoneDTO{" +
            "id=" + getId() +
            ", codigoArea='" + getCodigoArea() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", principla='" + getPrincipla() + "'" +
            ", tipoTelefone='" + getTipoTelefone() + "'" +
            ", pessoa=" + getPessoa() +
            "}";
    }
}
