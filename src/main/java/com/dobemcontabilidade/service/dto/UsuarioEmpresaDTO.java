package com.dobemcontabilidade.service.dto;

import com.dobemcontabilidade.domain.enumeration.SituacaoUsuarioEmpresaEnum;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.UsuarioEmpresa} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UsuarioEmpresaDTO implements Serializable {

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

    private SituacaoUsuarioEmpresaEnum situacao;

    private PessoaDTO pessoa;

    @NotNull
    private EmpresaDTO empresa;

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

    public SituacaoUsuarioEmpresaEnum getSituacao() {
        return situacao;
    }

    public void setSituacao(SituacaoUsuarioEmpresaEnum situacao) {
        this.situacao = situacao;
    }

    public PessoaDTO getPessoa() {
        return pessoa;
    }

    public void setPessoa(PessoaDTO pessoa) {
        this.pessoa = pessoa;
    }

    public EmpresaDTO getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaDTO empresa) {
        this.empresa = empresa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UsuarioEmpresaDTO)) {
            return false;
        }

        UsuarioEmpresaDTO usuarioEmpresaDTO = (UsuarioEmpresaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, usuarioEmpresaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UsuarioEmpresaDTO{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", senha='" + getSenha() + "'" +
            ", token='" + getToken() + "'" +
            ", dataHoraAtivacao='" + getDataHoraAtivacao() + "'" +
            ", dataLimiteAcesso='" + getDataLimiteAcesso() + "'" +
            ", situacao='" + getSituacao() + "'" +
            ", pessoa=" + getPessoa() +
            ", empresa=" + getEmpresa() +
            "}";
    }
}
