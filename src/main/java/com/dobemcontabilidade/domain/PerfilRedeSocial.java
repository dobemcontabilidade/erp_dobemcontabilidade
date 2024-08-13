package com.dobemcontabilidade.domain;

import com.dobemcontabilidade.domain.enumeration.TipoRedeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A PerfilRedeSocial.
 */
@Entity
@Table(name = "perfil_rede_social")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PerfilRedeSocial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "rede", nullable = false)
    private String rede;

    @NotNull
    @Column(name = "url_perfil", nullable = false)
    private String urlPerfil;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_rede")
    private TipoRedeEnum tipoRede;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PerfilRedeSocial id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRede() {
        return this.rede;
    }

    public PerfilRedeSocial rede(String rede) {
        this.setRede(rede);
        return this;
    }

    public void setRede(String rede) {
        this.rede = rede;
    }

    public String getUrlPerfil() {
        return this.urlPerfil;
    }

    public PerfilRedeSocial urlPerfil(String urlPerfil) {
        this.setUrlPerfil(urlPerfil);
        return this;
    }

    public void setUrlPerfil(String urlPerfil) {
        this.urlPerfil = urlPerfil;
    }

    public TipoRedeEnum getTipoRede() {
        return this.tipoRede;
    }

    public PerfilRedeSocial tipoRede(TipoRedeEnum tipoRede) {
        this.setTipoRede(tipoRede);
        return this;
    }

    public void setTipoRede(TipoRedeEnum tipoRede) {
        this.tipoRede = tipoRede;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PerfilRedeSocial)) {
            return false;
        }
        return getId() != null && getId().equals(((PerfilRedeSocial) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PerfilRedeSocial{" +
            "id=" + getId() +
            ", rede='" + getRede() + "'" +
            ", urlPerfil='" + getUrlPerfil() + "'" +
            ", tipoRede='" + getTipoRede() + "'" +
            "}";
    }
}
