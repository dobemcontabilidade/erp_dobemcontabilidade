package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.PlanoContaAzul;
import com.dobemcontabilidade.service.dto.PlanoContaAzulDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PlanoContaAzul} and its DTO {@link PlanoContaAzulDTO}.
 */
@Mapper(componentModel = "spring")
public interface PlanoContaAzulMapper extends EntityMapper<PlanoContaAzulDTO, PlanoContaAzul> {}
