package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.Empresa;
import com.dobemcontabilidade.domain.Funcionario;
import com.dobemcontabilidade.domain.Pessoa;
import com.dobemcontabilidade.service.dto.EmpresaDTO;
import com.dobemcontabilidade.service.dto.FuncionarioDTO;
import com.dobemcontabilidade.service.dto.PessoaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Funcionario} and its DTO {@link FuncionarioDTO}.
 */
@Mapper(componentModel = "spring")
public interface FuncionarioMapper extends EntityMapper<FuncionarioDTO, Funcionario> {
    @Mapping(target = "pessoa", source = "pessoa", qualifiedByName = "pessoaNome")
    @Mapping(target = "empresa", source = "empresa", qualifiedByName = "empresaRazaoSocial")
    FuncionarioDTO toDto(Funcionario s);

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
