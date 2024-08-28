package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RedeSocialEmpresa.
 */
@Entity
@Table(name = "rede_social_empresa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RedeSocialEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "perfil", nullable = false)
    private String perfil;

    @Lob
    @Column(name = "url_perfil")
    private String urlPerfil;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "redeSocialEmpresas" }, allowSetters = true)
    private RedeSocial redeSocial;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "redeSocialEmpresas", "certificadoDigitalEmpresas", "docsEmpresas", "enderecoEmpresas", "empresa" },
        allowSetters = true
    )
    private Pessoajuridica pessoajuridica;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RedeSocialEmpresa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPerfil() {
        return this.perfil;
    }

    public RedeSocialEmpresa perfil(String perfil) {
        this.setPerfil(perfil);
        return this;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getUrlPerfil() {
        return this.urlPerfil;
    }

    public RedeSocialEmpresa urlPerfil(String urlPerfil) {
        this.setUrlPerfil(urlPerfil);
        return this;
    }

    public void setUrlPerfil(String urlPerfil) {
        this.urlPerfil = urlPerfil;
    }

    public RedeSocial getRedeSocial() {
        return this.redeSocial;
    }

    public void setRedeSocial(RedeSocial redeSocial) {
        this.redeSocial = redeSocial;
    }

    public RedeSocialEmpresa redeSocial(RedeSocial redeSocial) {
        this.setRedeSocial(redeSocial);
        return this;
    }

    public Pessoajuridica getPessoajuridica() {
        return this.pessoajuridica;
    }

    public void setPessoajuridica(Pessoajuridica pessoajuridica) {
        this.pessoajuridica = pessoajuridica;
    }

    public RedeSocialEmpresa pessoajuridica(Pessoajuridica pessoajuridica) {
        this.setPessoajuridica(pessoajuridica);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RedeSocialEmpresa)) {
            return false;
        }
        return getId() != null && getId().equals(((RedeSocialEmpresa) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RedeSocialEmpresa{" +
            "id=" + getId() +
            ", perfil='" + getPerfil() + "'" +
            ", urlPerfil='" + getUrlPerfil() + "'" +
            "}";
    }
}
