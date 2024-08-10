package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.Empresa;
import com.dobemcontabilidade.domain.Pessoa;
import com.dobemcontabilidade.domain.UsuarioEmpresa;
import com.dobemcontabilidade.service.dto.EmpresaDTO;
import com.dobemcontabilidade.service.dto.PessoaDTO;
import com.dobemcontabilidade.service.dto.UsuarioEmpresaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UsuarioEmpresa} and its DTO {@link UsuarioEmpresaDTO}.
 */
@Mapper(componentModel = "spring")
public interface UsuarioEmpresaMapper extends EntityMapper<UsuarioEmpresaDTO, UsuarioEmpresa> {
    @Mapping(target = "pessoa", source = "pessoa", qualifiedByName = "pessoaNome")
    @Mapping(target = "empresa", source = "empresa", qualifiedByName = "empresaRazaoSocial")
    UsuarioEmpresaDTO toDto(UsuarioEmpresa s);

    @Named("pessoaNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    PessoaDTO toDtoPessoaNome(Pessoa pessoa);

    @Named("empresaRazaoSocial")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "razaoSocial", source = "razaoSocial")
    EmpresaDTO toDtoEmpresaRazaoSocial(Empresa empresa);
}
