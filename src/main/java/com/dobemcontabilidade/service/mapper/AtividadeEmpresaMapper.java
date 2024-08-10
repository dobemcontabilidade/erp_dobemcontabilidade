package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.AtividadeEmpresa;
import com.dobemcontabilidade.domain.Empresa;
import com.dobemcontabilidade.service.dto.AtividadeEmpresaDTO;
import com.dobemcontabilidade.service.dto.EmpresaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AtividadeEmpresa} and its DTO {@link AtividadeEmpresaDTO}.
 */
@Mapper(componentModel = "spring")
public interface AtividadeEmpresaMapper extends EntityMapper<AtividadeEmpresaDTO, AtividadeEmpresa> {
    @Mapping(target = "empresa", source = "empresa", qualifiedByName = "empresaRazaoSocial")
    AtividadeEmpresaDTO toDto(AtividadeEmpresa s);

    @Named("empresaRazaoSocial")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "razaoSocial", source = "razaoSocial")
    EmpresaDTO toDtoEmpresaRazaoSocial(Empresa empresa);
}
