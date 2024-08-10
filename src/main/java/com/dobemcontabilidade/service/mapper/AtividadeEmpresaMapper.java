package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.AtividadeEmpresa;
import com.dobemcontabilidade.domain.Empresa;
import com.dobemcontabilidade.domain.SubclasseCnae;
import com.dobemcontabilidade.service.dto.AtividadeEmpresaDTO;
import com.dobemcontabilidade.service.dto.EmpresaDTO;
import com.dobemcontabilidade.service.dto.SubclasseCnaeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AtividadeEmpresa} and its DTO {@link AtividadeEmpresaDTO}.
 */
@Mapper(componentModel = "spring")
public interface AtividadeEmpresaMapper extends EntityMapper<AtividadeEmpresaDTO, AtividadeEmpresa> {
    @Mapping(target = "cnae", source = "cnae", qualifiedByName = "subclasseCnaeDescricao")
    @Mapping(target = "empresa", source = "empresa", qualifiedByName = "empresaRazaoSocial")
    AtividadeEmpresaDTO toDto(AtividadeEmpresa s);

    @Named("subclasseCnaeDescricao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "descricao", source = "descricao")
    SubclasseCnaeDTO toDtoSubclasseCnaeDescricao(SubclasseCnae subclasseCnae);

    @Named("empresaRazaoSocial")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "razaoSocial", source = "razaoSocial")
    EmpresaDTO toDtoEmpresaRazaoSocial(Empresa empresa);

    default String map(byte[] value) {
        return new String(value);
    }
}
