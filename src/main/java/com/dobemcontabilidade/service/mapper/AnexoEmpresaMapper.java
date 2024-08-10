package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.AnexoEmpresa;
import com.dobemcontabilidade.domain.Empresa;
import com.dobemcontabilidade.service.dto.AnexoEmpresaDTO;
import com.dobemcontabilidade.service.dto.EmpresaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AnexoEmpresa} and its DTO {@link AnexoEmpresaDTO}.
 */
@Mapper(componentModel = "spring")
public interface AnexoEmpresaMapper extends EntityMapper<AnexoEmpresaDTO, AnexoEmpresa> {
    @Mapping(target = "empresa", source = "empresa", qualifiedByName = "empresaRazaoSocial")
    AnexoEmpresaDTO toDto(AnexoEmpresa s);

    @Named("empresaRazaoSocial")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "razaoSocial", source = "razaoSocial")
    EmpresaDTO toDtoEmpresaRazaoSocial(Empresa empresa);
}
