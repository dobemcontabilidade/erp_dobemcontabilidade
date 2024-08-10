package com.dobemcontabilidade.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.dobemcontabilidade.domain.EnderecoEmpresa} entity. This class is used
 * in {@link com.dobemcontabilidade.web.rest.EnderecoEmpresaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /endereco-empresas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EnderecoEmpresaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter logradouro;

    private StringFilter numero;

    private StringFilter complemento;

    private StringFilter bairro;

    private StringFilter cep;

    private BooleanFilter principal;

    private BooleanFilter filial;

    private BooleanFilter enderecoFiscal;

    private LongFilter empresaId;

    private LongFilter cidadeId;

    private Boolean distinct;

    public EnderecoEmpresaCriteria() {}

    public EnderecoEmpresaCriteria(EnderecoEmpresaCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.logradouro = other.optionalLogradouro().map(StringFilter::copy).orElse(null);
        this.numero = other.optionalNumero().map(StringFilter::copy).orElse(null);
        this.complemento = other.optionalComplemento().map(StringFilter::copy).orElse(null);
        this.bairro = other.optionalBairro().map(StringFilter::copy).orElse(null);
        this.cep = other.optionalCep().map(StringFilter::copy).orElse(null);
        this.principal = other.optionalPrincipal().map(BooleanFilter::copy).orElse(null);
        this.filial = other.optionalFilial().map(BooleanFilter::copy).orElse(null);
        this.enderecoFiscal = other.optionalEnderecoFiscal().map(BooleanFilter::copy).orElse(null);
        this.empresaId = other.optionalEmpresaId().map(LongFilter::copy).orElse(null);
        this.cidadeId = other.optionalCidadeId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public EnderecoEmpresaCriteria copy() {
        return new EnderecoEmpresaCriteria(this);
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

    public StringFilter getLogradouro() {
        return logradouro;
    }

    public Optional<StringFilter> optionalLogradouro() {
        return Optional.ofNullable(logradouro);
    }

    public StringFilter logradouro() {
        if (logradouro == null) {
            setLogradouro(new StringFilter());
        }
        return logradouro;
    }

    public void setLogradouro(StringFilter logradouro) {
        this.logradouro = logradouro;
    }

    public StringFilter getNumero() {
        return numero;
    }

    public Optional<StringFilter> optionalNumero() {
        return Optional.ofNullable(numero);
    }

    public StringFilter numero() {
        if (numero == null) {
            setNumero(new StringFilter());
        }
        return numero;
    }

    public void setNumero(StringFilter numero) {
        this.numero = numero;
    }

    public StringFilter getComplemento() {
        return complemento;
    }

    public Optional<StringFilter> optionalComplemento() {
        return Optional.ofNullable(complemento);
    }

    public StringFilter complemento() {
        if (complemento == null) {
            setComplemento(new StringFilter());
        }
        return complemento;
    }

    public void setComplemento(StringFilter complemento) {
        this.complemento = complemento;
    }

    public StringFilter getBairro() {
        return bairro;
    }

    public Optional<StringFilter> optionalBairro() {
        return Optional.ofNullable(bairro);
    }

    public StringFilter bairro() {
        if (bairro == null) {
            setBairro(new StringFilter());
        }
        return bairro;
    }

    public void setBairro(StringFilter bairro) {
        this.bairro = bairro;
    }

    public StringFilter getCep() {
        return cep;
    }

    public Optional<StringFilter> optionalCep() {
        return Optional.ofNullable(cep);
    }

    public StringFilter cep() {
        if (cep == null) {
            setCep(new StringFilter());
        }
        return cep;
    }

    public void setCep(StringFilter cep) {
        this.cep = cep;
    }

    public BooleanFilter getPrincipal() {
        return principal;
    }

    public Optional<BooleanFilter> optionalPrincipal() {
        return Optional.ofNullable(principal);
    }

    public BooleanFilter principal() {
        if (principal == null) {
            setPrincipal(new BooleanFilter());
        }
        return principal;
    }

    public void setPrincipal(BooleanFilter principal) {
        this.principal = principal;
    }

    public BooleanFilter getFilial() {
        return filial;
    }

    public Optional<BooleanFilter> optionalFilial() {
        return Optional.ofNullable(filial);
    }

    public BooleanFilter filial() {
        if (filial == null) {
            setFilial(new BooleanFilter());
        }
        return filial;
    }

    public void setFilial(BooleanFilter filial) {
        this.filial = filial;
    }

    public BooleanFilter getEnderecoFiscal() {
        return enderecoFiscal;
    }

    public Optional<BooleanFilter> optionalEnderecoFiscal() {
        return Optional.ofNullable(enderecoFiscal);
    }

    public BooleanFilter enderecoFiscal() {
        if (enderecoFiscal == null) {
            setEnderecoFiscal(new BooleanFilter());
        }
        return enderecoFiscal;
    }

    public void setEnderecoFiscal(BooleanFilter enderecoFiscal) {
        this.enderecoFiscal = enderecoFiscal;
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

    public LongFilter getCidadeId() {
        return cidadeId;
    }

    public Optional<LongFilter> optionalCidadeId() {
        return Optional.ofNullable(cidadeId);
    }

    public LongFilter cidadeId() {
        if (cidadeId == null) {
            setCidadeId(new LongFilter());
        }
        return cidadeId;
    }

    public void setCidadeId(LongFilter cidadeId) {
        this.cidadeId = cidadeId;
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
        final EnderecoEmpresaCriteria that = (EnderecoEmpresaCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(logradouro, that.logradouro) &&
            Objects.equals(numero, that.numero) &&
            Objects.equals(complemento, that.complemento) &&
            Objects.equals(bairro, that.bairro) &&
            Objects.equals(cep, that.cep) &&
            Objects.equals(principal, that.principal) &&
            Objects.equals(filial, that.filial) &&
            Objects.equals(enderecoFiscal, that.enderecoFiscal) &&
            Objects.equals(empresaId, that.empresaId) &&
            Objects.equals(cidadeId, that.cidadeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            logradouro,
            numero,
            complemento,
            bairro,
            cep,
            principal,
            filial,
            enderecoFiscal,
            empresaId,
            cidadeId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EnderecoEmpresaCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalLogradouro().map(f -> "logradouro=" + f + ", ").orElse("") +
            optionalNumero().map(f -> "numero=" + f + ", ").orElse("") +
            optionalComplemento().map(f -> "complemento=" + f + ", ").orElse("") +
            optionalBairro().map(f -> "bairro=" + f + ", ").orElse("") +
            optionalCep().map(f -> "cep=" + f + ", ").orElse("") +
            optionalPrincipal().map(f -> "principal=" + f + ", ").orElse("") +
            optionalFilial().map(f -> "filial=" + f + ", ").orElse("") +
            optionalEnderecoFiscal().map(f -> "enderecoFiscal=" + f + ", ").orElse("") +
            optionalEmpresaId().map(f -> "empresaId=" + f + ", ").orElse("") +
            optionalCidadeId().map(f -> "cidadeId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
