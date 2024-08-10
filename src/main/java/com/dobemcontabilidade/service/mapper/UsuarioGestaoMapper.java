package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.Administrador;
import com.dobemcontabilidade.domain.UsuarioGestao;
import com.dobemcontabilidade.service.dto.AdministradorDTO;
import com.dobemcontabilidade.service.dto.UsuarioGestaoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UsuarioGestao} and its DTO {@link UsuarioGestaoDTO}.
 */
@Mapper(componentModel = "spring")
public interface UsuarioGestaoMapper extends EntityMapper<UsuarioGestaoDTO, UsuarioGestao> {
    @Mapping(target = "administrador", source = "administrador", qualifiedByName = "administradorNome")
    UsuarioGestaoDTO toDto(UsuarioGestao s);

    @Named("administradorNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    AdministradorDTO toDtoAdministradorNome(Administrador administrador);
}
