package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.Administrador;
import com.dobemcontabilidade.domain.UsuarioErp;
import com.dobemcontabilidade.service.dto.AdministradorDTO;
import com.dobemcontabilidade.service.dto.UsuarioErpDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UsuarioErp} and its DTO {@link UsuarioErpDTO}.
 */
@Mapper(componentModel = "spring")
public interface UsuarioErpMapper extends EntityMapper<UsuarioErpDTO, UsuarioErp> {
    @Mapping(target = "administrador", source = "administrador", qualifiedByName = "administradorNome")
    UsuarioErpDTO toDto(UsuarioErp s);

    @Named("administradorNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    AdministradorDTO toDtoAdministradorNome(Administrador administrador);
}
