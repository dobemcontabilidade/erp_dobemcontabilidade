package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.AdicionalEnquadramento;
import com.dobemcontabilidade.domain.Enquadramento;
import com.dobemcontabilidade.domain.PlanoContabil;
import com.dobemcontabilidade.service.dto.AdicionalEnquadramentoDTO;
import com.dobemcontabilidade.service.dto.EnquadramentoDTO;
import com.dobemcontabilidade.service.dto.PlanoContabilDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdicionalEnquadramento} and its DTO {@link AdicionalEnquadramentoDTO}.
 */
@Mapper(componentModel = "spring")
public interface AdicionalEnquadramentoMapper extends EntityMapper<AdicionalEnquadramentoDTO, AdicionalEnquadramento> {
    @Mapping(target = "enquadramento", source = "enquadramento", qualifiedByName = "enquadramentoNome")
    @Mapping(target = "planoContabil", source = "planoContabil", qualifiedByName = "planoContabilNome")
    AdicionalEnquadramentoDTO toDto(AdicionalEnquadramento s);

    @Named("enquadramentoNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    EnquadramentoDTO toDtoEnquadramentoNome(Enquadramento enquadramento);

    @Named("planoContabilNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    PlanoContabilDTO toDtoPlanoContabilNome(PlanoContabil planoContabil);
}
