package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.Estado;
import com.dobemcontabilidade.service.dto.EstadoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Estado} and its DTO {@link EstadoDTO}.
 */
@Mapper(componentModel = "spring")
public interface EstadoMapper extends EntityMapper<EstadoDTO, Estado> {}
