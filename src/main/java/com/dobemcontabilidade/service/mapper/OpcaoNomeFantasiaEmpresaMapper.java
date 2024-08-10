package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.Empresa;
import com.dobemcontabilidade.domain.OpcaoNomeFantasiaEmpresa;
import com.dobemcontabilidade.service.dto.EmpresaDTO;
import com.dobemcontabilidade.service.dto.OpcaoNomeFantasiaEmpresaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OpcaoNomeFantasiaEmpresa} and its DTO {@link OpcaoNomeFantasiaEmpresaDTO}.
 */
@Mapper(componentModel = "spring")
public interface OpcaoNomeFantasiaEmpresaMapper extends EntityMapper<OpcaoNomeFantasiaEmpresaDTO, OpcaoNomeFantasiaEmpresa> {
    @Mapping(target = "empresa", source = "empresa", qualifiedByName = "empresaRazaoSocial")
    OpcaoNomeFantasiaEmpresaDTO toDto(OpcaoNomeFantasiaEmpresa s);

    @Named("empresaRazaoSocial")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "razaoSocial", source = "razaoSocial")
    EmpresaDTO toDtoEmpresaRazaoSocial(Empresa empresa);
}
