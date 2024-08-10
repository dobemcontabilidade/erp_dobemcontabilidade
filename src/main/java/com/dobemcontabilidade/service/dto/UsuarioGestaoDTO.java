package com.dobemcontabilidade.service.dto;

import com.dobemcontabilidade.domain.enumeration.SituacaoUsuarioGestaoEnum;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.UsuarioGestao} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UsuarioGestaoDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 200)
    private String email;

    @Lob
    private String senha;

    @Lob
    private String token;

    private Instant dataHoraAtivacao;

    private Instant dataLimiteAcesso;

    private SituacaoUsuarioGestaoEnum situacao;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public SituacaoUsuarioGestaoEnum getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoUsuarioGestaoEnum situacao) {
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
        if (!(o instanceof UsuarioGestaoDTO)) {
            return false;
        }

        UsuarioGestaoDTO usuarioGestaoDTO = (UsuarioGestaoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, usuarioGestaoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UsuarioGestaoDTO{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", senha='" + getSenha() + "'" +
            ", token='" + getToken() + "'" +
            ", dataHoraAtivacao='" + getDataHoraAtivacao() + "'" +
            ", dataLimiteAcesso='" + getDataLimiteAcesso() + "'" +
            ", situacao='" + getSituacao() + "'" +
            ", administrador=" + getAdministrador() +
            "}";
    }
}
