package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.Departamento;
import com.dobemcontabilidade.domain.DepartamentoFuncionario;
import com.dobemcontabilidade.domain.Funcionario;
import com.dobemcontabilidade.service.dto.DepartamentoDTO;
import com.dobemcontabilidade.service.dto.DepartamentoFuncionarioDTO;
import com.dobemcontabilidade.service.dto.FuncionarioDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DepartamentoFuncionario} and its DTO {@link DepartamentoFuncionarioDTO}.
 */
@Mapper(componentModel = "spring")
public interface DepartamentoFuncionarioMapper extends EntityMapper<DepartamentoFuncionarioDTO, DepartamentoFuncionario> {
    @Mapping(target = "funcionario", source = "funcionario", qualifiedByName = "funcionarioNome")
    @Mapping(target = "departamento", source = "departamento", qualifiedByName = "departamentoNome")
    DepartamentoFuncionarioDTO toDto(DepartamentoFuncionario s);

    @Named("funcionarioNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    FuncionarioDTO toDtoFuncionarioNome(Funcionario funcionario);

    @Named("departamentoNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    DepartamentoDTO toDtoDepartamentoNome(Departamento departamento);
}
