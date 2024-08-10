package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.Competencia;
import com.dobemcontabilidade.service.dto.CompetenciaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Competencia} and its DTO {@link CompetenciaDTO}.
 */
@Mapper(componentModel = "spring")
public interface CompetenciaMapper extends EntityMapper<CompetenciaDTO, Competencia> {}
