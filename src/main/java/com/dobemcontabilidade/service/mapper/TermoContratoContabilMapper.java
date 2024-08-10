package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.PlanoContabil;
import com.dobemcontabilidade.domain.TermoContratoContabil;
import com.dobemcontabilidade.service.dto.PlanoContabilDTO;
import com.dobemcontabilidade.service.dto.TermoContratoContabilDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TermoContratoContabil} and its DTO {@link TermoContratoContabilDTO}.
 */
@Mapper(componentModel = "spring")
public interface TermoContratoContabilMapper extends EntityMapper<TermoContratoContabilDTO, TermoContratoContabil> {
    @Mapping(target = "planoContabil", source = "planoContabil", qualifiedByName = "planoContabilNome")
    TermoContratoContabilDTO toDto(TermoContratoContabil s);

    @Named("planoContabilNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    PlanoContabilDTO toDtoPlanoContabilNome(PlanoContabil planoContabil);
}
