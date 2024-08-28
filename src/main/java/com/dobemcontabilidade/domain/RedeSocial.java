package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RedeSocial.
 */
@Entity
@Table(name = "rede_social")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RedeSocial implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "nome", nullable = false)
    private String nome;

    @Lob
    @Column(name = "descricao")
    private String descricao;

    @Lob
    @Column(name = "url")
    private String url;

    @Column(name = "logo")
    private String logo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "redeSocial")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "redeSocial", "pessoajuridica" }, allowSetters = true)
    private Set<RedeSocialEmpresa> redeSocialEmpresas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RedeSocial id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public RedeSocial nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public RedeSocial descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUrl() {
        return this.url;
    }

    public RedeSocial url(String url) {
        this.setUrl(url);
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLogo() {
        return this.logo;
    }

    public RedeSocial logo(String logo) {
        this.setLogo(logo);
        return this;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Set<RedeSocialEmpresa> getRedeSocialEmpresas() {
        return this.redeSocialEmpresas;
    }

    public void setRedeSocialEmpresas(Set<RedeSocialEmpresa> redeSocialEmpresas) {
        if (this.redeSocialEmpresas != null) {
            this.redeSocialEmpresas.forEach(i -> i.setRedeSocial(null));
        }
        if (redeSocialEmpresas != null) {
            redeSocialEmpresas.forEach(i -> i.setRedeSocial(this));
        }
        this.redeSocialEmpresas = redeSocialEmpresas;
    }

    public RedeSocial redeSocialEmpresas(Set<RedeSocialEmpresa> redeSocialEmpresas) {
        this.setRedeSocialEmpresas(redeSocialEmpresas);
        return this;
    }

    public RedeSocial addRedeSocialEmpresa(RedeSocialEmpresa redeSocialEmpresa) {
        this.redeSocialEmpresas.add(redeSocialEmpresa);
        redeSocialEmpresa.setRedeSocial(this);
        return this;
    }

    public RedeSocial removeRedeSocialEmpresa(RedeSocialEmpresa redeSocialEmpresa) {
        this.redeSocialEmpresas.remove(redeSocialEmpresa);
        redeSocialEmpresa.setRedeSocial(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RedeSocial)) {
            return false;
        }
        return getId() != null && getId().equals(((RedeSocial) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RedeSocial{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", url='" + getUrl() + "'" +
            ", logo='" + getLogo() + "'" +
            "}";
    }
}
