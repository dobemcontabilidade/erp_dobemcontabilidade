package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.Departamento;
import com.dobemcontabilidade.service.dto.DepartamentoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Departamento} and its DTO {@link DepartamentoDTO}.
 */
@Mapper(componentModel = "spring")
public interface DepartamentoMapper extends EntityMapper<DepartamentoDTO, Departamento> {}
