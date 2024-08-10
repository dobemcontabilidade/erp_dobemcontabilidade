package com.dobemcontabilidade.service.dto;

import com.dobemcontabilidade.domain.enumeration.SituacaoUsuarioErpEnum;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.UsuarioErp} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UsuarioErpDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 200)
    private String email;

    @Lob
    private String senha;

    private Instant dataHoraAtivacao;

    private Instant dataLimiteAcesso;

    private SituacaoUsuarioErpEnum situacao;

    @NotNull
    private AdministradorDTO administrador;

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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Instant getDataHoraAtivacao() {
        return dataHoraAtivacao;
    }

    public void setDataHoraAtivacao(Instant dataHoraAtivacao) {
        this.dataHoraAtivacao = dataHoraAtivacao;
    }

    public Instant getDataLimiteAcesso() {
        return dataLimiteAcesso;
    }

    public void setDataLimiteAcesso(Instant dataLimiteAcesso) {
        this.dataLimiteAcesso = dataLimiteAcesso;
    }

    public SituacaoUsuarioErpEnum getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoUsuarioErpEnum situacao) {
        this.situacao = situacao;
    }

    public AdministradorDTO getAdministrador() {
        return administrador;
    }

    public void setAdministrador(AdministradorDTO administrador) {
        this.administrador = administrador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UsuarioErpDTO)) {
            return false;
        }

        UsuarioErpDTO usuarioErpDTO = (UsuarioErpDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, usuarioErpDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UsuarioErpDTO{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", senha='" + getSenha() + "'" +
            ", dataHoraAtivacao='" + getDataHoraAtivacao() + "'" +
            ", dataLimiteAcesso='" + getDataLimiteAcesso() + "'" +
            ", situacao='" + getSituacao() + "'" +
            ", administrador=" + getAdministrador() +
            "}";
    }
}
