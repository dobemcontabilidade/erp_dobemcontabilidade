package com.dobemcontabilidade.service.mapper;

import com.dobemcontabilidade.domain.AdicionalTributacao;
import com.dobemcontabilidade.domain.PlanoContabil;
import com.dobemcontabilidade.domain.Tributacao;
import com.dobemcontabilidade.service.dto.AdicionalTributacaoDTO;
import com.dobemcontabilidade.service.dto.PlanoContabilDTO;
import com.dobemcontabilidade.service.dto.TributacaoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AdicionalTributacao} and its DTO {@link AdicionalTributacaoDTO}.
 */
@Mapper(componentModel = "spring")
public interface AdicionalTributacaoMapper extends EntityMapper<AdicionalTributacaoDTO, AdicionalTributacao> {
    @Mapping(target = "tributacao", source = "tributacao", qualifiedByName = "tributacaoNome")
    @Mapping(target = "planoContabil", source = "planoContabil", qualifiedByName = "planoContabilNome")
    AdicionalTributacaoDTO toDto(AdicionalTributacao s);

    @Named("tributacaoNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    TributacaoDTO toDtoTributacaoNome(Tributacao tributacao);

    @Named("planoContabilNome")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nome", source = "nome")
    PlanoContabilDTO toDtoPlanoContabilNome(PlanoContabil planoContabil);
}
