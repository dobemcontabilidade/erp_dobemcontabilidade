package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.Pessoajuridica} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.PessoajuridicaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /pessoajuridicas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PessoajuridicaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter razaoSocial;

    private StringFilter nomeFantasia;

    private StringFilter cnpj;

    private LongFilter redeSocialEmpresaId;

    private LongFilter certificadoDigitalEmpresaId;

    private LongFilter docsEmpresaId;

    private LongFilter enderecoEmpresaId;

    private LongFilter empresaId;

    private Boolean distinct;

    public PessoajuridicaCriteria() {}

    public PessoajuridicaCriteria(PessoajuridicaCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.razaoSocial = other.optionalRazaoSocial().map(StringFilter::copy).orElse(null);
        this.nomeFantasia = other.optionalNomeFantasia().map(StringFilter::copy).orElse(null);
        this.cnpj = other.optionalCnpj().map(StringFilter::copy).orElse(null);
        this.redeSocialEmpresaId = other.optionalRedeSocialEmpresaId().map(LongFilter::copy).orElse(null);
        this.certificadoDigitalEmpresaId = other.optionalCertificadoDigitalEmpresaId().map(LongFilter::copy).orElse(null);
        this.docsEmpresaId = other.optionalDocsEmpresaId().map(LongFilter::copy).orElse(null);
        this.enderecoEmpresaId = other.optionalEnderecoEmpresaId().map(LongFilter::copy).orElse(null);
        this.empresaId = other.optionalEmpresaId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public PessoajuridicaCriteria copy() {
        return new PessoajuridicaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getRazaoSocial() {
        return razaoSocial;
    }

    public Optional<StringFilter> optionalRazaoSocial() {
        return Optional.ofNullable(razaoSocial);
    }

    public StringFilter razaoSocial() {
        if (razaoSocial == null) {
            setRazaoSocial(new StringFilter());
        }
        return razaoSocial;
    }

    public void setRazaoSocial(StringFilter razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public StringFilter getNomeFantasia() {
        return nomeFantasia;
    }

    public Optional<StringFilter> optionalNomeFantasia() {
        return Optional.ofNullable(nomeFantasia);
    }

    public StringFilter nomeFantasia() {
        if (nomeFantasia == null) {
            setNomeFantasia(new StringFilter());
        }
        return nomeFantasia;
    }

    public void setNomeFantasia(StringFilter nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public StringFilter getCnpj() {
        return cnpj;
    }

    public Optional<StringFilter> optionalCnpj() {
        return Optional.ofNullable(cnpj);
    }

    public StringFilter cnpj() {
        if (cnpj == null) {
            setCnpj(new StringFilter());
        }
        return cnpj;
    }

    public void setCnpj(StringFilter cnpj) {
        this.cnpj = cnpj;
    }

    public LongFilter getRedeSocialEmpresaId() {
        return redeSocialEmpresaId;
    }

    public Optional<LongFilter> optionalRedeSocialEmpresaId() {
        return Optional.ofNullable(redeSocialEmpresaId);
    }

    public LongFilter redeSocialEmpresaId() {
        if (redeSocialEmpresaId == null) {
            setRedeSocialEmpresaId(new LongFilter());
        }
        return redeSocialEmpresaId;
    }

    public void setRedeSocialEmpresaId(LongFilter redeSocialEmpresaId) {
        this.redeSocialEmpresaId = redeSocialEmpresaId;
    }

    public LongFilter getCertificadoDigitalEmpresaId() {
        return certificadoDigitalEmpresaId;
    }

    public Optional<LongFilter> optionalCertificadoDigitalEmpresaId() {
        return Optional.ofNullable(certificadoDigitalEmpresaId);
    }

    public LongFilter certificadoDigitalEmpresaId() {
        if (certificadoDigitalEmpresaId == null) {
            setCertificadoDigitalEmpresaId(new LongFilter());
        }
        return certificadoDigitalEmpresaId;
    }

    public void setCertificadoDigitalEmpresaId(LongFilter certificadoDigitalEmpresaId) {
        this.certificadoDigitalEmpresaId = certificadoDigitalEmpresaId;
    }

    public LongFilter getDocsEmpresaId() {
        return docsEmpresaId;
    }

    public Optional<LongFilter> optionalDocsEmpresaId() {
        return Optional.ofNullable(docsEmpresaId);
    }

    public LongFilter docsEmpresaId() {
        if (docsEmpresaId == null) {
            setDocsEmpresaId(new LongFilter());
        }
        return docsEmpresaId;
    }

    public void setDocsEmpresaId(LongFilter docsEmpresaId) {
        this.docsEmpresaId = docsEmpresaId;
    }

    public LongFilter getEnderecoEmpresaId() {
        return enderecoEmpresaId;
    }

    public Optional<LongFilter> optionalEnderecoEmpresaId() {
        return Optional.ofNullable(enderecoEmpresaId);
    }

    public LongFilter enderecoEmpresaId() {
        if (enderecoEmpresaId == null) {
            setEnderecoEmpresaId(new LongFilter());
        }
        return enderecoEmpresaId;
    }

    public void setEnderecoEmpresaId(LongFilter enderecoEmpresaId) {
        this.enderecoEmpresaId = enderecoEmpresaId;
    }

    public LongFilter getEmpresaId() {
        return empresaId;
    }

    public Optional<LongFilter> optionalEmpresaId() {
        return Optional.ofNullable(empresaId);
    }

    public LongFilter empresaId() {
        if (empresaId == null) {
            setEmpresaId(new LongFilter());
        }
        return empresaId;
    }

    public void setEmpresaId(LongFilter empresaId) {
        this.empresaId = empresaId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PessoajuridicaCriteria that = (PessoajuridicaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(razaoSocial, that.razaoSocial) &&
            Objects.equals(nomeFantasia, that.nomeFantasia) &&
            Objects.equals(cnpj, that.cnpj) &&
            Objects.equals(redeSocialEmpresaId, that.redeSocialEmpresaId) &&
            Objects.equals(certificadoDigitalEmpresaId, that.certificadoDigitalEmpresaId) &&
            Objects.equals(docsEmpresaId, that.docsEmpresaId) &&
            Objects.equals(enderecoEmpresaId, that.enderecoEmpresaId) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            razaoSocial,
            nomeFantasia,
            cnpj,
            redeSocialEmpresaId,
            certificadoDigitalEmpresaId,
            docsEmpresaId,
            enderecoEmpresaId,
            empresaId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PessoajuridicaCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalRazaoSocial().map(f -> "razaoSocial=" + f + ", ").orElse("") +
            optionalNomeFantasia().map(f -> "nomeFantasia=" + f + ", ").orElse("") +
            optionalCnpj().map(f -> "cnpj=" + f + ", ").orElse("") +
            optionalRedeSocialEmpresaId().map(f -> "redeSocialEmpresaId=" + f + ", ").orElse("") +
            optionalCertificadoDigitalEmpresaId().map(f -> "certificadoDigitalEmpresaId=" + f + ", ").orElse("") +
            optionalDocsEmpresaId().map(f -> "docsEmpresaId=" + f + ", ").orElse("") +
            optionalEnderecoEmpresaId().map(f -> "enderecoEmpresaId=" + f + ", ").orElse("") +
            optionalEmpresaId().map(f -> "empresaId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
