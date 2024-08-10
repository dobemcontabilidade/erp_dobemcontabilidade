package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.Contador;
import com.dobemcontabilidade.domain.Empresa;
import com.dobemcontabilidade.domain.Tarefa;
import com.dobemcontabilidade.domain.TarefaEmpresa;
import com.dobemcontabilidade.service.dto.ContadorDTO;
import com.dobemcontabilidade.service.dto.EmpresaDTO;
import com.dobemcontabilidade.service.dto.TarefaDTO;
import com.dobemcontabilidade.service.dto.TarefaEmpresaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TarefaEmpresa} and its DTO {@link TarefaEmpresaDTO}.
 */
@Mapper(componentModel = "spring")
public interface TarefaEmpresaMapper extends EntityMapper<TarefaEmpresaDTO, TarefaEmpresa> {
    @Mapping(target = "empresa", source = "empresa", qualifiedByName = "empresaRazaoSocial")
    @Mapping(target = "contador", source = "contador", qualifiedByName = "contadorNome")
    @Mapping(target = "tarefa", source = "tarefa", qualifiedByName = "tarefaTitulo")
    TarefaEmpresaDTO toDto(TarefaEmpresa s);

    @Named("empresaRazaoSocial")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "razaoSocial", source = "razaoSocial")
    EmpresaDTO toDtoEmpresaRazaoSocial(Empresa empresa);

    @Named("contadorNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    ContadorDTO toDtoContadorNome(Contador contador);

    @Named("tarefaTitulo")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "titulo", source = "titulo")
    TarefaDTO toDtoTarefaTitulo(Tarefa tarefa);
}
