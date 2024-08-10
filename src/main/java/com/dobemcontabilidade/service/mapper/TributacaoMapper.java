package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.Tributacao;
import com.dobemcontabilidade.service.dto.TributacaoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tributacao} and its DTO {@link TributacaoDTO}.
 */
@Mapper(componentModel = "spring")
public interface TributacaoMapper extends EntityMapper<TributacaoDTO, Tributacao> {}
