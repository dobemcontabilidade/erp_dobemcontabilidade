package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.Frequencia;
import com.dobemcontabilidade.service.dto.FrequenciaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Frequencia} and its DTO {@link FrequenciaDTO}.
 */
@Mapper(componentModel = "spring")
public interface FrequenciaMapper extends EntityMapper<FrequenciaDTO, Frequencia> {}
