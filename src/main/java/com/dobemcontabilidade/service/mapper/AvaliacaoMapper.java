package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.Avaliacao;
import com.dobemcontabilidade.service.dto.AvaliacaoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Avaliacao} and its DTO {@link AvaliacaoDTO}.
 */
@Mapper(componentModel = "spring")
public interface AvaliacaoMapper extends EntityMapper<AvaliacaoDTO, Avaliacao> {}
