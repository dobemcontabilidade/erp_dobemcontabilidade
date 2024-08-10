package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.AssinaturaEmpresa;
import com.dobemcontabilidade.domain.Empresa;
import com.dobemcontabilidade.domain.FormaDePagamento;
import com.dobemcontabilidade.domain.PeriodoPagamento;
import com.dobemcontabilidade.domain.PlanoContaAzul;
import com.dobemcontabilidade.domain.PlanoContabil;
import com.dobemcontabilidade.service.dto.AssinaturaEmpresaDTO;
import com.dobemcontabilidade.service.dto.EmpresaDTO;
import com.dobemcontabilidade.service.dto.FormaDePagamentoDTO;
import com.dobemcontabilidade.service.dto.PeriodoPagamentoDTO;
import com.dobemcontabilidade.service.dto.PlanoContaAzulDTO;
import com.dobemcontabilidade.service.dto.PlanoContabilDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AssinaturaEmpresa} and its DTO {@link AssinaturaEmpresaDTO}.
 */
@Mapper(componentModel = "spring")
public interface AssinaturaEmpresaMapper extends EntityMapper<AssinaturaEmpresaDTO, AssinaturaEmpresa> {
    @Mapping(target = "periodoPagamento", source = "periodoPagamento", qualifiedByName = "periodoPagamentoPeriodo")
    @Mapping(target = "formaDePagamento", source = "formaDePagamento", qualifiedByName = "formaDePagamentoForma")
    @Mapping(target = "planoContaAzul", source = "planoContaAzul", qualifiedByName = "planoContaAzulNome")
    @Mapping(target = "planoContabil", source = "planoContabil", qualifiedByName = "planoContabilNome")
    @Mapping(target = "empresa", source = "empresa", qualifiedByName = "empresaRazaoSocial")
    AssinaturaEmpresaDTO toDto(AssinaturaEmpresa s);

    @Named("periodoPagamentoPeriodo")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "periodo", source = "periodo")
    PeriodoPagamentoDTO toDtoPeriodoPagamentoPeriodo(PeriodoPagamento periodoPagamento);

    @Named("formaDePagamentoForma")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "forma", source = "forma")
    FormaDePagamentoDTO toDtoFormaDePagamentoForma(FormaDePagamento formaDePagamento);

    @Named("planoContaAzulNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    PlanoContaAzulDTO toDtoPlanoContaAzulNome(PlanoContaAzul planoContaAzul);

    @Named("planoContabilNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    PlanoContabilDTO toDtoPlanoContabilNome(PlanoContabil planoContabil);

    @Named("empresaRazaoSocial")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "razaoSocial", source = "razaoSocial")
    EmpresaDTO toDtoEmpresaRazaoSocial(Empresa empresa);
}
