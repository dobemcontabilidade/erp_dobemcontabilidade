package com.dobemcontabilidade.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TermoContratoContabil.
 */
@Entity
@Table(name = "termo_contrato_contabil")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TermoContratoContabil implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "titulo")
    private String titulo;

    @Lob
    @Column(name = "descricao")
    private String descricao;

    @Lob
    @Column(name = "url_documento_fonte")
    private String urlDocumentoFonte;

    @Lob
    @Column(name = "documento")
    private String documento;

    @Column(name = "disponivel")
    private Boolean disponivel;

    @Column(name = "data_criacao")
    private Instant dataCriacao;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "termoContratoContabil")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "termoContratoContabil", "empresa" }, allowSetters = true)
    private Set<TermoContratoAssinaturaEmpresa> termoContratoAssinaturaEmpresas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TermoContratoContabil id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public TermoContratoContabil titulo(String titulo) {
        this.setTitulo(titulo);
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public TermoContratoContabil descricao(String descricao) {
        this.setDescricao(descricao);
        return this;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUrlDocumentoFonte() {
        return this.urlDocumentoFonte;
    }

    public TermoContratoContabil urlDocumentoFonte(String urlDocumentoFonte) {
        this.setUrlDocumentoFonte(urlDocumentoFonte);
        return this;
    }

    public void setUrlDocumentoFonte(String urlDocumentoFonte) {
        this.urlDocumentoFonte = urlDocumentoFonte;
    }

    public String getDocumento() {
        return this.documento;
    }

    public TermoContratoContabil documento(String documento) {
        this.setDocumento(documento);
        return this;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Boolean getDisponivel() {
        return this.disponivel;
    }

    public TermoContratoContabil disponivel(Boolean disponivel) {
        this.setDisponivel(disponivel);
        return this;
    }

    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
    }

    public Instant getDataCriacao() {
        return this.dataCriacao;
    }

    public TermoContratoContabil dataCriacao(Instant dataCriacao) {
        this.setDataCriacao(dataCriacao);
        return this;
    }

    public void setDataCriacao(Instant dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Set<TermoContratoAssinaturaEmpresa> getTermoContratoAssinaturaEmpresas() {
        return this.termoContratoAssinaturaEmpresas;
    }

    public void setTermoContratoAssinaturaEmpresas(Set<TermoContratoAssinaturaEmpresa> termoContratoAssinaturaEmpresas) {
        if (this.termoContratoAssinaturaEmpresas != null) {
            this.termoContratoAssinaturaEmpresas.forEach(i -> i.setTermoContratoContabil(null));
        }
        if (termoContratoAssinaturaEmpresas != null) {
            termoContratoAssinaturaEmpresas.forEach(i -> i.setTermoContratoContabil(this));
        }
        this.termoContratoAssinaturaEmpresas = termoContratoAssinaturaEmpresas;
    }

    public TermoContratoContabil termoContratoAssinaturaEmpresas(Set<TermoContratoAssinaturaEmpresa> termoContratoAssinaturaEmpresas) {
        this.setTermoContratoAssinaturaEmpresas(termoContratoAssinaturaEmpresas);
        return this;
    }

    public TermoContratoContabil addTermoContratoAssinaturaEmpresa(TermoContratoAssinaturaEmpresa termoContratoAssinaturaEmpresa) {
        this.termoContratoAssinaturaEmpresas.add(termoContratoAssinaturaEmpresa);
        termoContratoAssinaturaEmpresa.setTermoContratoContabil(this);
        return this;
    }

    public TermoContratoContabil removeTermoContratoAssinaturaEmpresa(TermoContratoAssinaturaEmpresa termoContratoAssinaturaEmpresa) {
        this.termoContratoAssinaturaEmpresas.remove(termoContratoAssinaturaEmpresa);
        termoContratoAssinaturaEmpresa.setTermoContratoContabil(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TermoContratoContabil)) {
            return false;
        }
        return getId() != null && getId().equals(((TermoContratoContabil) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TermoContratoContabil{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", descricao='" + getDescricao() + "'" +
            ", urlDocumentoFonte='" + getUrlDocumentoFonte() + "'" +
            ", documento='" + getDocumento() + "'" +
            ", disponivel='" + getDisponivel() + "'" +
            ", dataCriacao='" + getDataCriacao() + "'" +
            "}";
    }
}
