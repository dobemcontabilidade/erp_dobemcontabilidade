package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.Pais;
import com.dobemcontabilidade.service.dto.PaisDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Pais} and its DTO {@link PaisDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaisMapper extends EntityMapper<PaisDTO, Pais> {}
