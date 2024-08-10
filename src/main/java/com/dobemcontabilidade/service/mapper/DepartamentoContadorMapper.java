package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.Contador;
import com.dobemcontabilidade.domain.Departamento;
import com.dobemcontabilidade.domain.DepartamentoContador;
import com.dobemcontabilidade.service.dto.ContadorDTO;
import com.dobemcontabilidade.service.dto.DepartamentoContadorDTO;
import com.dobemcontabilidade.service.dto.DepartamentoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DepartamentoContador} and its DTO {@link DepartamentoContadorDTO}.
 */
@Mapper(componentModel = "spring")
public interface DepartamentoContadorMapper extends EntityMapper<DepartamentoContadorDTO, DepartamentoContador> {
    @Mapping(target = "departamento", source = "departamento", qualifiedByName = "departamentoNome")
    @Mapping(target = "contador", source = "contador", qualifiedByName = "contadorNome")
    DepartamentoContadorDTO toDto(DepartamentoContador s);

    @Named("departamentoNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    DepartamentoDTO toDtoDepartamentoNome(Departamento departamento);

    @Named("contadorNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    ContadorDTO toDtoContadorNome(Contador contador);
}
