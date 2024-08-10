package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.Administrador;
import com.dobemcontabilidade.domain.Contador;
import com.dobemcontabilidade.domain.UsuarioContador;
import com.dobemcontabilidade.service.dto.AdministradorDTO;
import com.dobemcontabilidade.service.dto.ContadorDTO;
import com.dobemcontabilidade.service.dto.UsuarioContadorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UsuarioContador} and its DTO {@link UsuarioContadorDTO}.
 */
@Mapper(componentModel = "spring")
public interface UsuarioContadorMapper extends EntityMapper<UsuarioContadorDTO, UsuarioContador> {
    @Mapping(target = "contador", source = "contador", qualifiedByName = "contadorNome")
    @Mapping(target = "administrador", source = "administrador", qualifiedByName = "administradorFuncao")
    UsuarioContadorDTO toDto(UsuarioContador s);

    @Named("contadorNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    ContadorDTO toDtoContadorNome(Contador contador);

    @Named("administradorFuncao")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "funcao", source = "funcao")
    AdministradorDTO toDtoAdministradorFuncao(Administrador administrador);
}
