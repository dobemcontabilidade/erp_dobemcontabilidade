package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.Cidade;
import com.dobemcontabilidade.domain.Empresa;
import com.dobemcontabilidade.domain.EnderecoEmpresa;
import com.dobemcontabilidade.service.dto.CidadeDTO;
import com.dobemcontabilidade.service.dto.EmpresaDTO;
import com.dobemcontabilidade.service.dto.EnderecoEmpresaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EnderecoEmpresa} and its DTO {@link EnderecoEmpresaDTO}.
 */
@Mapper(componentModel = "spring")
public interface EnderecoEmpresaMapper extends EntityMapper<EnderecoEmpresaDTO, EnderecoEmpresa> {
    @Mapping(target = "empresa", source = "empresa", qualifiedByName = "empresaRazaoSocial")
    @Mapping(target = "cidade", source = "cidade", qualifiedByName = "cidadeNome")
    EnderecoEmpresaDTO toDto(EnderecoEmpresa s);

    @Named("empresaRazaoSocial")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "razaoSocial", source = "razaoSocial")
    EmpresaDTO toDtoEmpresaRazaoSocial(Empresa empresa);

    @Named("cidadeNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    CidadeDTO toDtoCidadeNome(Cidade cidade);
}
