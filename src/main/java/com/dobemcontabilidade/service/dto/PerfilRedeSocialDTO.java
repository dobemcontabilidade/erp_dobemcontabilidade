package com.dobemcontabilidade.service.dto;

import com.dobemcontabilidade.domain.enumeration.TipoRedeSocialEnum;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.dobemcontabilidade.domain.PerfilRedeSocial} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PerfilRedeSocialDTO implements Serializable {

    private Long id;

    @NotNull
    private String rede;

    @NotNull
    private String urlPerfil;

    private TipoRedeSocialEnum tipoRede;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRede() {
        return rede;
    }

    public void setRede(String rede) {
        this.rede = rede;
    }

    public String getUrlPerfil() {
        return urlPerfil;
    }

    public void setUrlPerfil(String urlPerfil) {
        this.urlPerfil = urlPerfil;
    }

    public TipoRedeSocialEnum getTipoRede() {
        return tipoRede;
    }

    public void setTipoRede(TipoRedeSocialEnum tipoRede) {
        this.tipoRede = tipoRede;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PerfilRedeSocialDTO)) {
            return false;
        }

        PerfilRedeSocialDTO perfilRedeSocialDTO = (PerfilRedeSocialDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, perfilRedeSocialDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PerfilRedeSocialDTO{" +
            "id=" + getId() +
            ", rede='" + getRede() + "'" +
            ", urlPerfil='" + getUrlPerfil() + "'" +
            ", tipoRede='" + getTipoRede() + "'" +
            "}";
    }
}
