package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.PerfilContador;
import com.dobemcontabilidade.service.dto.PerfilContadorDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PerfilContador} and its DTO {@link PerfilContadorDTO}.
 */
@Mapper(componentModel = "spring")
public interface PerfilContadorMapper extends EntityMapper<PerfilContadorDTO, PerfilContador> {}
