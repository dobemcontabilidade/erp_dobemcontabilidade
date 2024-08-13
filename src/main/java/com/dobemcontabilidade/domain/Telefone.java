package com.dobemcontabilidade.domain;

import com.dobemcontabilidade.domain.enumeration.TipoTelefoneEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Telefone.
 */
@Entity
@Table(name = "telefone")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Telefone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 3)
    @Column(name = "codigo_area", length = 3, nullable = false)
    private String codigoArea;

    @NotNull
    @Column(name = "telefone", nullable = false)
    private String telefone;

    @Column(name = "principal")
    private Boolean principal;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_telefone")
    private TipoTelefoneEnum tipoTelefone;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = {
            "funcionarios",
            "anexoPessoas",
            "escolaridadePessoas",
            "bancoPessoas",
            "dependentesFuncionarios",
            "enderecoPessoas",
            "emails",
            "telefones",
            "administrador",
            "contador",
            "socio",
        },
        allowSetters = true
    )
    private Pessoa pessoa;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Telefone id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoArea() {
        return this.codigoArea;
    }

    public Telefone codigoArea(String codigoArea) {
        this.setCodigoArea(codigoArea);
        return this;
    }

    public void setCodigoArea(String codigoArea) {
        this.codigoArea = codigoArea;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public Telefone telefone(String telefone) {
        this.setTelefone(telefone);
        return this;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Boolean getPrincipal() {
        return this.principal;
    }

    public Telefone principal(Boolean principal) {
        this.setPrincipal(principal);
        return this;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }

    public TipoTelefoneEnum getTipoTelefone() {
        return this.tipoTelefone;
    }

    public Telefone tipoTelefone(TipoTelefoneEnum tipoTelefone) {
        this.setTipoTelefone(tipoTelefone);
        return this;
    }

    public void setTipoTelefone(TipoTelefoneEnum tipoTelefone) {
        this.tipoTelefone = tipoTelefone;
    }

    public Pessoa getPessoa() {
        return this.pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Telefone pessoa(Pessoa pessoa) {
        this.setPessoa(pessoa);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Telefone)) {
            return false;
        }
        return getId() != null && getId().equals(((Telefone) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Telefone{" +
            "id=" + getId() +
            ", codigoArea='" + getCodigoArea() + "'" +
            ", telefone='" + getTelefone() + "'" +
            ", principal='" + getPrincipal() + "'" +
            ", tipoTelefone='" + getTipoTelefone() + "'" +
            "}";
    }
}
