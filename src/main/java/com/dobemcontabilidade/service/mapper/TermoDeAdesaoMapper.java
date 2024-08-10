package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.TermoDeAdesao;
import com.dobemcontabilidade.service.dto.TermoDeAdesaoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TermoDeAdesao} and its DTO {@link TermoDeAdesaoDTO}.
 */
@Mapper(componentModel = "spring")
public interface TermoDeAdesaoMapper extends EntityMapper<TermoDeAdesaoDTO, TermoDeAdesao> {}
