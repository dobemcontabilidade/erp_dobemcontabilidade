package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DocsEmpresa.
 */
@Entity
@Table(name = "docs_empresa")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DocsEmpresa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Size(max = 300)
    @Column(name = "documento", length = 300)
    private String documento;

    @Lob
    @Column(name = "descricao")
    private String descricao;

    @Lob
    @Column(name = "url")
    private String url;

    @Column(name = "data_emissao")
    private Instant dataEmissao;

    @Column(name = "data_encerramento")
    private Instant dataEncerramento;

    @Column(name = "orgao_emissor")
    private String orgaoEmissor;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "redeSocialEmpresas", "certificadoDigitalEmpresas", "docsEmpresas", "enderecoEmpresas", "empresa" },
        allowSetters = true
    )
    private Pessoajuridica pessoaJuridica;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DocsEmpresa id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumento() {
        return this.documento;
    }

    public DocsEmpresa documento(String documento) {
        this.setDocumento(documento);
        return this;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public DocsEmpresa descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUrl() {
        return this.url;
    }

    public DocsEmpresa url(String url) {
        this.setUrl(url);
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Instant getDataEmissao() {
        return this.dataEmissao;
    }

    public DocsEmpresa dataEmissao(Instant dataEmissao) {
        this.setDataEmissao(dataEmissao);
        return this;
    }

    public void setDataEmissao(Instant dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public Instant getDataEncerramento() {
        return this.dataEncerramento;
    }

    public DocsEmpresa dataEncerramento(Instant dataEncerramento) {
        this.setDataEncerramento(dataEncerramento);
        return this;
    }

    public void setDataEncerramento(Instant dataEncerramento) {
        this.dataEncerramento = dataEncerramento;
    }

    public String getOrgaoEmissor() {
        return this.orgaoEmissor;
    }

    public DocsEmpresa orgaoEmissor(String orgaoEmissor) {
        this.setOrgaoEmissor(orgaoEmissor);
        return this;
    }

    public void setOrgaoEmissor(String orgaoEmissor) {
        this.orgaoEmissor = orgaoEmissor;
    }

    public Pessoajuridica getPessoaJuridica() {
        return this.pessoaJuridica;
    }

    public void setPessoaJuridica(Pessoajuridica pessoajuridica) {
        this.pessoaJuridica = pessoajuridica;
    }

    public DocsEmpresa pessoaJuridica(Pessoajuridica pessoajuridica) {
        this.setPessoaJuridica(pessoajuridica);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DocsEmpresa)) {
            return false;
        }
        return getId() != null && getId().equals(((DocsEmpresa) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DocsEmpresa{" +
            "id=" + getId() +
            ", documento='" + getDocumento() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", url='" + getUrl() + "'" +
            ", dataEmissao='" + getDataEmissao() + "'" +
            ", dataEncerramento='" + getDataEncerramento() + "'" +
            ", orgaoEmissor='" + getOrgaoEmissor() + "'" +
            "}";
    }
}
