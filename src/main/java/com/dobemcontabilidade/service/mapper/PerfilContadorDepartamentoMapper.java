package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.Departamento;
import com.dobemcontabilidade.domain.PerfilContador;
import com.dobemcontabilidade.domain.PerfilContadorDepartamento;
import com.dobemcontabilidade.service.dto.DepartamentoDTO;
import com.dobemcontabilidade.service.dto.PerfilContadorDTO;
import com.dobemcontabilidade.service.dto.PerfilContadorDepartamentoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PerfilContadorDepartamento} and its DTO {@link PerfilContadorDepartamentoDTO}.
 */
@Mapper(componentModel = "spring")
public interface PerfilContadorDepartamentoMapper extends EntityMapper<PerfilContadorDepartamentoDTO, PerfilContadorDepartamento> {
    @Mapping(target = "departamento", source = "departamento", qualifiedByName = "departamentoNome")
    @Mapping(target = "perfilContador", source = "perfilContador", qualifiedByName = "perfilContadorPerfil")
    PerfilContadorDepartamentoDTO toDto(PerfilContadorDepartamento s);

    @Named("departamentoNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    DepartamentoDTO toDtoDepartamentoNome(Departamento departamento);

    @Named("perfilContadorPerfil")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "perfil", source = "perfil")
    PerfilContadorDTO toDtoPerfilContadorPerfil(PerfilContador perfilContador);
}
