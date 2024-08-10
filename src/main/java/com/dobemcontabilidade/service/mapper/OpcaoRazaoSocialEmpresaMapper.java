package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.Empresa;
import com.dobemcontabilidade.domain.OpcaoRazaoSocialEmpresa;
import com.dobemcontabilidade.service.dto.EmpresaDTO;
import com.dobemcontabilidade.service.dto.OpcaoRazaoSocialEmpresaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OpcaoRazaoSocialEmpresa} and its DTO {@link OpcaoRazaoSocialEmpresaDTO}.
 */
@Mapper(componentModel = "spring")
public interface OpcaoRazaoSocialEmpresaMapper extends EntityMapper<OpcaoRazaoSocialEmpresaDTO, OpcaoRazaoSocialEmpresa> {
    @Mapping(target = "empresa", source = "empresa", qualifiedByName = "empresaRazaoSocial")
    OpcaoRazaoSocialEmpresaDTO toDto(OpcaoRazaoSocialEmpresa s);

    @Named("empresaRazaoSocial")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "razaoSocial", source = "razaoSocial")
    EmpresaDTO toDtoEmpresaRazaoSocial(Empresa empresa);
}
