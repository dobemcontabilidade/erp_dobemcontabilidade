package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.Enquadramento;
import com.dobemcontabilidade.service.dto.EnquadramentoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Enquadramento} and its DTO {@link EnquadramentoDTO}.
 */
@Mapper(componentModel = "spring")
public interface EnquadramentoMapper extends EntityMapper<EnquadramentoDTO, Enquadramento> {}
