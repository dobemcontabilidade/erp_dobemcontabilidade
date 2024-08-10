package com.dobemcontabilidade.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.Email} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmailDTO implements Serializable {

    private Long id;

    @NotNull
    private String email;

    private Boolean principal;

    @NotNull
    private PessoaDTO pessoa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getPrincipal() {
        return principal;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
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
        if (!(o instanceof EmailDTO)) {
            return false;
        }

        EmailDTO emailDTO = (EmailDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, emailDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmailDTO{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", principal='" + getPrincipal() + "'" +
            ", pessoa=" + getPessoa() +
            "}";
    }
}
